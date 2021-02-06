package pl.maryniowski.apps.puzzlelibrary.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.maryniowski.apps.puzzlelibrary.web.rest.TestUtil;

public class PuzzleItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuzzleItem.class);
        PuzzleItem puzzleItem1 = new PuzzleItem();
        puzzleItem1.setId(1L);
        PuzzleItem puzzleItem2 = new PuzzleItem();
        puzzleItem2.setId(puzzleItem1.getId());
        assertThat(puzzleItem1).isEqualTo(puzzleItem2);
        puzzleItem2.setId(2L);
        assertThat(puzzleItem1).isNotEqualTo(puzzleItem2);
        puzzleItem1.setId(null);
        assertThat(puzzleItem1).isNotEqualTo(puzzleItem2);
    }
}
