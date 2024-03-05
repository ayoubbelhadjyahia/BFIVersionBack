package bfi.groupe.bfiversionback.controller;

import bfi.groupe.bfiversionback.Iservice.IserviceUser;
import bfi.groupe.bfiversionback.configuration.MessageResponse;
import bfi.groupe.bfiversionback.entity.ResetPassword;
import bfi.groupe.bfiversionback.entity.Token;
import bfi.groupe.bfiversionback.entity.Utilisateur;
import bfi.groupe.bfiversionback.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/User")
@CrossOrigin(origins = "*")
public class UserController {
    IserviceUser iserviceUser;
    TokenRepository tokenRepository;

    @GetMapping("/retrieve-all-user")
    public List<Utilisateur> GetAllUser() {
        return iserviceUser.GetALLUser();
    }

    @GetMapping("GetUsrById/{idU}")
    public Utilisateur GetUserById(@PathVariable("idU") Integer idU) {
        return iserviceUser.GetUserById(idU);
    }
    @PutMapping("/EditUser")
    public void EditUser(@RequestBody Utilisateur utilisateur){
        List<Token> tokens=tokenRepository.findAllTokenByUser(utilisateur.getId());
        for (Token i:tokens) {
            tokenRepository.deleteById(i.id);
        }
        iserviceUser.EditUser(utilisateur);}
    @DeleteMapping("/remove-user/{idU}")
    @ResponseBody
    public void removeUser(@PathVariable("idU") Integer idU) {
        List<Token> tokens=tokenRepository.findAllTokenByUser(idU);
        for (Token i:tokens) {
            tokenRepository.deleteById(i.id);
        }
        iserviceUser.DeleteUser(idU);
    }
}