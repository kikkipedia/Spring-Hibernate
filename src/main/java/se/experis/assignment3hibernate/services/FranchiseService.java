package se.experis.assignment3hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.experis.assignment3hibernate.models.Movie;
import se.experis.assignment3hibernate.models.Character;
import se.experis.assignment3hibernate.repositories.CharacterRepository;
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
    @Autowired
    private CharacterRepository characterRepository;

    public List<Movie> getAllMoviesInFranchise(Long id) {
        return franchiseRepository.getById(id).getMovies();
    }

    public List<Character> getAllCharactersInFranchise(Long id) {
        ArrayList<Character> characters = new ArrayList<>();
        var allMovies = franchiseRepository.getById(id).getMovies();
        for (Movie movie: allMovies) {
            for (Character character: movie.getCharacters()) {
                characters.add(character);
            }
        }
        return characters;
    }
}
