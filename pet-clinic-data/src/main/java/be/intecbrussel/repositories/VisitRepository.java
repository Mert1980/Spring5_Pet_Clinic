package be.intecbrussel.repositories;

import be.intecbrussel.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
