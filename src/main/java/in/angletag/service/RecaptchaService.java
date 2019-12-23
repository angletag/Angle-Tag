package in.angletag.service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


public class RecaptchaService {

	private static final Logger log = LoggerFactory.getLogger(RecaptchaService.class);

	@Value("${google.recaptcha.secret}")
	String recaptchaSecret;

	private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

	@Autowired
	@Qualifier("proxiedRestTemplate")
	RestTemplate rt;

	public String verifyRecaptcha(String ip, String recaptchaResponse) {

		Map<String, String> body = new HashMap<>();
		body.put("secret", recaptchaSecret);
		body.put("response", recaptchaResponse);
		body.put("remoteip", ip);
		log.debug("Request body for recaptcha: {}", body);
	
		ResponseEntity<Map> recaptchaResponseEntity = rt.postForEntity(
				GOOGLE_RECAPTCHA_VERIFY_URL + "?secret={secret}&response={response}&remoteip={remoteip}", body,
				Map.class, body);

		log.debug("Response from recaptcha: {}", recaptchaResponseEntity);
		Map<String, Object> responseBody = recaptchaResponseEntity.getBody();

		boolean recaptchaSucess = (Boolean) responseBody.get("success");
		if (!recaptchaSucess) {
			List<String> errorCodes = (List) responseBody.get("error-codes");

			String errorMessage = errorCodes.stream().map(s -> RecaptchaUtil.RECAPTCHA_ERROR_CODE.get(s))
					.collect(Collectors.joining(", "));

			return errorMessage;
		} else {
			return "";
		}
	}
	 
}
