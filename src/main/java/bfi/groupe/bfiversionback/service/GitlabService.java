package bfi.groupe.bfiversionback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GitlabService {
    private final RestTemplate restTemplate;
    @Value("${gitlab.api.baseurl}")
    private String gitLabApiBaseUrl;

    @Value("${gitlab.api.token}")
    private String gitLabApiToken;
    public ResponseEntity GetUserGitLab(int id) {
        String url = gitLabApiBaseUrl +"users/"+id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gitLabApiToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
    }
    public ResponseEntity GetAllGroupe() {
        String url = gitLabApiBaseUrl +"groups/";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gitLabApiToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
    }
    public ResponseEntity GetGroupsById(int id) {
        String url = gitLabApiBaseUrl +"groups/"+id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gitLabApiToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
    }
    public ResponseEntity GetProjectById(int id) {
        String url = gitLabApiBaseUrl +"projects/"+id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gitLabApiToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
    }
}
