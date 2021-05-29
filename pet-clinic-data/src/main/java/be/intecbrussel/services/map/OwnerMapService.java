package be.intecbrussel.services.map;

import be.intecbrussel.model.Owner;
import be.intecbrussel.model.Pet;
import be.intecbrussel.services.OwnerService;
import be.intecbrussel.services.PetService;
import be.intecbrussel.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"}) // if we don't specify an active profile, this profile will be default
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner save(Owner owner) {
        if(owner != null){
            persistPets(owner);
            return super.save(owner);
        } else {
            return null;
        }
    }

    private void persistPets(Owner object) {
        if(object.getPets() != null){
            object.getPets().forEach(pet -> persistPet(pet));
        }
    }

    private void persistPet(Pet pet){
        try {
            petTypeService.save(pet.getPetType());
        } catch (RuntimeException runtimeException){
            throw new RuntimeException("Pet Type is required");
        }

        if(pet.getId() == null){
            petService.save(pet);
        }
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return this.findAll().stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
}
