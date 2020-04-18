package com.scott.steam.alexa.intenthandler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.scott.steam.alexa.dto.App;
import com.scott.steam.alexa.dto.UserStatsByGameResponseParent;

public class PlaytimeByGameIntentHandler extends IntentHandler implements RequestHandler {

	private static final String endpoint = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key={steamApiKey}&steamid={steamId}&format=json&include_appinfo=true&include_played_free_games=true";
			
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("PlaytimeByGameIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
	    IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
	    Map<String, Slot> slots = intentRequest.getIntent().getSlots();
	    logger.debug("Getting playtime for " + slots.get("gameName").getValue());

		UserStatsByGameResponseParent response = (UserStatsByGameResponseParent) doRestCall(endpoint, UserStatsByGameResponseParent.class);
		
		App app = null;
		for(App a : response.getResponse().getGames()) {
			if (a.getName().toLowerCase().equals(slots.get("gameName").getValue()))
				app = a;
		}
		
		String speechText = "You have played " + slots.get("gameName").getValue() + " for " + (app.getPlaytimeForever() / 60) + " hours.";
		logger.debug("Saying \"" + speechText + "\"");
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("PlaytimeCard", speechText).build();
	}
	
}
