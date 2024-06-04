package bfi.groupe.bfiversionback.controller;

import bfi.groupe.bfiversionback.Iservice.IserviceUser;
import bfi.groupe.bfiversionback.auth.AuthenticationRequest;
import bfi.groupe.bfiversionback.configuration.MessageResponse;
import bfi.groupe.bfiversionback.entity.ResetPassword;
import bfi.groupe.bfiversionback.service.AuthenticationService;
import bfi.groupe.bfiversionback.entity.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final IserviceUser iserviceUser;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Utilisateur request) {
        return ResponseEntity.ok(service.register(request)).getBody();
    }
    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request)).getBody();
    }
    @GetMapping("/GetCurrentUser/{username}")
    public Utilisateur getCurrentUser(@PathVariable("username") String username){
        return service.getCurrentUserByUsername(username);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
    @GetMapping("/SendCode/{Email}")
    public ResponseEntity sendCode(@PathVariable("Email") String Email) {
        try {
            return ResponseEntity.ok(new MessageResponse(iserviceUser.SendCode(Email)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }}
    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody ResetPassword resetPassword) {
        return ResponseEntity.ok(new MessageResponse(iserviceUser.ResetPassword(resetPassword.getVerificationCode(),resetPassword.getNewPassword())));
    }


}