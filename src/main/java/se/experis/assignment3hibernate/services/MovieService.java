package se.experis.assignment3hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.experis.assignment3hibernate.models.Character;
import se.experis.assignment3hibernate.repositories.CharacterRepository;
import se.experis.assignment3hibernate.repositories.MovieRepository;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Character> getCharactersInMovie(Long id) {
        return movieRepository.getById(id).getCharacters();
    }

}
