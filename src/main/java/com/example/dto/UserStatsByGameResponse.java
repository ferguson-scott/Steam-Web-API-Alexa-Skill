package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Getter
@lombok.Setter
public class UserStatsByGameResponse {

	@JsonProperty("game_count")
	private String gameCount;
	private App[] games;
}
