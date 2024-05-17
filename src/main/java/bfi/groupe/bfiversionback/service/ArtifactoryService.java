package bfi.groupe.bfiversionback.service;
import bfi.groupe.bfiversionback.entity.UrlArtifact;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ArtifactoryService {

    private final String baseUrl;
    private final String username;
    private final String password;

    private final RestTemplate restTemplate;

    public ArtifactoryService(RestTemplate restTemplate,@Value("${UsernameArtifactory}")String  username,@Value("${passwordArtifactory}")String  password,@Value("apiKey")String  apiKey, @Value("${artifactory.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.password=password;
        this.username=username;
    }

    public ResponseEntity getAllArtifact() {
        String url = baseUrl + "/artifactory/api/storage/bfi-virtual";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        return response;
    }

    public ResponseEntity<String> getArtifactByUrl(UrlArtifact urlArtifact) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = urlArtifact.getBaseUri() + urlArtifact.getFolderUri();
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        return response;
    }

    public String getUserAndGroupDetails() {
        String url = baseUrl + "/artifactory/api/system/security";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username,password);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return parseXmlToJson(responseEntity.getBody());
        } else {
            return "Failed to fetch data. Status code: " + responseEntity.getStatusCodeValue();
        }
    }
    public String parseXmlToJson(String xmlData) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            Object json = xmlMapper.readValue(xmlData, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(json);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to parse XML to JSON: " + e.getMessage();
        }
    }
    public String getStorage() {
        String url = baseUrl + "/artifactory/api/storageinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username,password);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return "Failed to fetch data. Status code: " + responseEntity.getStatusCodeValue();
        }
    }
    public String getVersion() {
        String url = baseUrl + "/artifactory/api/system/version";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username,password);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return "Failed to fetch data. Status code: " + responseEntity.getStatusCodeValue();
        }
    }
}
