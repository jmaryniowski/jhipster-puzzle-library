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
import pl.maryniowski.apps.puzzlelibrary.domain.PuzzleRental;
import pl.maryniowski.apps.puzzlelibrary.repository.PuzzleRentalRepository;
import pl.maryniowski.apps.puzzlelibrary.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link pl.maryniowski.apps.puzzlelibrary.domain.PuzzleRental}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PuzzleRentalResource {
    private final Logger log = LoggerFactory.getLogger(PuzzleRentalResource.class);

    private static final String ENTITY_NAME = "puzzleRental";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PuzzleRentalRepository puzzleRentalRepository;

    public PuzzleRentalResource(PuzzleRentalRepository puzzleRentalRepository) {
        this.puzzleRentalRepository = puzzleRentalRepository;
    }

    /**
     * {@code POST  /puzzle-rentals} : Create a new puzzleRental.
     *
     * @param puzzleRental the puzzleRental to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new puzzleRental, or with status {@code 400 (Bad Request)} if the puzzleRental has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/puzzle-rentals")
    public ResponseEntity<PuzzleRental> createPuzzleRental(@RequestBody PuzzleRental puzzleRental) throws URISyntaxException {
        log.debug("REST request to save PuzzleRental : {}", puzzleRental);
        if (puzzleRental.getId() != null) {
            throw new BadRequestAlertException("A new puzzleRental cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuzzleRental result = puzzleRentalRepository.save(puzzleRental);
        return ResponseEntity
            .created(new URI("/api/puzzle-rentals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /puzzle-rentals} : Updates an existing puzzleRental.
     *
     * @param puzzleRental the puzzleRental to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated puzzleRental,
     * or with status {@code 400 (Bad Request)} if the puzzleRental is not valid,
     * or with status {@code 500 (Internal Server Error)} if the puzzleRental couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/puzzle-rentals")
    public ResponseEntity<PuzzleRental> updatePuzzleRental(@RequestBody PuzzleRental puzzleRental) throws URISyntaxException {
        log.debug("REST request to update PuzzleRental : {}", puzzleRental);
        if (puzzleRental.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PuzzleRental result = puzzleRentalRepository.save(puzzleRental);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, puzzleRental.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /puzzle-rentals} : get all the puzzleRentals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of puzzleRentals in body.
     */
    @GetMapping("/puzzle-rentals")
    public List<PuzzleRental> getAllPuzzleRentals() {
        log.debug("REST request to get all PuzzleRentals");
        return puzzleRentalRepository.findAll();
    }

    /**
     * {@code GET  /puzzle-rentals/:id} : get the "id" puzzleRental.
     *
     * @param id the id of the puzzleRental to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the puzzleRental, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/puzzle-rentals/{id}")
    public ResponseEntity<PuzzleRental> getPuzzleRental(@PathVariable Long id) {
        log.debug("REST request to get PuzzleRental : {}", id);
        Optional<PuzzleRental> puzzleRental = puzzleRentalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(puzzleRental);
    }

    /**
     * {@code DELETE  /puzzle-rentals/:id} : delete the "id" puzzleRental.
     *
     * @param id the id of the puzzleRental to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/puzzle-rentals/{id}")
    public ResponseEntity<Void> deletePuzzleRental(@PathVariable Long id) {
        log.debug("REST request to delete PuzzleRental : {}", id);
        puzzleRentalRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
