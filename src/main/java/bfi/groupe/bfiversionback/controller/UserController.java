package bfi.groupe.bfiversionback.controller;

import bfi.groupe.bfiversionback.iservice.IserviceUser;
import bfi.groupe.bfiversionback.auditing.ApplicationAuditAware;
import bfi.groupe.bfiversionback.entity.ChangeLang;
import bfi.groupe.bfiversionback.entity.Token;
import bfi.groupe.bfiversionback.entity.Utilisateur;
import bfi.groupe.bfiversionback.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/User")

@PreAuthorize("hasAnyRole('ADMIN','USER')")
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
    @PutMapping("ChangeLang")
    public ResponseEntity ChangeLang(@RequestBody ChangeLang changeLang) {
         iserviceUser.ChangeLang(changeLang.getId(),changeLang.getLang());
         return ResponseEntity.ok("Langue is changed");
    }

    @PutMapping("/EditUser")
    public ResponseEntity EditUser(@RequestBody Utilisateur utilisateur) {
        ApplicationAuditAware a = new ApplicationAuditAware();
        if (a.getCurrentAuditor().get() == utilisateur.getId() || iserviceUser.GetUserById(a.getCurrentAuditor().get()).getRole().toString().equals("ADMIN")) {
            List<Token> tokens = tokenRepository.findAllTokenByUser(utilisateur.getId());
            for (Token i : tokens) {
                tokenRepository.deleteById(i.id);
            }
            iserviceUser.EditUser(utilisateur);
            return ResponseEntity.ok("Modifié avec succés");
        }
        return ResponseEntity.status(403).build();
    }
    @PreAuthorize("hasAuthority('admin:delete')")
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