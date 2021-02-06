package pl.maryniowski.apps.puzzlelibrary.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.maryniowski.apps.puzzlelibrary.domain.PuzzleItem;
import pl.maryniowski.apps.puzzlelibrary.repository.PuzzleItemRepository;
import pl.maryniowski.apps.puzzlelibrary.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link pl.maryniowski.apps.puzzlelibrary.domain.PuzzleItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PuzzleItemResource {
    private final Logger log = LoggerFactory.getLogger(PuzzleItemResource.class);

    private static final String ENTITY_NAME = "puzzleItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PuzzleItemRepository puzzleItemRepository;

    public PuzzleItemResource(PuzzleItemRepository puzzleItemRepository) {
        this.puzzleItemRepository = puzzleItemRepository;
    }

    /**
     * {@code POST  /puzzle-items} : Create a new puzzleItem.
     *
     * @param puzzleItem the puzzleItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new puzzleItem, or with status {@code 400 (Bad Request)} if the puzzleItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/puzzle-items")
    public ResponseEntity<PuzzleItem> createPuzzleItem(@RequestBody PuzzleItem puzzleItem) throws URISyntaxException {
        log.debug("REST request to save PuzzleItem : {}", puzzleItem);
        if (puzzleItem.getId() != null) {
            throw new BadRequestAlertException("A new puzzleItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuzzleItem result = puzzleItemRepository.save(puzzleItem);
        return ResponseEntity
            .created(new URI("/api/puzzle-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /puzzle-items} : Updates an existing puzzleItem.
     *
     * @param puzzleItem the puzzleItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated puzzleItem,
     * or with status {@code 400 (Bad Request)} if the puzzleItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the puzzleItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/puzzle-items")
    public ResponseEntity<PuzzleItem> updatePuzzleItem(@RequestBody PuzzleItem puzzleItem) throws URISyntaxException {
        log.debug("REST request to update PuzzleItem : {}", puzzleItem);
        if (puzzleItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PuzzleItem result = puzzleItemRepository.save(puzzleItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, puzzleItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /puzzle-items} : get all the puzzleItems.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of puzzleItems in body.
     */
    @GetMapping("/puzzle-items")
    public List<PuzzleItem> getAllPuzzleItems(@RequestParam(required = false) String filter) {
        if ("puzzlerental-is-null".equals(filter)) {
            log.debug("REST request to get all PuzzleItems where puzzleRental is null");
            return StreamSupport
                .stream(puzzleItemRepository.findAll().spliterator(), false)
                .filter(puzzleItem -> puzzleItem.getPuzzleRental() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PuzzleItems");
        return puzzleItemRepository.findAll();
    }

    /**
     * {@code GET  /puzzle-items/:id} : get the "id" puzzleItem.
     *
     * @param id the id of the puzzleItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the puzzleItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/puzzle-items/{id}")
    public ResponseEntity<PuzzleItem> getPuzzleItem(@PathVariable Long id) {
        log.debug("REST request to get PuzzleItem : {}", id);
        Optional<PuzzleItem> puzzleItem = puzzleItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(puzzleItem);
    }

    /**
     * {@code DELETE  /puzzle-items/:id} : delete the "id" puzzleItem.
     *
     * @param id the id of the puzzleItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/puzzle-items/{id}")
    public ResponseEntity<Void> deletePuzzleItem(@PathVariable Long id) {
        log.debug("REST request to delete PuzzleItem : {}", id);
        puzzleItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
