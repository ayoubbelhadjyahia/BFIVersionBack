package bfi.groupe.bfiversionback.controller;

import bfi.groupe.bfiversionback.entity.GitLabCode;
import bfi.groupe.bfiversionback.entity.GitLabGetFileTree;
import bfi.groupe.bfiversionback.service.GitlabService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

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
    @GetMapping("/GetUsers")
    public ResponseEntity GetUsers(){
        ResponseEntity<String> response=gitlabService.getUsers();
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
    @GetMapping("/GetGitlabRepoTree/{id}")
    public ResponseEntity GetGitlabRepoTree(@PathVariable("id") int id){
        ResponseEntity<String> response=gitlabService.GetGitlabRepoTree(id);
        return response;
    }
    @PostMapping("/GetGitlabFileTree")
    public ResponseEntity GetGitlabFileTree(@RequestBody GitLabGetFileTree request) {
         ResponseEntity<String> response=gitlabService.GetGitlabFileTree(request.getId(),request.getPath());
   return response;
    }

    @PostMapping("/GetCodeGitlab")
    public ResponseEntity GetCodeGitlab(@RequestBody GitLabCode request) throws URISyntaxException {
        String content=gitlabService.GetFileGitlab(request.getId(), request.getPath(), request.getBranch());
        if(content=="404"){
            ResponseEntity.status(HttpStatus.OK).body("404");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonResponse = objectMapper.createObjectNode();
        jsonResponse.put("message", content);
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }
    @PostMapping("/GetFileGitlabDetails")
    public ResponseEntity GetFileGitlabDetails(@RequestBody GitLabCode request) throws URISyntaxException {
        String content=gitlabService.GetFileGitlabDetails(request.getId(), request.getPath(), request.getBranch());

        return ResponseEntity.status(HttpStatus.OK).body(content);
    }
    @PostMapping("/image")
    public ResponseEntity<byte[]> getFileContent(@RequestBody GitLabCode request) throws UnsupportedEncodingException, URISyntaxException {
        return gitlabService.getFileContent(request.getId(), request.getPath(), request.getBranch());
    }

    @GetMapping("/GetCommits/{id}")
    public ResponseEntity GetCommits(@PathVariable("id") int id){
        ResponseEntity<ObjectNode> response=gitlabService.getCommits(id);
        return response;
    }
    @GetMapping("/GetEventsbyId/{id}")
    public ResponseEntity GetEventsbyId(@PathVariable("id") int id){
        ResponseEntity<ObjectNode> response=gitlabService.GetEventsbyId(id);
        return response;
    }
    @GetMapping("/GetAllProjectsLastWeek")
    public ResponseEntity GetAllProjectsLastWeek(){
        ResponseEntity<ObjectNode> response=gitlabService.GetAllProjectsLastWeek();
        return response;
    }
}
