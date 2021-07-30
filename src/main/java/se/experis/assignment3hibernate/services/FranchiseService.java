package se.experis.assignment3hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.experis.assignment3hibernate.models.Character;
import se.experis.assignment3hibernate.models.Franchise;
import se.experis.assignment3hibernate.models.Movie;
import se.experis.assignment3hibernate.repositories.FranchiseRepository;
import se.experis.assignment3hibernate.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for Franchise controller
 */
@Service
public class FranchiseService {
    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Get all movies in a franchise.
     *
     * @param id - Id of the franchise to get all movies from.
     * @return A list of movies.
     */
    public List<Movie> getAllMoviesInFranchise(Long id) {
        Franchise franchise = franchiseRepository.findById(id).get();
        return franchise.getMovies();
    }

    /**
     * Get all Characters in a franchise.
     *
     * @param id - Id of the franchise to get all characters from.
     * @return A List of Characters.
     */
    public List<Character> getAllCharactersInFranchise(Long id) {
        ArrayList<Character> characters = new ArrayList<>();
        List<Movie> allMovies = getAllMoviesInFranchise(id);

        // Loop through every movie in franchise and get the characters by saving into an
        // arrayList of characters.
        for (Movie movie : allMovies) {
            for (Character character : movie.getCharacters()) {
                if (!characters.contains(character)) {
                    characters.add(character);
                }
            }
        }
        return characters;
    }

    /**
     * Update franchise with a list of movies.
     *
     * @param id - Id of the franchise to update with movies.
     * @return A franchise containing the newly added movies.
     */
    public Franchise updateFranchise(Long id, ArrayList<Long> movieIds) {
        Franchise franchise = franchiseRepository.findById(id).get();

        // Set FranchiseId of movies to null.
        setFranchiseIdInMoviesToNull(franchise);
//        for (Movie movie : franchise.getMovies()) {
//            movie.setFranchise(null);
//            movieRepository.save(movie);
//        }

        // Loop through the movieIds and get the movie.
        // Set the franchise of the movie to the franchise supplied (id) and save.
        for (int i = 0; i < movieIds.size(); i++) {

            long movieId = movieIds.get(i);

            if (movieRepository.existsById(movieId)) {
                Movie movie = movieRepository.findById(movieId).get();
                movie.setFranchise(franchise);
                movieRepository.save(movie);
            } else {
                System.out.println("Movie with movieID: " + movieId + " doesn't exist.");
            }
        }

        return franchise;
    }


    /**
     * Set franchiseId to null for every movie in franchise.
     *
     * @param franchise - The franchise to get all movies from.
     */
    public void setFranchiseIdInMoviesToNull(Franchise franchise) {

        List<Movie> movies = franchise.getMovies();

        for (Movie movie: movies) {
            movie.setFranchise(null);
            movieRepository.save(movie);
        }
    }
}
