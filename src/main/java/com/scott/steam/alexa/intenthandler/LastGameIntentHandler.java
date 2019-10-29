package com.scott.steam.alexa.intenthandler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.scott.steam.alexa.dto.GamesResponseParent;

public class LastGameIntentHandler implements RequestHandler {

	@Value("${steam.api.key}")
	private String steamApiKey;
	@Value("${steam.id}")
	private String steamId;
	
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("LastPlayedGameIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
	    System.out.println("Getting last played game");
	    
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    
		ResponseEntity<GamesResponseParent> response = restTemplate.exchange("http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key=" + steamApiKey + "&steamid=" + steamId + "&format=json", HttpMethod.GET, new HttpEntity<Object>(headers), GamesResponseParent.class);
		System.out.println(response.getBody());
		System.out.println(response.getBody().getResponse().getGames()[0]);
		
		String speechText = "The last game you played was " + response.getBody().getResponse().getGames()[0].getName();
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("LastPlayed", speechText).build();
	}
	
}
