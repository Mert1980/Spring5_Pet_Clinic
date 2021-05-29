package be.intecbrussel.services.map;

import be.intecbrussel.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class OwnerMapServiceTest {
    OwnerMapService ownerMapService;
    private final Long ownerId = 1L;
    private final String lastname = "Smith";
    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName("Smith").build());
    }

    @Test
    void findAll() {
        assertNotNull(ownerMapService.findAll());
        assertEquals(1, ownerMapService.findAll().size());
    }

    @Test
    void findById() {
        assertNotNull(ownerMapService.findById(ownerId));
        assertEquals(ownerId, ownerMapService.findById(ownerId).getId());
    }

    @Test
    void save() {
        ownerMapService.save(Owner.builder().build());
        assertEquals(2, ownerMapService.findAll().size());
    }

    @Test
    void doNotSaveDuplicateOwners() {
        Owner owner = ownerMapService.findById(ownerId);
        System.out.println(owner.getId()); // 1

        ownerMapService.save(ownerMapService.findById(ownerId));
        assertEquals(1, ownerMapService.findAll().size());
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner owner = Owner.builder().build();
        Owner savedOwner = ownerMapService.save(owner);
        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().build());

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        Owner owner = ownerMapService.findById(ownerId);
        ownerMapService.delete(owner);
        assertNull(ownerMapService.findById(ownerId));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        assertNull(ownerMapService.findById(ownerId));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerMapService.findByLastName(lastname);
        assertNotNull(owner);
        assertEquals("Smith", owner.getLastName());
    }
}