package com.scott.steam.alexa.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.scott.steam.alexa.SteamWebApiAlexaSkill;
import com.scott.steam.alexa.dto.GamesResponseParent;
import com.scott.steam.alexa.intenthandler.LastGameIntentHandler;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SteamWebApiAlexaSkill.class)
@ActiveProfiles("test")
public class SteamAlexaSkillTest {
	
	@Autowired
	private LastGameIntentHandler intentHandler;
	@Value("${steam.api.key}")
	private String apiKey;
	
	@Test
	public void test_last_game_played() throws JsonParseException, JsonMappingException, IOException {
		GamesResponseParent response = (GamesResponseParent) intentHandler.doRestCall("http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key={steamApiKey}&steamid={steamId}&format=json", GamesResponseParent.class);
		System.out.println(response.getResponse().getGames()[0].getName());
	}
}
