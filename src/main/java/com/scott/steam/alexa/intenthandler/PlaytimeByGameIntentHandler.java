package com.scott.steam.alexa.intenthandler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.scott.steam.alexa.dto.App;
import com.scott.steam.alexa.dto.UserStatsByGameResponseParent;

public class PlaytimeByGameIntentHandler implements RequestHandler {

	@Value("${steam.api.key}")
	private String steamApiKey;
	@Value("${steam.id}")
	private String steamId;
	
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("PlaytimeByGameIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		
		System.out.println("Revieved request");
	    IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
	    Map<String, Slot> slots = intentRequest.getIntent().getSlots();
	    System.out.println("Getting playtime for " + slots.get("gameName").getValue());
	
	    RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<UserStatsByGameResponseParent> response = restTemplate.exchange("http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + steamApiKey + "&steamid=" + steamId + "&format=json&include_appinfo=true&include_played_free_games=true", HttpMethod.GET, new HttpEntity<Object>(headers), UserStatsByGameResponseParent.class);
		
		App app = null;
		for(App a : response.getBody().getResponse().getGames()) {
			if (a.getName().toLowerCase().equals(slots.get("gameName").getValue()))
				app = a;
		}
		
		String speechText = "You have played " + slots.get("gameName").getValue() + " for " + (app.getPlaytimeForever() / 60) + " hours.";
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("PlaytimeCard", speechText).build();
	}
	
}
