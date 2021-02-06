package pl.maryniowski.apps.puzzlelibrary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pl.maryniowski.apps.puzzlelibrary.domain.enumeration.PuzzleType;

/**
 * A PuzzleItem.
 */
@Entity
@Table(name = "puzzle_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PuzzleItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PuzzleType type;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @OneToOne(mappedBy = "puzzleItem")
    @JsonIgnore
    private PuzzleRental puzzleRental;

    @ManyToOne
    @JsonIgnoreProperties(value = "puzzleItems", allowSetters = true)
    private PuzzlePerson puzzlePerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PuzzleItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public PuzzleItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PuzzleType getType() {
        return type;
    }

    public PuzzleItem type(PuzzleType type) {
        this.type = type;
        return this;
    }

    public void setType(PuzzleType type) {
        this.type = type;
    }

    public byte[] getPicture() {
        return picture;
    }

    public PuzzleItem picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public PuzzleItem pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public PuzzleRental getPuzzleRental() {
        return puzzleRental;
    }

    public PuzzleItem puzzleRental(PuzzleRental puzzleRental) {
        this.puzzleRental = puzzleRental;
        return this;
    }

    public void setPuzzleRental(PuzzleRental puzzleRental) {
        this.puzzleRental = puzzleRental;
    }

    public PuzzlePerson getPuzzlePerson() {
        return puzzlePerson;
    }

    public PuzzleItem puzzlePerson(PuzzlePerson puzzlePerson) {
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
        if (!(o instanceof PuzzleItem)) {
            return false;
        }
        return id != null && id.equals(((PuzzleItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PuzzleItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            "}";
    }
}
