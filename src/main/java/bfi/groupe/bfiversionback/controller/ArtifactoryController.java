package bfi.groupe.bfiversionback.controller;
import bfi.groupe.bfiversionback.entity.UrlArtifact;
import bfi.groupe.bfiversionback.service.ArtifactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ArtifactoryController {

    private final ArtifactoryService artifactoryService;

    @Autowired
    public ArtifactoryController(ArtifactoryService artifactoryService) {
        this.artifactoryService = artifactoryService;
    }

    @GetMapping("/getAllArtifact")
    public String getAllArtifact() {
        return artifactoryService.getAllArtifact();
    }
    @PostMapping("/getArtifactUrl")
    public ResponseEntity<?> getArtifactUrl(@RequestBody UrlArtifact urlArtifact) {
        ResponseEntity<String> response = artifactoryService.getArtifactByUrl(urlArtifact);
        return response;
    }
}

