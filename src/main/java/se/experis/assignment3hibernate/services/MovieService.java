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

    public Movie updateMovieWithCharacters(Long id, ArrayList<Long> characterIds) {
        ArrayList<Character> characters = new ArrayList<>();
        Movie movie = movieRepository.findById(id).get();

        for (int i = 0; i < characterIds.size(); i++) {

            long characterId = characterIds.get(i);

            if (characterRepository.existsById(characterId)) {
                Character character = characterRepository.findById(characterId).get();
                characters.add(character);
            } else {
                System.out.println("Character with characterID: " + characterId + " doesn't exist.");
            }
        }

        movie.setCharacters(characters);
        return movie;
    }

}
