package com.scott.steam.alexa.intenthandler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.scott.steam.alexa.dto.App;
import com.scott.steam.alexa.dto.UserStatsByGameResponseParent;

public class PlayedIntentHandler extends IntentHandler implements RequestHandler {
	
	private static final String endpoint = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key={steamApiKey}&steamid={steamId}&format=json&include_appinfo=true&include_played_free_games=true";
	
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("PlayedIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
	    logger.debug("Getting never played games");

	    UserStatsByGameResponseParent response = (UserStatsByGameResponseParent) doRestCall(endpoint, UserStatsByGameResponseParent.class);
		
		int playedCount = 0;
		for(App a : response.getResponse().getGames()) {
			if (a.getPlaytimeForever() > 0)
				playedCount ++;
		}
		
		String speechText = "You have played " + playedCount + " Steam games.";
		logger.debug("Saying \"" + speechText + "\"");
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("LastPlayed", speechText).build();
	}
}
