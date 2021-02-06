package pl.maryniowski.apps.puzzlelibrary.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import pl.maryniowski.apps.puzzlelibrary.domain.PuzzleRental;

/**
 * Spring Data  repository for the PuzzleRental entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuzzleRentalRepository extends JpaRepository<PuzzleRental, Long> {}
