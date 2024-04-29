package bfi.groupe.bfiversionback.service;

import com.fasterxml.jackson.core.type.TypeReference;
import bfi.groupe.bfiversionback.entity.GitLabGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GitlabService {
    private final RestTemplate restTemplate;
    @Value("${gitlab.api.baseurl}")
    private String gitLabApiBaseUrl;

    @Value("${gitlab.api.token}")
    private String gitLabApiToken;

    public ResponseEntity GetUserGitLab(int id) {
        String url = gitLabApiBaseUrl + "users/" + id;
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

    public ResponseEntity<?> getAllGroups() {
        try {
            int page = 1;
            int perPage = 100;
            List<GitLabGroup> allGroups = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            while (true) {
                String url = gitLabApiBaseUrl + "groups?page=" + page + "&per_page=" + perPage;
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(gitLabApiToken);
                HttpEntity<?> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        String.class
                );
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    String responseBody = responseEntity.getBody();
                    List<GitLabGroup> groups = objectMapper.readValue(responseBody, new TypeReference<List<GitLabGroup>>() {
                    });
                    allGroups.addAll(groups);

                    String nextPageLink = responseEntity.getHeaders().getFirst("Link");
                    if (nextPageLink == null || !nextPageLink.contains("rel=\"next\"")) {
                        break;
                    }
                    page++;
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve groups");
                }
            }

            return ResponseEntity.ok().body(allGroups);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity GetGroupsById(int id) {
        String url = gitLabApiBaseUrl + "groups/" + id;
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
        String url = gitLabApiBaseUrl + "projects/" + id + "?statistics=true";
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

    public ResponseEntity GetBranches(int id) {
        String url = gitLabApiBaseUrl + "projects/" + id + "/repository/branches";
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


    public ResponseEntity GetTags(int id) {
        String url = gitLabApiBaseUrl + "projects/" + id + "/repository/tags";
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

    public String GetFileGitlab(int id, String pathFichier, String branch) {

        String url = gitLabApiBaseUrl + "projects/" + id + "/repository/files/" + pathFichier.replace("/", "%2F") + "/raw?ref=" + branch;
        URI gitlabUri = UriComponentsBuilder.fromHttpUrl(url)
                .build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gitLabApiToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        System.out.println(gitlabUri);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    gitlabUri,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );

            String fileContent = response.getBody();
            if (fileContent == null) {
                return "vide";
            }

            return fileContent;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "404";
            }
        }
        return null;
    }
    public ResponseEntity<byte[]> getFileContent(int id, String pathFichier, String branch) {
        String url = gitLabApiBaseUrl + "projects/" + id + "/repository/files/" + pathFichier.replace("/", "%2F").replace(" ","%20") + "/raw?ref=" + branch;
        URI gitlabUri = UriComponentsBuilder.fromHttpUrl(url)
                .build(true).toUri();HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gitLabApiToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                gitlabUri,
                HttpMethod.GET,
                requestEntity,
                byte[].class
        );
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(responseEntity.getBody(), responseHeaders, responseEntity.getStatusCode());
    }
    public ResponseEntity GetGitlabRepoTree(int id) {
        String url = gitLabApiBaseUrl + "projects/" + id + "/repository/tree?per_page=100";
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
    public ResponseEntity GetGitlabFileTree(int id,String path) {
        int page=1;
        int perPage= 100;
        String url = gitLabApiBaseUrl + "projects/" + id + "/repository/tree?path="+path+"&per_page=100";
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
    public ResponseEntity GetCommits(int id) {
        String url = gitLabApiBaseUrl +"projects/"+id+"/repository/commits?per_page=100";
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
