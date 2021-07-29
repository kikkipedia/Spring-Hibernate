package se.experis.assignment3hibernate.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.v3.oas.annotations.Hidden;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Movie {

    @Hidden
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    @Size(max=255)
    private String title;

    @Column(name="genre")
    @Size(max=255)
    private String genre;

    @Column(name="release_year")
    @Size(max=255)
    private String releaseYear;

    @Column(name="director")
    @Size(max=255)
    private String director;

    @Column(name="picture_url")
    @Size(max=255)
    private String pictureURL;

    @Column(name="trailer_url")
    @Size(max=255)
    private String trailerURL;

    /**
     * Many-To-Many-relation: One movie can have several characters and one character can be in many movies.
     * Movie has a join column character_movie that stores the id value and has a foreign key to the Character entity
     */
    @Hidden
    @ManyToMany
    @JoinTable(
            name = "character_movie",
            joinColumns = {@JoinColumn(name="movie_id")},
            inverseJoinColumns = {@JoinColumn(name="character_id")}
    )
    public List<Character> characters;

    /**
     * @return a map of Character objects
     */
    @JsonGetter("characters")
    public List<String> characters() {
        if(characters != null) {
            return characters.stream()
                    .map(character -> {
                        return "/api/v1/characters/" + character.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Many-To-One relationship with Franchise. Many Movies can have the same Franchise
     */
    @Hidden
    @ManyToOne
    @JoinColumn(name="franchise_id")
    public Franchise franchise;

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }

    public void setGenre(String genre) { this.genre = genre; }

    public String getReleaseYear() { return releaseYear; }

    public void setReleaseYear(String releaseYear) { this.releaseYear = releaseYear; }

    public String getDirector() { return director; }

    public void setDirector(String director) { this.director = director; }

    public String getPictureURL() { return pictureURL; }

    public void setPictureURL(String pictureURL) { this.pictureURL = pictureURL; }

    public String getTrailerURL() { return trailerURL; }

    public void setTrailerURL(String trailerURL) { this.trailerURL = trailerURL; }

    public List<Character> getCharacters() { return characters; }

    public void setCharacters(List<Character> characters) { this.characters = characters; }

    public Franchise getFranchise() { return franchise; }

    public void setFranchise(Franchise franchise) { this.franchise = franchise; }
}
