package pl.maryniowski.apps.puzzlelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maryniowski.apps.puzzlelibrary.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
