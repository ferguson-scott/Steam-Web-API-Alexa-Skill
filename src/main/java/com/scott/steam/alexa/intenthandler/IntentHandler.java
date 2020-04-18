package com.scott.steam.alexa.intenthandler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.scott.steam.alexa.dto.SteamApiResponse;

public abstract class IntentHandler {

	@Autowired
	Logger logger;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	HttpHeaders headers;
	
	@Value("${steam.api.key}")
	private String steamApiKey;
	@Value("${steam.id}")
	private String steamId;
	
	
	public <T extends SteamApiResponse> SteamApiResponse doRestCall(String endpoint, Class<? extends SteamApiResponse> responseType) {
		return restTemplate.getForObject(endpoint, responseType, steamApiKey, steamId);
	}
}
