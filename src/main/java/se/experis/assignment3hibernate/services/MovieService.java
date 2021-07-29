package se.experis.assignment3hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.experis.assignment3hibernate.models.Character;
import se.experis.assignment3hibernate.models.Movie;
import se.experis.assignment3hibernate.repositories.CharacterRepository;
import se.experis.assignment3hibernate.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for Movie controller
 */
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;

    /**
     * Get all Characters in a movie.
     *
     * @param id - Id of the movie to get all characters from.
     * @return A List of Characters.
     */
    public List<Character> getCharactersInMovie(Long id) {
        return movieRepository.getById(id).getCharacters();
    }

    /**
     * Update movie with characters.
     * @param id - Id of movie to update with characters.
     * @param characterIds - List of character Ids (Long) to add to a movies List of Characters.
     * @return Movie containing the new characters.
     */
    public Movie updateMovieWithCharacters(Long id, ArrayList<Long> characterIds) {
        ArrayList<Character> characters = new ArrayList<>();
        Movie movie = movieRepository.findById(id).get();

        // Loop through list of character IDs and add to a list of Characters.
        for (int i = 0; i < characterIds.size(); i++) {

            long characterId = characterIds.get(i);

            if (characterRepository.existsById(characterId)) {
                Character character = characterRepository.findById(characterId).get();
                characters.add(character);
            } else {
                System.out.println("Character with characterID: " + characterId + " doesn't exist.");
            }
        }

        // Add list of characters to movie.
        movie.setCharacters(characters);
        return movie;
    }


}
