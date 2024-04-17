package bfi.groupe.bfiversionback.controller;

import bfi.groupe.bfiversionback.service.GitlabService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    }
    @GetMapping("/GetAllGroupe/")
    public ResponseEntity GetAllGroupe(){
        return ResponseEntity.ok(gitlabService.getAllGroups().getBody());
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
    @GetMapping("/GetBranches/{id}")
    public ResponseEntity GetBranches(@PathVariable("id") int id){
        ResponseEntity<String> response=gitlabService.GetBranches(id);
        return response;
    }

    @GetMapping("/GetTags/{id}")
    public ResponseEntity GetTags(@PathVariable("id") int id){
        ResponseEntity<String> response=gitlabService.GetTags(id);
        return response;
    }
    @GetMapping("/GetProjectFileTreeGitlab/{id}")
    public ResponseEntity GetProjectFileTreeGitlab(@PathVariable("id") int id){
        ResponseEntity<String> response=gitlabService.GetGitlabFileTree(id);
        return response;
    }
    @GetMapping("/GetFileGitlab/{id}/{path}/{branch}")
    public ResponseEntity<String> getFile(@PathVariable("id") int id, @PathVariable("path") String path, @PathVariable("branch") String branch) {
        String content = gitlabService.GetFileGitlab(id, path, branch);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonResponse = objectMapper.createObjectNode();
        jsonResponse.put("message", content);
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }
    @GetMapping("/GetCommits/{id}")
    public ResponseEntity GetCommits(@PathVariable("id") int id){
        ResponseEntity<String> response=gitlabService.GetCommits(id);
        return response;
    }
}
