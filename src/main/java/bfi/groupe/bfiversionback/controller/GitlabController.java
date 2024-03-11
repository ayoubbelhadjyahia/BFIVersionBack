package bfi.groupe.bfiversionback.controller;

import bfi.groupe.bfiversionback.service.GitlabService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/gitlab")
@CrossOrigin(origins = "*")
public class GitlabController {
    private final GitlabService gitlabService;
    @GetMapping("/getUserGitlab/{id}")
    public ResponseEntity GetUserGitlab(@PathVariable("id") int id){
ResponseEntity<String> response=gitlabService.GetUserGitLab(id);
        return response;
    }   @GetMapping("/GetAllGroupe/")
    public ResponseEntity GetAllGroupe(){
ResponseEntity<String> response=gitlabService.GetAllGroupe();
        return response;
    }
    @GetMapping("/GetGroupsById/{id}")
    public ResponseEntity GetGroupsById(@PathVariable("id") int id){
        ResponseEntity<String> response=gitlabService.GetGroupsById(id);
        return response;
    }
    @GetMapping("/GetProjectById/{id}")
    public ResponseEntity GetProjectById(@PathVariable("id") int id){
        ResponseEntity<String> response=gitlabService.GetProjectById(id);
        return response;
    }
}
