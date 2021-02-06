package pl.maryniowski.apps.puzzlelibrary.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.maryniowski.apps.puzzlelibrary.domain.PuzzlePerson;
import pl.maryniowski.apps.puzzlelibrary.repository.PuzzlePersonRepository;
import pl.maryniowski.apps.puzzlelibrary.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link pl.maryniowski.apps.puzzlelibrary.domain.PuzzlePerson}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PuzzlePersonResource {
    private final Logger log = LoggerFactory.getLogger(PuzzlePersonResource.class);

    private static final String ENTITY_NAME = "puzzlePerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PuzzlePersonRepository puzzlePersonRepository;

    public PuzzlePersonResource(PuzzlePersonRepository puzzlePersonRepository) {
        this.puzzlePersonRepository = puzzlePersonRepository;
    }

    /**
     * {@code POST  /puzzle-people} : Create a new puzzlePerson.
     *
     * @param puzzlePerson the puzzlePerson to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new puzzlePerson, or with status {@code 400 (Bad Request)} if the puzzlePerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/puzzle-people")
    public ResponseEntity<PuzzlePerson> createPuzzlePerson(@RequestBody PuzzlePerson puzzlePerson) throws URISyntaxException {
        log.debug("REST request to save PuzzlePerson : {}", puzzlePerson);
        if (puzzlePerson.getId() != null) {
            throw new BadRequestAlertException("A new puzzlePerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuzzlePerson result = puzzlePersonRepository.save(puzzlePerson);
        return ResponseEntity
            .created(new URI("/api/puzzle-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /puzzle-people} : Updates an existing puzzlePerson.
     *
     * @param puzzlePerson the puzzlePerson to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated puzzlePerson,
     * or with status {@code 400 (Bad Request)} if the puzzlePerson is not valid,
     * or with status {@code 500 (Internal Server Error)} if the puzzlePerson couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/puzzle-people")
    public ResponseEntity<PuzzlePerson> updatePuzzlePerson(@RequestBody PuzzlePerson puzzlePerson) throws URISyntaxException {
        log.debug("REST request to update PuzzlePerson : {}", puzzlePerson);
        if (puzzlePerson.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PuzzlePerson result = puzzlePersonRepository.save(puzzlePerson);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, puzzlePerson.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /puzzle-people} : get all the puzzlePeople.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of puzzlePeople in body.
     */
    @GetMapping("/puzzle-people")
    public List<PuzzlePerson> getAllPuzzlePeople() {
        log.debug("REST request to get all PuzzlePeople");
        return puzzlePersonRepository.findAll();
    }

    /**
     * {@code GET  /puzzle-people/:id} : get the "id" puzzlePerson.
     *
     * @param id the id of the puzzlePerson to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the puzzlePerson, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/puzzle-people/{id}")
    public ResponseEntity<PuzzlePerson> getPuzzlePerson(@PathVariable Long id) {
        log.debug("REST request to get PuzzlePerson : {}", id);
        Optional<PuzzlePerson> puzzlePerson = puzzlePersonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(puzzlePerson);
    }

    /**
     * {@code DELETE  /puzzle-people/:id} : delete the "id" puzzlePerson.
     *
     * @param id the id of the puzzlePerson to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/puzzle-people/{id}")
    public ResponseEntity<Void> deletePuzzlePerson(@PathVariable Long id) {
        log.debug("REST request to delete PuzzlePerson : {}", id);
        puzzlePersonRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
