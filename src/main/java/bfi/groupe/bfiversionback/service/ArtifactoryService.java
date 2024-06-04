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
    private final ServiceUser serviceUser;
    private final String username;
    private final String password;

    private final RestTemplate restTemplate;

    public ArtifactoryService(ServiceUser serviceUser,RestTemplate restTemplate,@Value("${UsernameArtifactory}")String  username,@Value("${passwordArtifactory}")String  password) {
        this.restTemplate = restTemplate;
        this.password=password;
        this.username=username;
        this.serviceUser=serviceUser;
    }



    public ResponseEntity getAllArtifact() {
        try{
        String baseUrl =serviceUser.GetUrlServer().getUrlArtifactory();
        String url ="http://"+baseUrl+"/artifactory/api/storage/bfi-virtual";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);
    }catch (Exception e){
            return null;
        }
    }

    public ResponseEntity<String> getArtifactByUrl(UrlArtifact urlArtifact) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = urlArtifact.getBaseUri() + urlArtifact.getFolderUri();
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);


    }

    public String getUserAndGroupDetails() {
        try {
            String baseUrl = serviceUser.GetUrlServer().getUrlArtifactory();
            String url = "http://" + baseUrl + "/artifactory/api/system/security";
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return parseXmlToJson(responseEntity.getBody());
            }
        }catch (Exception e){
            return null;
        }
        return null;
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
        try {
            String baseUrl = serviceUser.GetUrlServer().getUrlArtifactory();
            String url = "http://" + baseUrl + "/artifactory/api/storageinfo";
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
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
                return "Failed to fetch data. Status code: ";
            }
        } catch (Exception e){
return null;
        }
    }
    public String getVersion() {
        try {
            String baseUrl = serviceUser.GetUrlServer().getUrlArtifactory();
            String url = "http://" + baseUrl + "/artifactory/api/system/version";
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
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
                return "Failed to fetch data. Status code: ";
            }
        }catch (Exception e){
            return null;
        }
    }
}
