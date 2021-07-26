package se.experis.assignment3hibernate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.assignment3hibernate.models.Franchise;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
}
