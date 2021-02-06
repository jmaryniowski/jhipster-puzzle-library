package pl.maryniowski.apps.puzzlelibrary.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.maryniowski.apps.puzzlelibrary.web.rest.TestUtil;

public class PuzzleRentalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuzzleRental.class);
        PuzzleRental puzzleRental1 = new PuzzleRental();
        puzzleRental1.setId(1L);
        PuzzleRental puzzleRental2 = new PuzzleRental();
        puzzleRental2.setId(puzzleRental1.getId());
        assertThat(puzzleRental1).isEqualTo(puzzleRental2);
        puzzleRental2.setId(2L);
        assertThat(puzzleRental1).isNotEqualTo(puzzleRental2);
        puzzleRental1.setId(null);
        assertThat(puzzleRental1).isNotEqualTo(puzzleRental2);
    }
}
