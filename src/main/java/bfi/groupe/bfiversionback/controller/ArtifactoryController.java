package bfi.groupe.bfiversionback.controller;
import bfi.groupe.bfiversionback.configuration.MessageResponse;
import bfi.groupe.bfiversionback.entity.UrlArtifact;
import bfi.groupe.bfiversionback.service.ArtifactoryService;
import com.fasterxml.jackson.core.PrettyPrinter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ArtifactoryController {

    private final ArtifactoryService artifactoryService;

    @Autowired
    public ArtifactoryController(ArtifactoryService artifactoryService) {
        this.artifactoryService = artifactoryService;
    }

    @GetMapping("/getAllArtifact")
    public ResponseEntity getAllArtifact() {
         ResponseEntity<String> response =artifactoryService.getAllArtifact();
        return response;
    }
    @PostMapping("/getArtifactUrl")
    public ResponseEntity<?> getArtifactUrl(@RequestBody UrlArtifact urlArtifact) {
        ResponseEntity<String> response = artifactoryService.getArtifactByUrl(urlArtifact);
        return response;
    }
    @GetMapping("/GetUserArtifactoryDetails")
    public ResponseEntity GetUserAndGroupeDetails(){
      return   ResponseEntity.ok(artifactoryService.getUserAndGroupDetails());
    }
}

