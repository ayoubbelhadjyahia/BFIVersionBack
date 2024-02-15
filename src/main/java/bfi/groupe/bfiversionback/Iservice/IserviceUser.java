package bfi.groupe.bfiversionback.Iservice;

import bfi.groupe.bfiversionback.entity.Utilisateur;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IserviceUser {
     List<Utilisateur> GetALLUser();
     void DeleteUser(Integer IdUser);
     String addUser(Utilisateur utilisateur);
     ResponseEntity<?> Login(String usernameOuEmail, String password);
     String SendCode(String Email);
     String ResetPassword(String Code, String NewPassword);
     void EditUser(Utilisateur U);
     Utilisateur GetUserById(Integer id);
}
