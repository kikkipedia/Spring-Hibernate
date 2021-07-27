package se.experis.assignment3hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.assignment3hibernate.models.Character;
import se.experis.assignment3hibernate.models.Franchise;
import se.experis.assignment3hibernate.repositories.FranchiseRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/franchises")
public class FranchiseController {

    @Autowired
    private FranchiseRepository franchiseRepository;

    @GetMapping
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises, status);
    }

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

    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise) {
        franchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(franchise, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable Long id, @RequestBody Franchise franchise) {
        HttpStatus status;
        Franchise returnFranchise = franchiseRepository.findById(id).get();
        if(returnFranchise == null){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnFranchise, status);
        }
        franchise.setId(id);
        returnFranchise = franchiseRepository.save(franchise);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnFranchise, status);
    }

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

    @GetMapping("/{id}/allMovies")
    public ResponseEntity<Franchise> getAllMoviesInFranchise(@PathVariable Long id) {
        HttpStatus status;
        return
    }

}
