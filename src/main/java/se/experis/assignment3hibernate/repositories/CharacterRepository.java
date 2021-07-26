package se.experis.assignment3hibernate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.assignment3hibernate.models.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
