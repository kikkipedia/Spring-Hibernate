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

@Service
public class FranchiseService {
    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMoviesInFranchise(Long id) {
        Franchise franchise = franchiseRepository.findById(id).get();
        return franchise.getMovies();
    }

    public List<Character> getAllCharactersInFranchise(Long id) {
        ArrayList<Character> characters = new ArrayList<>();
        List<Movie> allMovies = getAllMoviesInFranchise(id);
        for (Movie movie: allMovies) {
            for (Character character: movie.getCharacters()) {
                if (!characters.contains(character)){
                    characters.add(character);
                }
            }
        }
        return characters;
    }

    public Franchise updateFranchise(Long id, ArrayList<Long> movieIds) {
        Franchise franchise = franchiseRepository.findById(id).get();

        for (Movie movie: franchise.getMovies()) {
            movie.setFranchise(null);
            movieRepository.save(movie);
        }

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
}
