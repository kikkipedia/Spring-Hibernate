package se.experis.assignment3hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.experis.assignment3hibernate.models.Character;
import se.experis.assignment3hibernate.models.Movie;
import se.experis.assignment3hibernate.repositories.CharacterRepository;
import se.experis.assignment3hibernate.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;

    public List<Character> getCharactersInMovie(Long id) {
        return movieRepository.getById(id).getCharacters();
    }

    public Movie updateMovieWithCharacters(Long id, Long[] characterIds) {

        Movie movie = new Movie();
        ArrayList<Character> characters = new ArrayList<>();

        movie = movieRepository.findById(id).get();
        // We want update the movies characters with ids in intArray.
        for (int i = 0; i < characterIds.length; i++) {
            if (characterRepository.existsById(characterIds[i])) {
                Character character = characterRepository.findById(characterIds[i]).get();
                characters.add(character);
            } else {
                System.out.println("Character with characterID: " + characterIds[i] + " doesn't exist.");
            }
        }

        movie.setCharacters(characters);
        return movie;

    }

}
