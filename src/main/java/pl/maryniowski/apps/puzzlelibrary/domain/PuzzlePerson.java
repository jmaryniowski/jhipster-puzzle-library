package pl.maryniowski.apps.puzzlelibrary.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PuzzlePerson.
 */
@Entity
@Table(name = "puzzle_person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PuzzlePerson implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "puzzlePerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PuzzleRental> puzzleRentals = new HashSet<>();

    @OneToMany(mappedBy = "puzzlePerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PuzzleItem> puzzleItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public PuzzlePerson user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<PuzzleRental> getPuzzleRentals() {
        return puzzleRentals;
    }

    public PuzzlePerson puzzleRentals(Set<PuzzleRental> puzzleRentals) {
        this.puzzleRentals = puzzleRentals;
        return this;
    }

    public PuzzlePerson addPuzzleRental(PuzzleRental puzzleRental) {
        this.puzzleRentals.add(puzzleRental);
        puzzleRental.setPuzzlePerson(this);
        return this;
    }

    public PuzzlePerson removePuzzleRental(PuzzleRental puzzleRental) {
        this.puzzleRentals.remove(puzzleRental);
        puzzleRental.setPuzzlePerson(null);
        return this;
    }

    public void setPuzzleRentals(Set<PuzzleRental> puzzleRentals) {
        this.puzzleRentals = puzzleRentals;
    }

    public Set<PuzzleItem> getPuzzleItems() {
        return puzzleItems;
    }

    public PuzzlePerson puzzleItems(Set<PuzzleItem> puzzleItems) {
        this.puzzleItems = puzzleItems;
        return this;
    }

    public PuzzlePerson addPuzzleItem(PuzzleItem puzzleItem) {
        this.puzzleItems.add(puzzleItem);
        puzzleItem.setPuzzlePerson(this);
        return this;
    }

    public PuzzlePerson removePuzzleItem(PuzzleItem puzzleItem) {
        this.puzzleItems.remove(puzzleItem);
        puzzleItem.setPuzzlePerson(null);
        return this;
    }

    public void setPuzzleItems(Set<PuzzleItem> puzzleItems) {
        this.puzzleItems = puzzleItems;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PuzzlePerson)) {
            return false;
        }
        return id != null && id.equals(((PuzzlePerson) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PuzzlePerson{" +
            "id=" + getId() +
            "}";
    }
}
