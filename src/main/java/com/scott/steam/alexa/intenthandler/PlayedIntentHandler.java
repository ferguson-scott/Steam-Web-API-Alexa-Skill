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
import com.scott.steam.alexa.dto.App;
import com.scott.steam.alexa.dto.UserStatsByGameResponseParent;

public class PlayedIntentHandler implements RequestHandler {

	@Value("${steam.api.key}")
	private String steamApiKey;
	@Value("${steam.id}")
	private String steamId;
	
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("PlayedIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
	    System.out.println("Getting never played games");
	    
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    
	    ResponseEntity<UserStatsByGameResponseParent> response = restTemplate.exchange("http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + steamApiKey + "&steamid=" + steamId + "&format=json&include_appinfo=true&include_played_free_games=true", HttpMethod.GET, new HttpEntity<Object>(headers), UserStatsByGameResponseParent.class);
		
		int playedCount = 0;
		for(App a : response.getBody().getResponse().getGames()) {
			if (a.getPlaytimeForever() > 0)
				playedCount ++;
		}
		
		String speechText = "You have played " + playedCount + " Steam games.";
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("LastPlayed", speechText).build();
	}
}
