package pl.maryniowski.apps.puzzlelibrary.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import pl.maryniowski.apps.puzzlelibrary.domain.PuzzlePerson;

/**
 * Spring Data  repository for the PuzzlePerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuzzlePersonRepository extends JpaRepository<PuzzlePerson, Long> {}
