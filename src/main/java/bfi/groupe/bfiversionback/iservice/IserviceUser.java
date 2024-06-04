package bfi.groupe.bfiversionback.iservice;

import bfi.groupe.bfiversionback.entity.UrlServer;
import bfi.groupe.bfiversionback.entity.Utilisateur;

import java.util.List;

public interface IserviceUser {
     List<Utilisateur> GetALLUser();
     void DeleteUser(Integer IdUser);
     String SendCode(String Email);
     String ResetPassword(String Code, String NewPassword);
     void EditUser(Utilisateur U);
     Utilisateur GetUserById(Integer id);
     void ChangeLang(Integer id,String Lang);
     UrlServer AddUrlServer(UrlServer urlServer);
     UrlServer SetUrlServerArtifactory(String port);

     UrlServer GetUrlServer();
     UrlServer SetUrlServerGit(String port);

}
