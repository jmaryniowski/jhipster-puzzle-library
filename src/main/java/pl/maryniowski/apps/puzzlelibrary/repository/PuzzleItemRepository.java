package pl.maryniowski.apps.puzzlelibrary.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import pl.maryniowski.apps.puzzlelibrary.domain.PuzzleItem;

/**
 * Spring Data  repository for the PuzzleItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuzzleItemRepository extends JpaRepository<PuzzleItem, Long> {}
