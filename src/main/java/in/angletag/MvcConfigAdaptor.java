package in.angletag;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigAdaptor implements WebMvcConfigurer{
	
	private static final Logger log = LoggerFactory.getLogger(MvcConfigAdaptor.class);


	@Autowired
	private Environment environment;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		if (environment.acceptsProfiles(Profiles.of("default"))) {
			log.info("CrossOrigin is enabled globally for this profile");
			registry.addMapping("/api/**").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
					.allowCredentials(true).allowedOrigins("http://localhost:3000").maxAge(3600);

		} else {
			log.info("CrossOrigin disabled globally for this profile : " + environment);
		}
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
	
	@Bean("proxiedRestTemplate")
	public RestTemplate createRestTemplate() throws Exception {
        final String username = "";
        final String password = "";
        final String proxyUrl = "";
        final int port = 6050;

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials( 
                new AuthScope(proxyUrl, port), 
                new UsernamePasswordCredentials(username, password));

        HttpHost myProxy = new HttpHost(proxyUrl, port);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        clientBuilder.setProxy(myProxy).setDefaultCredentialsProvider(credsProvider).disableCookieManagement();

        HttpClient httpClient = clientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }
}
