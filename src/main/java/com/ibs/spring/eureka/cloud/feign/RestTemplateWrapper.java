package com.ibs.spring.eureka.cloud.feign;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author DougLei
 */
public class RestTemplateWrapper {
	private RestTemplate restTemplate;

	public RestTemplateWrapper(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * 
	 * @param server
	 * @param requestBody
	 * @param responseType
	 * @param uriVariables
	 * @return
	 */
	public <T> ResponseEntity<T> exchange(APIServer api, Object requestBody, Class<T> responseType, Object... uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, api.getHeaders());
		return restTemplate.exchange(api.getUrl(), api.getRequestMethod(), requestEntity, responseType, uriVariables);
	}
}
