package bfi.groupe.bfiversionback.service;

import bfi.groupe.bfiversionback.Iservice.IserviceUser;
import bfi.groupe.bfiversionback.entity.UrlServer;
import bfi.groupe.bfiversionback.entity.Utilisateur;
import bfi.groupe.bfiversionback.repository.UrlServerREpository;
import bfi.groupe.bfiversionback.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceUser implements IserviceUser{
    UserRepository userRepository;
    UrlServerREpository urlServerRepository;
    final MailerService mailerService;

    private final RandomString randomString;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Utilisateur> GetALLUser() {
        return userRepository.findAll();
    }
    @Override
    public void DeleteUser(Integer idUser) {
    userRepository.deleteById(idUser);
    }
    @Override
    public String SendCode(String email) {
        if(userRepository.GetUserByEmail(email)==null){
            return "Email n'existe pas";
        }else{
            String code = randomString.randomGeneratedString(8);
            Utilisateur u =userRepository.GetUserByEmail(email);
            u.setCodeVerification(code);
            u.setDateEndCode(LocalDateTime.now().plusMinutes(5));
            mailerService.sendEmail(u.getEmail(),"REST CODE","votre code de verification est :"+code+"\nNB:Le code ne fonctionne pas apres 5 minutes");
            userRepository.save(u);
            return "ok";
        }
    }

    @Override
    public String ResetPassword(String code, String newPassword) {
        Utilisateur u = userRepository.GetUserByCode(code);
        if (u == null ){
            return "code est incorrect";
        }
        else if(u.getDateEndCode().isBefore(LocalDateTime.now())) {
            return "date expirer";
        }
        else{
                u.setPassword(passwordEncoder.encode(newPassword));
                u.setCodeVerification(null);
                u.setDateEndCode(null);
                userRepository.save(u);
            }
        return "OK";
    }
    @Override
    public void EditUser(Utilisateur utilisateur) {
        Utilisateur u=userRepository.findById(utilisateur.getId()).orElse(null);
        if(u!=null) {
            if (!u.getPassword().equals(utilisateur.getPassword())) {
                utilisateur.setPassword(this.passwordEncoder.encode(utilisateur.getPassword()));
            }
            userRepository.save(utilisateur);
        }
    }

    @Override
    public Utilisateur GetUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void ChangeLang(Integer id, String lang) {
        Utilisateur a=userRepository.findById(id).orElse(null);
        if(a!=null) {
            a.setLang(lang);
            userRepository.save(a);
        }


    }

    @Override
    public UrlServer AddUrlServer(UrlServer urlServer) {
        List<UrlServer> u=urlServerRepository.findAll();
if(u.isEmpty()){
    System.out.println(urlServer);
    urlServerRepository.save(urlServer);
    return urlServer;
            }
return null;
    }

    @Override
    public UrlServer SetUrlServerArtifactory(String port) {
        List<UrlServer> urlServers = urlServerRepository.findAll();
        if (!urlServers.isEmpty()) {
            UrlServer u = urlServers.get(0);
            u.setUrlArtifactory(port);
            urlServerRepository.save(u);
            return u;
        }
        return null;
    }
    @Override
    public UrlServer SetUrlServerGit(String port) {
        List<UrlServer> urlServers = urlServerRepository.findAll();
        if (!urlServers.isEmpty()) {
            UrlServer u = urlServers.get(0);
            u.setUrlGitLab(port);
            urlServerRepository.save(u);
            return u;
        }
        return null;
    }

    @Override
    public UrlServer  GetUrlServer() {
        List<UrlServer> urlServers=urlServerRepository.findAll();
        if(!urlServers.isEmpty()){
            UrlServer u = urlServers.get(0);
            return u;
        }
       return null;
    }


}
