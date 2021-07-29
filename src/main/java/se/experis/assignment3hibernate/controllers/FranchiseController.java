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
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class)) })
    })
    @GetMapping("/all")
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises, status);
    }

    @Operation(summary = "Get franchise by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found franchise by Id.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class)) }),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content)
    })
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
            return  new ResponseEntity<>(status);
        }

        return new ResponseEntity<>(franchise, status);
    }

    @Operation(summary = "Add a new franchise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Franchise created.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class)) })
    })
    @PostMapping("/add")
    public ResponseEntity<Franchise> addFranchise(
            @Parameter(description = "Franchise to add.")
            @RequestBody Franchise franchise) {
        franchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(franchise, status);
    }

    @Operation(summary = "Updating specified franchise with new information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found franchise by Id. Updated franchise with new information.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class)) }),
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

            if (!(franchiseName == null || franchiseName.isEmpty())){
                foundFranchise.setName(franchiseName);
            }

            if (!(franchiseDescription == null || franchiseDescription.isEmpty())){
                foundFranchise.setDescription(franchiseDescription);
            }

            status = HttpStatus.OK;
            franchiseRepository.save(foundFranchise);
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(foundFranchise, status);
    }

    @Operation(summary = "Updating specified franchise with new movies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Found franchise by Id. Updated franchise with new movies.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class)) }),
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
            franchiseRepository.deleteById(id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

        return new ResponseEntity<>(status);
    }

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

    @Operation(summary = "Get all characters in franchise by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Characters within a franchise.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class)) }),
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
