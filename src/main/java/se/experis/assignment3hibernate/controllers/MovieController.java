package se.experis.assignment3hibernate.controllers;

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

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
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

    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        movie = movieRepository.save(movie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(movie, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
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

    @PutMapping("/{id}/characters")
    public ResponseEntity<Movie> updateMovieCharacters(@PathVariable Long id, @RequestBody ArrayList<Long> characterIds) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovieById(@PathVariable Long id) {
        HttpStatus status;
        Movie returnMovie = movieRepository.findById(id).get();
        if(returnMovie == null){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnMovie, status);
        }
        movieRepository.deleteById(id);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(status);
    }

    @GetMapping("/{id}/allCharacters")
    public ResponseEntity<List<Character>> getAllCharactersInMovie(@PathVariable Long id) {
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
