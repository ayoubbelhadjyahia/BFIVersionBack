package bfi.groupe.bfiversionback.service;
import bfi.groupe.bfiversionback.entity.UrlArtifact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ArtifactoryService {

    private final String baseUrl;
    private final String accessToken;

    private final RestTemplate restTemplate;

    public ArtifactoryService(RestTemplate restTemplate, @Value("${artifactory.base-url}") String baseUrl, @Value("${accessToken}") String accessToken) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.accessToken=accessToken;
    }

    public String getAllArtifact() {
        String url = baseUrl + "/artifactory/api/storage/bfi-virtual";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }
    public ResponseEntity<String> getArtifactByUrl(UrlArtifact urlArtifact) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
String url=urlArtifact.getBaseUri()+urlArtifact.getFolderUri();
        System.out.println(url);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        return response;
    }
}
