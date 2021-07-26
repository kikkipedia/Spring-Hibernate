package se.experis.assignment3hibernate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.assignment3hibernate.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
