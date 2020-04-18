package com.scott.steam.alexa.intenthandler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.scott.steam.alexa.dto.GamesResponseParent;

@Component
public class LastGameIntentHandler extends IntentHandler implements RequestHandler {
	
	private static final String recentlyPlayedEndpoint = "http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key=${steam.api.key}&steamid=${steam.id}&format=json";
	
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("LastPlayedGameIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
	    logger.debug("Getting last played game");
	
	    GamesResponseParent response = (GamesResponseParent) doRestCall(recentlyPlayedEndpoint, GamesResponseParent.class);

	    logger.debug("Steam API Response: " + response);
	    
		String speechText = "The last game you played was " + response.getResponse().getGames()[0].getName();
		logger.debug("Saying \"" + speechText + "\"");
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("LastPlayed", speechText).build();
	}
	
}
