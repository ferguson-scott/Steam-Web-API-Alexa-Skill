package com.main;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.example.handlers.LastGameIntentHandler;
import com.example.handlers.NeverPlayedIntentHandler;
import com.example.handlers.PlayedIntentHandler;
import com.example.handlers.PlaytimeByGameIntentHandler;

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
}
