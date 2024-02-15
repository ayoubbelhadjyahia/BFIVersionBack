package bfi.groupe.bfiversionback.controller;

import bfi.groupe.bfiversionback.Iservice.IserviceUser;
import bfi.groupe.bfiversionback.configuration.MessageResponse;
import bfi.groupe.bfiversionback.entity.ResetPassword;
import bfi.groupe.bfiversionback.entity.Utilisateur;
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

    @GetMapping("/retrieve-all-user")
    public List<Utilisateur> GetAllUser() {
        return iserviceUser.GetALLUser();
    }

    @GetMapping("GetUsrById/{idU}")
    public Utilisateur GetUserById(@PathVariable("idU") Integer idU) {
        return iserviceUser.GetUserById(idU);
    }

    @PostMapping("/add-user")
    @ResponseBody
    public ResponseEntity<?> addUser(@RequestBody Utilisateur utilisateur) {
        try {
            return ResponseEntity.ok(new MessageResponse(iserviceUser.addUser(utilisateur)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PutMapping("/EditUser")
    public void EditUser(@RequestBody Utilisateur utilisateur){
        iserviceUser.EditUser(utilisateur);

    }

    @DeleteMapping("/remove-user/{idU}")
    @ResponseBody
    public void removeUser(@PathVariable("idU") Integer idU) {
        iserviceUser.DeleteUser(idU);
    }

    @GetMapping("/Login/{username}/{password}")
    @ResponseBody
    public ResponseEntity<?> Login(@PathVariable("username") String username, @PathVariable("password") String password) {
        return iserviceUser.Login(username, password);
    }

    @GetMapping("/SendCode/{Email}")
    public ResponseEntity<?> SendCode(@PathVariable("Email") String Email) {
        try {
            return ResponseEntity.ok(new MessageResponse(iserviceUser.SendCode(Email)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("/resetPassword")
    ResponseEntity resetPassword(@RequestBody ResetPassword resetPassword) {
        return ResponseEntity.ok(new MessageResponse(iserviceUser.ResetPassword(resetPassword.getVerificationCode(),resetPassword.getNewPassword())));
    }
}