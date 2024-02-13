package bfi.groupe.bfiversionback.service;

import bfi.groupe.bfiversionback.Iservice.IserviceUser;
import bfi.groupe.bfiversionback.entity.Utilisateur;
import bfi.groupe.bfiversionback.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
public class ServiceUser implements IserviceUser{
UserRepository userRepository;
    final MailerService mailerService;
    private final RandomString randomString;

    @Autowired
    PasswordEncoderService passwordEncoder;

    @Override
    public List<Utilisateur> GetALLUser() {
        return userRepository.findAll();
    }
    @Override
    public void DeleteUser(Integer IdUser) {
    userRepository.deleteById(IdUser);
    }
    @Override
    public String addUser(Utilisateur utilisateur) {
        if(userRepository.GetUserByUsername(utilisateur.getUsername())!=null){
            return "nom existe deja";
        }else if(userRepository.GetUserByEmail(utilisateur.getEmail())!=null){
            return "email existe deja";
        }else

        utilisateur.setPassword(this.passwordEncoder.encodePassword(utilisateur.getPassword()));
            userRepository.save(utilisateur);
            return "ajouter avec succes";
    }
    @Override
    public ResponseEntity<?> Login(String username, String password) {
        if(userRepository.GetUserByUsername(username)==null&&userRepository.GetUserByEmail(username)==null){
            return ResponseEntity.ok("nom ou email est incorrect");
        }else if(!passwordEncoder.matches(password,userRepository.GetUserByUsername(username).getPassword())||!passwordEncoder.matches(password,userRepository.GetUserByEmail(username).getPassword())){
            return ResponseEntity.ok("mot de passe est incorrect");
        }else
        return ResponseEntity.ok(userRepository.GetUserByUsername(username));
    }

    @Override
    public String SendCode(String Email) {
        if(userRepository.GetUserByEmail(Email)==null){
            return "Email n'existe pas";
        }else{
            String code = randomString.randomGeneratedString(8);
            Utilisateur u =userRepository.GetUserByEmail(Email);
            u.setCodeVerification(code);
            u.setDateEndCode(LocalDateTime.now().plusMinutes(5));
            mailerService.sendEmail(u.getEmail(),"REST CODE","votre code de verification est :"+code+"\nNB:Le code ne fonctionne pas apres 5 minutes");
            userRepository.save(u);
            return "ok";
        }
    }

    @Override
    public ResponseEntity ResetPassword(String Code, String NewPassword) {
        Utilisateur u = userRepository.GetUserByCode(Code);
        if (u == null ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(u.getDateEndCode().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
        }
        else{
                u.setPassword(passwordEncoder.encodePassword(NewPassword));
                u.setCodeVerification(null);
                u.setDateEndCode(null);
                userRepository.save(u);
            }

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
