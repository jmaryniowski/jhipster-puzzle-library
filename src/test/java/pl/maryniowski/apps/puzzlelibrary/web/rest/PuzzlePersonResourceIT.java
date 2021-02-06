package pl.maryniowski.apps.puzzlelibrary.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import pl.maryniowski.apps.puzzlelibrary.domain.PuzzlePerson;
import pl.maryniowski.apps.puzzlelibrary.repository.PuzzlePersonRepository;

/**
 * Integration tests for the {@link PuzzlePersonResource} REST controller.
 */
@SpringBootTest(classes = PuzzlelibraryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PuzzlePersonResourceIT {
    @Autowired
    private PuzzlePersonRepository puzzlePersonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPuzzlePersonMockMvc;

    private PuzzlePerson puzzlePerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PuzzlePerson createEntity(EntityManager em) {
        PuzzlePerson puzzlePerson = new PuzzlePerson();
        return puzzlePerson;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PuzzlePerson createUpdatedEntity(EntityManager em) {
        PuzzlePerson puzzlePerson = new PuzzlePerson();
        return puzzlePerson;
    }

    @BeforeEach
    public void initTest() {
        puzzlePerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuzzlePerson() throws Exception {
        int databaseSizeBeforeCreate = puzzlePersonRepository.findAll().size();
        // Create the PuzzlePerson
        restPuzzlePersonMockMvc
            .perform(
                post("/api/puzzle-people").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puzzlePerson))
            )
            .andExpect(status().isCreated());

        // Validate the PuzzlePerson in the database
        List<PuzzlePerson> puzzlePersonList = puzzlePersonRepository.findAll();
        assertThat(puzzlePersonList).hasSize(databaseSizeBeforeCreate + 1);
        PuzzlePerson testPuzzlePerson = puzzlePersonList.get(puzzlePersonList.size() - 1);
    }

    @Test
    @Transactional
    public void createPuzzlePersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puzzlePersonRepository.findAll().size();

        // Create the PuzzlePerson with an existing ID
        puzzlePerson.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuzzlePersonMockMvc
            .perform(
                post("/api/puzzle-people").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puzzlePerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the PuzzlePerson in the database
        List<PuzzlePerson> puzzlePersonList = puzzlePersonRepository.findAll();
        assertThat(puzzlePersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPuzzlePeople() throws Exception {
        // Initialize the database
        puzzlePersonRepository.saveAndFlush(puzzlePerson);

        // Get all the puzzlePersonList
        restPuzzlePersonMockMvc
            .perform(get("/api/puzzle-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puzzlePerson.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPuzzlePerson() throws Exception {
        // Initialize the database
        puzzlePersonRepository.saveAndFlush(puzzlePerson);

        // Get the puzzlePerson
        restPuzzlePersonMockMvc
            .perform(get("/api/puzzle-people/{id}", puzzlePerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(puzzlePerson.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPuzzlePerson() throws Exception {
        // Get the puzzlePerson
        restPuzzlePersonMockMvc.perform(get("/api/puzzle-people/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuzzlePerson() throws Exception {
        // Initialize the database
        puzzlePersonRepository.saveAndFlush(puzzlePerson);

        int databaseSizeBeforeUpdate = puzzlePersonRepository.findAll().size();

        // Update the puzzlePerson
        PuzzlePerson updatedPuzzlePerson = puzzlePersonRepository.findById(puzzlePerson.getId()).get();
        // Disconnect from session so that the updates on updatedPuzzlePerson are not directly saved in db
        em.detach(updatedPuzzlePerson);

        restPuzzlePersonMockMvc
            .perform(
                put("/api/puzzle-people")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPuzzlePerson))
            )
            .andExpect(status().isOk());

        // Validate the PuzzlePerson in the database
        List<PuzzlePerson> puzzlePersonList = puzzlePersonRepository.findAll();
        assertThat(puzzlePersonList).hasSize(databaseSizeBeforeUpdate);
        PuzzlePerson testPuzzlePerson = puzzlePersonList.get(puzzlePersonList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPuzzlePerson() throws Exception {
        int databaseSizeBeforeUpdate = puzzlePersonRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuzzlePersonMockMvc
            .perform(
                put("/api/puzzle-people").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puzzlePerson))
            )
            .andExpect(status().isBadRequest());

        // Validate the PuzzlePerson in the database
        List<PuzzlePerson> puzzlePersonList = puzzlePersonRepository.findAll();
        assertThat(puzzlePersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePuzzlePerson() throws Exception {
        // Initialize the database
        puzzlePersonRepository.saveAndFlush(puzzlePerson);

        int databaseSizeBeforeDelete = puzzlePersonRepository.findAll().size();

        // Delete the puzzlePerson
        restPuzzlePersonMockMvc
            .perform(delete("/api/puzzle-people/{id}", puzzlePerson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PuzzlePerson> puzzlePersonList = puzzlePersonRepository.findAll();
        assertThat(puzzlePersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
