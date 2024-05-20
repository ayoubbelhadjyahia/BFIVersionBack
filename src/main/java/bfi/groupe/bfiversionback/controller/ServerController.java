package bfi.groupe.bfiversionback.controller;

import bfi.groupe.bfiversionback.entity.UrlServer;
import bfi.groupe.bfiversionback.service.ServiceUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/Server")
@CrossOrigin(origins = "*")
public class ServerController {
    private final ServiceUser serviceUser;

    @GetMapping("/getUrlServer")
    public UrlServer GetUserGitlab(){
        return serviceUser.GetUrlServer();
    }
    @GetMapping("/SetUrlArtifactory/{url}")
    public UrlServer SetUrlArtifactory(@PathVariable("url") String url){
        return serviceUser.SetUrlServerArtifactory(url);
    }
    @GetMapping("/SetUrlGit/{url}")
    public UrlServer SetUrlGit(@PathVariable("url") String url){
        return serviceUser.SetUrlServerGit(url);
    }
    @PostMapping("/AddUrlServer")
    public ResponseEntity AddUrlServer(@RequestBody UrlServer request) {
        return ResponseEntity.ok(serviceUser.AddUrlServer(request));
    }
}
