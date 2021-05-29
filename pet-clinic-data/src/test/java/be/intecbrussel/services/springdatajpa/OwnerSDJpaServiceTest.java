package be.intecbrussel.services.springdatajpa;

import be.intecbrussel.model.Owner;
import be.intecbrussel.repositories.OwnerRepository;
import be.intecbrussel.repositories.PetRepository;
import be.intecbrussel.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
        Owner smith = ownerSDJpaService.findByLastName("Smith");

        assertEquals(LAST_NAME, smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> returnOwnerSet = new HashSet<>();
        returnOwnerSet.add(Owner.builder().id(1L).build());
        returnOwnerSet.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(returnOwnerSet);

        Set<Owner> ownerSet = ownerSDJpaService.findAll();
        assertNotNull(ownerSet);
        assertEquals(2, ownerSet.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnOwner));
        Owner owner = ownerSDJpaService.findById(1L);
        assertNotNull(owner);
        assertEquals(LAST_NAME, owner.getLastName());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = ownerSDJpaService.findById(1L);
        assertNull(owner);
    }

    @Test
    void save() {
        when(ownerRepository.save(any(Owner.class))).thenReturn(returnOwner);
        Owner owner = ownerSDJpaService.save(Owner.builder().build());
        assertNotNull(owner);
        assertEquals(LAST_NAME, owner.getLastName());
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(returnOwner);

        // verify delete() method in ownerRepository is called once when delete() method in service is called
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(1L);
        verify(ownerRepository).deleteById(anyLong());
    }
}