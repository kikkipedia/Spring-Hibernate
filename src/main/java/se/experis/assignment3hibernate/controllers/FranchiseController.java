package se.experis.assignment3hibernate.controllers;

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

    /**
     * GET one specified Franchise object.
     * @param id - identifier of Franchise object that we want to fetch.
     *           Checks if desired object is to be found in the database.
     * @return Franchise object
     */
    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchiseById(@PathVariable Long id) {
        Franchise franchise = new Franchise();
        HttpStatus status;

        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            franchise = franchiseRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(franchise, status);
    }

    /**
     * POST a new Franchise object.
     * @param franchise - Franchise object to be added in database
     * @return saved Franchise object
     */
    @PostMapping("/add")
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise) {
        franchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(franchise, status);
    }

    /**
     * UPDATE Franchise object in database. Checks if values in the request body is empty, and
     * if not updates Franchise with new values.
     * @param id - Franchise identifier
     * @param franchise - Franchise object from the request body
     * @return updated Franchise object
     */
    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable Long id, @RequestBody Franchise franchise) {
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

    /**
     * UPDATES Movie(s) in one Franchise objects
     * @param id - Franchise identifier.
     * @param movieIds - Array of Movie object Ids from the request body
     * @return updated Franchise object
     */
    @PutMapping("/{id}/movies")
    public ResponseEntity<Franchise> updateFranchiseInMovie(@PathVariable Long id, @RequestBody ArrayList<Long> movieIds) {
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
     * @param id - Franchise identifier.
     * @return HttpStatus
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Franchise> deleteFranchiseById(@PathVariable Long id) {
        HttpStatus status;
        Franchise returnFranchise = franchiseRepository.findById(id).get();
        if(returnFranchise == null){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnFranchise, status);
        }
        franchiseRepository.deleteById(id);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(status);
    }

    /**
     * GET all Movies in one Franchise
     * @param id - Franchise identifier
     * @return List of Movies
     */
    @GetMapping("/{id}/allMovies")
    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(@PathVariable Long id) {
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
     * @param id - Franchise identifier
     * @return List of Characters
     */
    @GetMapping("/{id}/allCharacters")
    public ResponseEntity<List<Character>> getAllCharactersInFranchise(@PathVariable Long id) {
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
