package pl.maryniowski.apps.puzzlelibrary.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PuzzleRental.
 */
@Entity
@Table(name = "puzzle_rental")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PuzzleRental implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(unique = true)
    private PuzzleItem puzzleItem;

    @ManyToOne
    @JsonIgnoreProperties(value = "puzzleRentals", allowSetters = true)
    private PuzzlePerson puzzlePerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public PuzzleRental startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public PuzzleRental endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public PuzzleRental isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public PuzzleItem getPuzzleItem() {
        return puzzleItem;
    }

    public PuzzleRental puzzleItem(PuzzleItem puzzleItem) {
        this.puzzleItem = puzzleItem;
        return this;
    }

    public void setPuzzleItem(PuzzleItem puzzleItem) {
        this.puzzleItem = puzzleItem;
    }

    public PuzzlePerson getPuzzlePerson() {
        return puzzlePerson;
    }

    public PuzzleRental puzzlePerson(PuzzlePerson puzzlePerson) {
        this.puzzlePerson = puzzlePerson;
        return this;
    }

    public void setPuzzlePerson(PuzzlePerson puzzlePerson) {
        this.puzzlePerson = puzzlePerson;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PuzzleRental)) {
            return false;
        }
        return id != null && id.equals(((PuzzleRental) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PuzzleRental{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
