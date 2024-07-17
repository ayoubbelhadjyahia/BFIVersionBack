package bfi.groupe.bfiversionback.service;

import bfi.groupe.bfiversionback.entity.Utilisateur;
import bfi.groupe.bfiversionback.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceUserTest {
    @InjectMocks
    private ServiceUser serviceUser;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Utilisateur existingUser;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        existingUser = new Utilisateur();
        existingUser.setId(1);
        existingUser.setPassword("existingPassword");
    }

    @Test
    void getALLUser() {
        List<Utilisateur> Users=new ArrayList<>();
        Utilisateur user1=new Utilisateur();
        user1.setId(1);
        user1.setUsername("test");
        Utilisateur user2=new Utilisateur();
        user2.setId(3);
        user2.setUsername("test2");
        Users.add(user1);
        Users.add(user2);
        when(userRepository.findAll()).thenReturn(Users);
        List<Utilisateur> result = serviceUser.GetALLUser();
        assertEquals(result,Users);
        System.err.println("test getAllUser : SUCCESS");
    }
    @Test
    void editUser() {
        Utilisateur updatedUser = new Utilisateur();
        updatedUser.setId(1);
        updatedUser.setPassword("newPassword");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        serviceUser.EditUser(updatedUser);
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(userRepository, times(1)).save(updatedUser);
        assertEquals("encodedNewPassword", updatedUser.getPassword());
        System.err.println("Test Edit User : SUCCESS");
 }


    @Test
    void getUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        Utilisateur result = serviceUser.GetUserById(1);
        assertNotNull(result);
        assertEquals(existingUser, result);
        System.err.println("testRetrieveUser : SUCCESS");
    }
}