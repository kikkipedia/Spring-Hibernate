package se.experis.assignment3hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.assignment3hibernate.models.Character;
import se.experis.assignment3hibernate.repositories.CharacterRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/characters")
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = characterRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(characters, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) {
        Character character = new Character();
        HttpStatus status;

        if (characterRepository.existsById(id)) {
            status = HttpStatus.OK;
            character = characterRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(character, status);
    }

    @PostMapping
    public ResponseEntity<Character> addCharacter(@RequestBody Character character) {
        character = characterRepository.save(character);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(character, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable Long id, @RequestBody Character character) {
        HttpStatus status;
        Character returnCharacter = characterRepository.findById(id).get();

        if(returnCharacter == null){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnCharacter, status);
        }
        character.setId(id);
        returnCharacter = characterRepository.save(character);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnCharacter, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Character> deleteCharacterById(@PathVariable Long id) {
        HttpStatus status;
        Character returnCharacter = characterRepository.findById(id).get();
        if(returnCharacter == null){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnCharacter, status);
        }
        characterRepository.deleteById(id);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(status);
    }
}
