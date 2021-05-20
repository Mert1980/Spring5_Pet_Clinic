package be.intecbrussel.repositories;

import be.intecbrussel.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
