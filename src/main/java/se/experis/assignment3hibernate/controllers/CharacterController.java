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
import se.experis.assignment3hibernate.repositories.CharacterRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/character")
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    /**
     * GET all Character objects in database
     *
     * @return - Character objects
     */
    @Operation(summary = "Get all characters from character table.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displaying all characters.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Character.class))})
    })
    @GetMapping("/all")
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = characterRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(characters, status);
    }

    /**
     * GET one specified Character object.
     *
     * @param id - identifier of Character object that we want to fetch.
     *           Checks if desired object is to be found in the database.
     * @return Character object
     */
    @Operation(summary = "Get character by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found character by Id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Character.class))}),
            @ApiResponse(responseCode = "404", description = "Character not found.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@Parameter(description = "Id of character to be searched.")
                                                      @PathVariable Long id) {
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

    /**
     * POST a new Character object.
     *
     * @param character - Character object to be added in database
     * @return saved Character object
     */
    @Operation(summary = "Add a new character.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character created.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Character.class))})
    })
    @PostMapping("/add")
    public ResponseEntity<Character> addCharacter(@Parameter(description = "character to add.")
                                                  @RequestBody Character character) {
        character = characterRepository.save(character);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(character, status);
    }

    /**
     * UPDATE Character object in database. Checks if values in the request body is empty, and
     * if not updates Character with new values.
     *
     * @param id        - Identifier of Character object to be updated
     * @param character - Character object from the request body
     * @return updated Character object
     */
    @Operation(summary = "Updating specified character with new information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found character by Id. Updated character with new information.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Character.class))}),
            @ApiResponse(responseCode = "404", description = "Character not found.",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@Parameter(description = "Id of character to update.")
                                                     @PathVariable Long id,
                                                     @Parameter(description = "character information to update with.")
                                                     @RequestBody Character character) {
        HttpStatus status;
        Character foundCharacter = null;

        if (characterRepository.existsById(id)) {
            foundCharacter = characterRepository.findById(id).get();

            String characterFullName = character.getFullName();
            String characterAlias = character.getAlias();
            String characterGender = character.getGender();
            String characterPictureURL = character.getPictureURL();

            if (!(characterFullName == null || characterFullName.isEmpty())) {
                foundCharacter.setFullName(characterFullName);
            }

            if (!(characterAlias == null || characterAlias.isEmpty())) {
                foundCharacter.setAlias(characterAlias);
            }

            if (!(characterGender == null || characterGender.isEmpty())) {
                foundCharacter.setGender(characterGender);
            }

            if (!(characterPictureURL == null || characterPictureURL.isEmpty())) {
                foundCharacter.setPictureURL(characterPictureURL);
            }

            status = HttpStatus.OK;
            characterRepository.save(foundCharacter);
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(foundCharacter, status);
    }

    /**
     * DELETE Character object
     * @param id - Character identifier.
     * @return HttpStatus
     */
    @Operation(summary = "Delete character by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted character with Id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Character.class))}),
            @ApiResponse(responseCode = "404", description = "Character not found.",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCharacterById(
            @Parameter(description = "Id of character to delete.")
            @PathVariable Long id) {
        HttpStatus status;

        if (characterRepository.existsById(id)) {
            status = HttpStatus.NO_CONTENT;
            characterRepository.deleteById(id);
        } else {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

        return new ResponseEntity<>(status);
    }
}
