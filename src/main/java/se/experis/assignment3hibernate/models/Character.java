package se.experis.assignment3hibernate.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="full_name")
    @Size(max=255)
    private String fullName;

    @Column(name="alias")
    @Size(max=255)
    private String alias;

    @Column(name="gender")
    @Size(max=255)
    private String gender;

    @Column(name="picture_url")
    @Size(max=255)
    private String pictureURL;

    /**
     * Many-To-Many-relation: A character can be in several movies. One movie can have several characters.
     * Character has a join column character_movie that stores the id value and has a foreign key to the Movie entity
     */
    @ManyToMany
    @JoinTable(
            name = "character_movie",
            joinColumns = {@JoinColumn(name="character_id")},
            inverseJoinColumns = {@JoinColumn(name="movie_id")}
    )
    public List<Movie> movies;

    /**
     * @return a map of movie objects
     */
    @JsonGetter("movies")
    public List<String> movies() {
        if(movies != null) {
            return movies.stream()
                    .map(movie -> {
                        return "/api/v1/movies/" + movie.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPictureURL() { return pictureURL; }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}
