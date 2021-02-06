package pl.maryniowski.apps.puzzlelibrary.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.maryniowski.apps.puzzlelibrary.PuzzlelibraryApp;
import pl.maryniowski.apps.puzzlelibrary.domain.PuzzleRental;
import pl.maryniowski.apps.puzzlelibrary.repository.PuzzleRentalRepository;

/**
 * Integration tests for the {@link PuzzleRentalResource} REST controller.
 */
@SpringBootTest(classes = PuzzlelibraryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PuzzleRentalResourceIT {
    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private PuzzleRentalRepository puzzleRentalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPuzzleRentalMockMvc;

    private PuzzleRental puzzleRental;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PuzzleRental createEntity(EntityManager em) {
        PuzzleRental puzzleRental = new PuzzleRental().startDate(DEFAULT_START_DATE).endDate(DEFAULT_END_DATE).isActive(DEFAULT_IS_ACTIVE);
        return puzzleRental;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PuzzleRental createUpdatedEntity(EntityManager em) {
        PuzzleRental puzzleRental = new PuzzleRental().startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).isActive(UPDATED_IS_ACTIVE);
        return puzzleRental;
    }

    @BeforeEach
    public void initTest() {
        puzzleRental = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuzzleRental() throws Exception {
        int databaseSizeBeforeCreate = puzzleRentalRepository.findAll().size();
        // Create the PuzzleRental
        restPuzzleRentalMockMvc
            .perform(
                post("/api/puzzle-rentals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puzzleRental))
            )
            .andExpect(status().isCreated());

        // Validate the PuzzleRental in the database
        List<PuzzleRental> puzzleRentalList = puzzleRentalRepository.findAll();
        assertThat(puzzleRentalList).hasSize(databaseSizeBeforeCreate + 1);
        PuzzleRental testPuzzleRental = puzzleRentalList.get(puzzleRentalList.size() - 1);
        assertThat(testPuzzleRental.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPuzzleRental.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPuzzleRental.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createPuzzleRentalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puzzleRentalRepository.findAll().size();

        // Create the PuzzleRental with an existing ID
        puzzleRental.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuzzleRentalMockMvc
            .perform(
                post("/api/puzzle-rentals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puzzleRental))
            )
            .andExpect(status().isBadRequest());

        // Validate the PuzzleRental in the database
        List<PuzzleRental> puzzleRentalList = puzzleRentalRepository.findAll();
        assertThat(puzzleRentalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPuzzleRentals() throws Exception {
        // Initialize the database
        puzzleRentalRepository.saveAndFlush(puzzleRental);

        // Get all the puzzleRentalList
        restPuzzleRentalMockMvc
            .perform(get("/api/puzzle-rentals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puzzleRental.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPuzzleRental() throws Exception {
        // Initialize the database
        puzzleRentalRepository.saveAndFlush(puzzleRental);

        // Get the puzzleRental
        restPuzzleRentalMockMvc
            .perform(get("/api/puzzle-rentals/{id}", puzzleRental.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(puzzleRental.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPuzzleRental() throws Exception {
        // Get the puzzleRental
        restPuzzleRentalMockMvc.perform(get("/api/puzzle-rentals/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuzzleRental() throws Exception {
        // Initialize the database
        puzzleRentalRepository.saveAndFlush(puzzleRental);

        int databaseSizeBeforeUpdate = puzzleRentalRepository.findAll().size();

        // Update the puzzleRental
        PuzzleRental updatedPuzzleRental = puzzleRentalRepository.findById(puzzleRental.getId()).get();
        // Disconnect from session so that the updates on updatedPuzzleRental are not directly saved in db
        em.detach(updatedPuzzleRental);
        updatedPuzzleRental.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).isActive(UPDATED_IS_ACTIVE);

        restPuzzleRentalMockMvc
            .perform(
                put("/api/puzzle-rentals")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPuzzleRental))
            )
            .andExpect(status().isOk());

        // Validate the PuzzleRental in the database
        List<PuzzleRental> puzzleRentalList = puzzleRentalRepository.findAll();
        assertThat(puzzleRentalList).hasSize(databaseSizeBeforeUpdate);
        PuzzleRental testPuzzleRental = puzzleRentalList.get(puzzleRentalList.size() - 1);
        assertThat(testPuzzleRental.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPuzzleRental.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPuzzleRental.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPuzzleRental() throws Exception {
        int databaseSizeBeforeUpdate = puzzleRentalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuzzleRentalMockMvc
            .perform(
                put("/api/puzzle-rentals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puzzleRental))
            )
            .andExpect(status().isBadRequest());

        // Validate the PuzzleRental in the database
        List<PuzzleRental> puzzleRentalList = puzzleRentalRepository.findAll();
        assertThat(puzzleRentalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePuzzleRental() throws Exception {
        // Initialize the database
        puzzleRentalRepository.saveAndFlush(puzzleRental);

        int databaseSizeBeforeDelete = puzzleRentalRepository.findAll().size();

        // Delete the puzzleRental
        restPuzzleRentalMockMvc
            .perform(delete("/api/puzzle-rentals/{id}", puzzleRental.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PuzzleRental> puzzleRentalList = puzzleRentalRepository.findAll();
        assertThat(puzzleRentalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
