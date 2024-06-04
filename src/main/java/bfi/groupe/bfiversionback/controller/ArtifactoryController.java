package bfi.groupe.bfiversionback.controller;
import bfi.groupe.bfiversionback.entity.UrlArtifact;
import bfi.groupe.bfiversionback.service.ArtifactoryService;
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
        return artifactoryService.getAllArtifact();
    }
    @PostMapping("/getArtifactUrl")
    public ResponseEntity getArtifactUrl(@RequestBody UrlArtifact urlArtifact) {
        return  artifactoryService.getArtifactByUrl(urlArtifact);

    }
    @GetMapping("/GetUserArtifactoryDetails")
    public ResponseEntity getUserAndGroupeDetails(){
      return   ResponseEntity.ok(artifactoryService.getUserAndGroupDetails());
    } @GetMapping("/GetStorageinfo")
    public ResponseEntity getStorageinfo(){
      return   ResponseEntity.ok(artifactoryService.getStorage());
    }
    @GetMapping("/GetVersion")
    public ResponseEntity getVersion(){
      return   ResponseEntity.ok(artifactoryService.getVersion());
    }
}

