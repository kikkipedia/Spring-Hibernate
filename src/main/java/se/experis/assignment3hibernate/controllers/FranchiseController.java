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
import se.experis.assignment3hibernate.models.Franchise;
import se.experis.assignment3hibernate.models.Movie;
import se.experis.assignment3hibernate.repositories.FranchiseRepository;
import se.experis.assignment3hibernate.services.FranchiseService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/franchise")
public class FranchiseController {

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private FranchiseService franchiseService;

    @Operation(summary = "Get all franchises from franchise table.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displaying all franchises.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))})
    })
    /**
     * GET all Franchise objects in database
     * @return - Franchise objects
     */
    @GetMapping("/all")
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises, status);
    }

    @Operation(summary = "Get franchise by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found franchise by Id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content)
    })
    /**
     * GET one specified Franchise object.
     * @param id - identifier of Franchise object that we want to fetch.
     *           Checks if desired object is to be found in the database.
     * @return Franchise object
     */
    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchiseById(
            @Parameter(description = "Id of franchise to be searched.")
            @PathVariable Long id) {
        Franchise franchise;
        HttpStatus status;

        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            franchise = franchiseRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

        return new ResponseEntity<>(franchise, status);
    }

    /**
     * POST a new Franchise object.
     *
     * @param franchise - Franchise object to be added in database
     * @return saved Franchise object
     */
    @Operation(summary = "Add a new franchise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Franchise created.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))})
    })
    @PostMapping("/add")
    public ResponseEntity<Franchise> addFranchise(
            @Parameter(description = "Franchise to add.")
            @RequestBody Franchise franchise) {
        franchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(franchise, status);
    }

    /**
     * UPDATE Franchise object in database. Checks if values in the request body is empty, and
     * if not updates Franchise with new values.
     *
     * @param id        - Franchise identifier
     * @param franchise - Franchise object from the request body
     * @return updated Franchise object
     */
    @Operation(summary = "Updating specified franchise with new information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found franchise by Id. Updated franchise with new information.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(
            @Parameter(description = "Id of franchise to update.")
            @PathVariable Long id,
            @Parameter(description = "Franchise information to update with.")
            @RequestBody Franchise franchise) {
        HttpStatus status;
        Franchise foundFranchise = null;

        if (franchiseRepository.existsById(id)) {
            foundFranchise = franchiseRepository.findById(id).get();

            String franchiseName = franchise.getName();
            String franchiseDescription = franchise.getDescription();

            if (!(franchiseName == null || franchiseName.isEmpty())) {
                foundFranchise.setName(franchiseName);
            }

            if (!(franchiseDescription == null || franchiseDescription.isEmpty())) {
                foundFranchise.setDescription(franchiseDescription);
            }

            status = HttpStatus.OK;
            franchiseRepository.save(foundFranchise);
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(foundFranchise, status);
    }

    /**
     * UPDATES Movie(s) in one Franchise objects
     *
     * @param id       - Franchise identifier.
     * @param movieIds - Array of Movie object Ids from the request body
     * @return updated Franchise object
     */
    @Operation(summary = "Updating specified franchise with new movies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Found franchise by Id. Updated franchise with new movies.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content)
    })
    @PutMapping("/{id}/movies")
    public ResponseEntity<Franchise> updateFranchiseInMovie(
            @Parameter(description = "Id of franchise to update.")
            @PathVariable Long id,
            @Parameter(description = "List of movies to update franchise with.")
            @RequestBody ArrayList<Long> movieIds) {
        HttpStatus status;
        Franchise franchise = new Franchise();

        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.NO_CONTENT;
            franchise = franchiseService.updateFranchise(id, movieIds);
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(franchise, status);
    }

    /**
     * DELETE Franchise object
     *
     * @param id - Franchise identifier.
     * @return HttpStatus
     */
    @Operation(summary = "Delete franchise by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted franchise with Id.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Franchise> deleteFranchiseById(
            @Parameter(description = "Id of franchise to delete.")
            @PathVariable Long id) {
        HttpStatus status;

        if (franchiseRepository.existsById(id)) {
            franchiseService.setFranchiseIdInMoviesToNull(franchiseRepository.findById(id).get());
            franchiseRepository.deleteById(id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

        return new ResponseEntity<>(status);
    }

    /**
     * GET all Movies in one Franchise
     *
     * @param id - Franchise identifier
     * @return List of Movies
     */
    @Operation(summary = "Get all movies in franchise by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movies within a franchise.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content)
    })
    @GetMapping("/{id}/allMovies")
    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(
            @Parameter(description = "Id of franchise to get all movies from.")
            @PathVariable Long id) {
        HttpStatus status;
        List<Movie> movies = null;
        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            movies = franchiseService.getAllMoviesInFranchise(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movies, status);
    }

    /**
     * GET all Character in one Franchise
     *
     * @param id - Franchise identifier
     * @return List of Characters
     */
    @Operation(summary = "Get all characters in franchise by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Characters within a franchise.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content)
    })
    @GetMapping("/{id}/allCharacters")
    public ResponseEntity<List<Character>> getAllCharactersInFranchise(
            @Parameter(description = "Id of franchise to get all characters from.")
            @PathVariable Long id) {
        HttpStatus status;
        List<Character> characters = null;
        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            characters = franchiseService.getAllCharactersInFranchise(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(characters, status);
    }

}
