package se.experis.assignment3hibernate.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.Hidden;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    @Size(max=255)
    private String name;

    @Column(name="description", columnDefinition = "TEXT")
    @Size(max=255)
    private String description;

    /**
     * One-To-Many-relation. A Franchise can have many Movies. A Movie can only belong
     * to one Franchise.
     * CascadeType.ALL replicates all actions to the Movie relation
     */
    @OneToMany(mappedBy = "franchise", cascade = CascadeType.ALL)
    List<Movie> movies;

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

    public Long getId() { return id; }

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
}
