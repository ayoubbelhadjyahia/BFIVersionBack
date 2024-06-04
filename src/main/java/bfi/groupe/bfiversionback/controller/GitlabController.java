package bfi.groupe.bfiversionback.controller;

import bfi.groupe.bfiversionback.entity.GitLabCode;
import bfi.groupe.bfiversionback.entity.GitLabGetFileTree;
import bfi.groupe.bfiversionback.service.GitlabService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

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
        return gitlabService.GetBranches(id);
        }

    @GetMapping("/GetTags/{id}")
    public ResponseEntity GetTags(@PathVariable("id") int id){
        return gitlabService.GetTags(id);
    }
    @GetMapping("/GetGitlabRepoTree/{id}")
    public ResponseEntity GetGitlabRepoTree(@PathVariable("id") int id){
        return gitlabService.GetGitlabRepoTree(id);
    }
    @PostMapping("/GetGitlabFileTree")
    public ResponseEntity GetGitlabFileTree(@RequestBody GitLabGetFileTree request) {
        return  gitlabService.GetGitlabFileTree(request.getId(),request.getPath());

    }

    @PostMapping("/GetCodeGitlab")
    public ResponseEntity GetCodeGitlab(@RequestBody GitLabCode request) throws URISyntaxException {
        String content=gitlabService.GetFileGitlab(request.getId(), request.getPath(), request.getBranch());
        if(content.equals("404")){
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
        return gitlabService.getCommits(id);

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
    @GetMapping("/GetGitlabVersion")
    public ResponseEntity GetGitlabVersion(){
        ResponseEntity<String> response=gitlabService.GetGitlabVersion();
        return response;
    }
}
