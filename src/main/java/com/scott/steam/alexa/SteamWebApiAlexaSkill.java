package com.scott.steam.alexa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.scott.steam.alexa.intenthandler.LastGameIntentHandler;
import com.scott.steam.alexa.intenthandler.NeverPlayedIntentHandler;
import com.scott.steam.alexa.intenthandler.PlayedIntentHandler;
import com.scott.steam.alexa.intenthandler.PlaytimeByGameIntentHandler;

@SpringBootApplication
public class SteamWebApiAlexaSkill extends SkillStreamHandler {

	//@Value("${skill.id}")
	private static String skillId = "amzn1.ask.skill.35fd8a9f-096b-4356-aa82-24c0aa244c25";
	
	private static Skill getSkill() {
		return Skills.standard().addRequestHandlers(
				new LastGameIntentHandler(),
				new PlaytimeByGameIntentHandler(),
				new NeverPlayedIntentHandler(),
				new PlayedIntentHandler()
				).withSkillId(skillId).build();
	}
	
	public SteamWebApiAlexaSkill() {
		super(getSkill());
	}
	
	@Bean
	public Logger logger() {
		return LoggerFactory.getLogger("com.scott.steam.alexa.logger");
	}
}
