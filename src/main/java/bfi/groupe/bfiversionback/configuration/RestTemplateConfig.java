package bfi.groupe.bfiversionback.configuration;
import javax.net.ssl.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.cert.X509Certificate;
@Configuration
public class RestTemplateConfig {
@Bean
    public RestTemplate getRestTemplate() throws Exception {
        TrustManager[] trustAllCertificates = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();


        return new RestTemplate(requestFactory);
    }
}
