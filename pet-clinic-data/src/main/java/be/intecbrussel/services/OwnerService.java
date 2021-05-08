package be.intecbrussel.services;

import be.intecbrussel.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
