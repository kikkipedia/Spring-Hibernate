package se.experis.assignment3hibernate.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.v3.oas.annotations.Hidden;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Character {

    @Hidden
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="full_name")
    private String fullName;
    @Column(name="alias")
    private String alias;
    @Column(name="gender")
    private String gender;
    @Column(name="picture_url")
    private String pictureURL;

    @ManyToMany
    @JoinTable(
            name = "character_movie",
            joinColumns = {@JoinColumn(name="character_id")},
            inverseJoinColumns = {@JoinColumn(name="movie_id")}
    )
    public List<Movie> movies;

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

    /*public void setId(Long id) {
        this.id = id;
    }*/

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

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}
