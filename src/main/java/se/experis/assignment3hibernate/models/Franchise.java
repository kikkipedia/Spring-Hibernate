package se.experis.assignment3hibernate.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.Hidden;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Franchise {

    @Hidden
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="name")
    private String name;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "franchise", cascade = CascadeType.ALL)
    List<Movie> movies;

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

/*    public void setId(Long id) {
        this.id = id;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
