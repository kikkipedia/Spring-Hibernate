package se.experis.assignment3hibernate.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.assignment3hibernate.models.Character;
import se.experis.assignment3hibernate.models.Movie;
import se.experis.assignment3hibernate.repositories.MovieRepository;
import se.experis.assignment3hibernate.services.MovieService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    /**
     * GET all Movie objects in database
     * @return - Movie objects
     */
    @Operation(summary = "Get all movies from movie table.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displaying all movies.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) })
    })
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }

    /**
     * GET one specified Movie object.
     * @param id - identifier of Movie object that we want to fetch.
     *           Checks if desired object is to be found in the database.
     * @return Movie object
     */
    @Operation(summary = "Get movie by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found movie by Id.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie not found.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(
            @Parameter(description = "Id of movie to be searched.")
            @PathVariable Long id) {
        Movie movie = new Movie();
        HttpStatus status;

        if (movieRepository.existsById(id)) {
            status = HttpStatus.OK;
            movie = movieRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(movie, status);
    }

    /**
     * POST a new Movie object.
     * @param movie - Movie object to be added in database
     * @return saved Movie object
     */
    @Operation(summary = "Add a new movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie created.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) })
    })
    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(
            @Parameter(description = "Movie to add.")
            @RequestBody Movie movie) {
        movie = movieRepository.save(movie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(movie, status);
    }

    /**
     * UPDATE Movie object in database. Checks if values in the request body is empty, and
     * if not updates Movie with new values.
     * @param id - Identifier of Movie object to be updated
     * @param movie - Movie object from the request body
     * @return updated Movie object
     */
    @Operation(summary = "Updating specified movie with new information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found movie by Id. Updated movie with new information.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie not found.",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @Parameter(description = "Id of movie to update.")
            @PathVariable Long id,
            @Parameter(description = "Movie information to update with.")
            @RequestBody Movie movie) {
        HttpStatus status;
        Movie foundMovie = null;

        if (movieRepository.existsById(id)) {
            foundMovie = movieRepository.findById(id).get();

            String movieDirector = movie.getDirector();
            String movieGenre = movie.getGenre();
            String moviePictureURL = movie.getPictureURL();
            String movieReleaseYear = movie.getReleaseYear();
            String movieTitle = movie.getTitle();
            String movieTrailerURL = movie.getTrailerURL();

            if (!(movieDirector == null || movieDirector.isEmpty())){
                foundMovie.setDirector(movieDirector);
            }

            if (!(movieGenre == null || movieGenre.isEmpty())){
                foundMovie.setGenre(movieGenre);
            }

            if (!(moviePictureURL == null || moviePictureURL.isEmpty())){
                foundMovie.setPictureURL(moviePictureURL);
            }

            if (!(movieReleaseYear == null || movieReleaseYear.isEmpty())){
                foundMovie.setReleaseYear(movieReleaseYear);
            }

            if (!(movieTitle == null || movieTitle.isEmpty())){
                foundMovie.setTitle(movieTitle);
            }

            if (!(movieTrailerURL == null || movieTrailerURL.isEmpty())){
                foundMovie.setTrailerURL(movieTrailerURL);
            }

            status = HttpStatus.OK;
            movieRepository.save(foundMovie);
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(foundMovie, status);
    }

    /**
     * UPDATE the Character(s) in Movie
     * @param id - Movie identifier
     * @param characterIds - Array of Character object Ids from the request body
     * @return Movie object
     */
    @Operation(summary = "Updating specified movie with new characters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found movie by Id. Updated Movie with new characters.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie not found.",
                    content = @Content)
    })
    @PutMapping("/{id}/characters")
    public ResponseEntity<Movie> updateMovieCharacters(
            @Parameter(description = "Id of franchise to update.")
            @PathVariable Long id,
            @Parameter(description = "List of characters to update franchise with.")
            @RequestBody ArrayList<Long> characterIds) {
        HttpStatus status;
        Movie movie = new Movie();

        if (movieRepository.existsById(id)) {
            movie = movieService.updateMovieWithCharacters(id, characterIds);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        movie = movieRepository.save(movie);
        return new ResponseEntity<>(movie, status);
    }

    /**
     * DELETE Movie object
     * @param id - Movie identifier.
     * @return HttpStatus
     */
    @Operation(summary = "Delete movie by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted franchise with Id.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) }),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovieById(
            @Parameter(description = "Id of movie to delete.")
            @PathVariable Long id) {
        HttpStatus status;

        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

    /**
     * GET all Characters in one Movie
     * @param id - Movie identifier
     * @return List of Characters
     */
        return new ResponseEntity<>(status);
    }

    @Operation(summary = "Get all characters in movie by Id.")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Characters within a movie.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie not found.",
                    content = @Content)
    })
    @GetMapping("/{id}/allCharacters")
    public ResponseEntity<List<Character>> getAllCharactersInMovie(
            @Parameter(description = "Id of franchise to get all movies from.")
            @PathVariable Long id) {
        HttpStatus status;
        List<Character> characters = null;
        if (movieRepository.existsById(id)) {
            status = HttpStatus.OK;
            characters = movieService.getCharactersInMovie(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(characters, status);
    }



}
