package pl.maryniowski.apps.puzzlelibrary.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.maryniowski.apps.puzzlelibrary.web.rest.TestUtil;

public class PuzzlePersonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuzzlePerson.class);
        PuzzlePerson puzzlePerson1 = new PuzzlePerson();
        puzzlePerson1.setId(1L);
        PuzzlePerson puzzlePerson2 = new PuzzlePerson();
        puzzlePerson2.setId(puzzlePerson1.getId());
        assertThat(puzzlePerson1).isEqualTo(puzzlePerson2);
        puzzlePerson2.setId(2L);
        assertThat(puzzlePerson1).isNotEqualTo(puzzlePerson2);
        puzzlePerson1.setId(null);
        assertThat(puzzlePerson1).isNotEqualTo(puzzlePerson2);
    }
}
