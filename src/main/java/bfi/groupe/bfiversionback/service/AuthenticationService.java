package bfi.groupe.bfiversionback.service;

import bfi.groupe.bfiversionback.auth.AuthenticationRequest;
import bfi.groupe.bfiversionback.auth.AuthenticationResponse;
import bfi.groupe.bfiversionback.entity.UrlServer;
import bfi.groupe.bfiversionback.security.JwtService;
import bfi.groupe.bfiversionback.entity.Token;
import bfi.groupe.bfiversionback.entity.TokenType;
import bfi.groupe.bfiversionback.entity.Utilisateur;
import bfi.groupe.bfiversionback.repository.TokenRepository;
import bfi.groupe.bfiversionback.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ServiceUser serviceUser;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity register(Utilisateur request) {
        if(repository.GetUserByUsername(request.getUsername())!=null){
            return ResponseEntity.ok("nom existe deja");
        }else if(repository.GetUserByEmail(request.getEmail())!=null){
            return ResponseEntity.ok("email existe deja");
        }else

            request.setPassword(this.passwordEncoder.encode(request.getPassword()));
        request.setLang("fr");
        repository.save(request);
        var jwtToken = jwtService.generateToken(request);
        var refreshToken = jwtService.generateRefreshToken(request);
        saveUserToken(request, jwtToken);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    public ResponseEntity authenticate(AuthenticationRequest request) {
        if(repository.GetUserByEmail(request.getEmail())==null){
                return ResponseEntity.ok("Email est incorrect");
            }else
            if(!passwordEncoder.matches(request.getPassword(),repository.GetUserByEmail(request.getEmail()).getPassword())){
                return ResponseEntity.ok("Mot de passe est incorrect");
            }

        UrlServer baseUrl =serviceUser.GetUrlServer();
            if(baseUrl==null){
                UrlServer a=new UrlServer(0,"192.168.230.3","192.168.230.2:8081");
                serviceUser.AddUrlServer(a);
            }
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    private void saveUserToken(Utilisateur user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Utilisateur user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    public Utilisateur getCurrentUserByUsername(String username){
        return repository.GetUserByUsername(username);
    }
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
