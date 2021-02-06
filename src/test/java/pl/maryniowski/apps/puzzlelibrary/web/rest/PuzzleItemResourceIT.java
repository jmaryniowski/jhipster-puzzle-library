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
import org.springframework.util.Base64Utils;
import pl.maryniowski.apps.puzzlelibrary.PuzzlelibraryApp;
import pl.maryniowski.apps.puzzlelibrary.domain.PuzzleItem;
import pl.maryniowski.apps.puzzlelibrary.domain.enumeration.PuzzleType;
import pl.maryniowski.apps.puzzlelibrary.repository.PuzzleItemRepository;

/**
 * Integration tests for the {@link PuzzleItemResource} REST controller.
 */
@SpringBootTest(classes = PuzzlelibraryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PuzzleItemResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final PuzzleType DEFAULT_TYPE = PuzzleType.PUZZLE;
    private static final PuzzleType UPDATED_TYPE = PuzzleType.GAME;

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    @Autowired
    private PuzzleItemRepository puzzleItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPuzzleItemMockMvc;

    private PuzzleItem puzzleItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PuzzleItem createEntity(EntityManager em) {
        PuzzleItem puzzleItem = new PuzzleItem()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE);
        return puzzleItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PuzzleItem createUpdatedEntity(EntityManager em) {
        PuzzleItem puzzleItem = new PuzzleItem()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);
        return puzzleItem;
    }

    @BeforeEach
    public void initTest() {
        puzzleItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuzzleItem() throws Exception {
        int databaseSizeBeforeCreate = puzzleItemRepository.findAll().size();
        // Create the PuzzleItem
        restPuzzleItemMockMvc
            .perform(
                post("/api/puzzle-items").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puzzleItem))
            )
            .andExpect(status().isCreated());

        // Validate the PuzzleItem in the database
        List<PuzzleItem> puzzleItemList = puzzleItemRepository.findAll();
        assertThat(puzzleItemList).hasSize(databaseSizeBeforeCreate + 1);
        PuzzleItem testPuzzleItem = puzzleItemList.get(puzzleItemList.size() - 1);
        assertThat(testPuzzleItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPuzzleItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPuzzleItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPuzzleItem.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testPuzzleItem.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPuzzleItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puzzleItemRepository.findAll().size();

        // Create the PuzzleItem with an existing ID
        puzzleItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuzzleItemMockMvc
            .perform(
                post("/api/puzzle-items").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puzzleItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PuzzleItem in the database
        List<PuzzleItem> puzzleItemList = puzzleItemRepository.findAll();
        assertThat(puzzleItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPuzzleItems() throws Exception {
        // Initialize the database
        puzzleItemRepository.saveAndFlush(puzzleItem);

        // Get all the puzzleItemList
        restPuzzleItemMockMvc
            .perform(get("/api/puzzle-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puzzleItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))));
    }

    @Test
    @Transactional
    public void getPuzzleItem() throws Exception {
        // Initialize the database
        puzzleItemRepository.saveAndFlush(puzzleItem);

        // Get the puzzleItem
        restPuzzleItemMockMvc
            .perform(get("/api/puzzle-items/{id}", puzzleItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(puzzleItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)));
    }

    @Test
    @Transactional
    public void getNonExistingPuzzleItem() throws Exception {
        // Get the puzzleItem
        restPuzzleItemMockMvc.perform(get("/api/puzzle-items/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuzzleItem() throws Exception {
        // Initialize the database
        puzzleItemRepository.saveAndFlush(puzzleItem);

        int databaseSizeBeforeUpdate = puzzleItemRepository.findAll().size();

        // Update the puzzleItem
        PuzzleItem updatedPuzzleItem = puzzleItemRepository.findById(puzzleItem.getId()).get();
        // Disconnect from session so that the updates on updatedPuzzleItem are not directly saved in db
        em.detach(updatedPuzzleItem);
        updatedPuzzleItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);

        restPuzzleItemMockMvc
            .perform(
                put("/api/puzzle-items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPuzzleItem))
            )
            .andExpect(status().isOk());

        // Validate the PuzzleItem in the database
        List<PuzzleItem> puzzleItemList = puzzleItemRepository.findAll();
        assertThat(puzzleItemList).hasSize(databaseSizeBeforeUpdate);
        PuzzleItem testPuzzleItem = puzzleItemList.get(puzzleItemList.size() - 1);
        assertThat(testPuzzleItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPuzzleItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPuzzleItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPuzzleItem.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testPuzzleItem.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPuzzleItem() throws Exception {
        int databaseSizeBeforeUpdate = puzzleItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuzzleItemMockMvc
            .perform(
                put("/api/puzzle-items").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puzzleItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PuzzleItem in the database
        List<PuzzleItem> puzzleItemList = puzzleItemRepository.findAll();
        assertThat(puzzleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePuzzleItem() throws Exception {
        // Initialize the database
        puzzleItemRepository.saveAndFlush(puzzleItem);

        int databaseSizeBeforeDelete = puzzleItemRepository.findAll().size();

        // Delete the puzzleItem
        restPuzzleItemMockMvc
            .perform(delete("/api/puzzle-items/{id}", puzzleItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PuzzleItem> puzzleItemList = puzzleItemRepository.findAll();
        assertThat(puzzleItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
