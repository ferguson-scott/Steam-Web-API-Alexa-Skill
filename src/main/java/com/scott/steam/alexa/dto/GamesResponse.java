package com.scott.steam.alexa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Getter
@lombok.Setter
public class GamesResponse {
	@JsonProperty("total_count")
	private String totalCount;
	private App[] games;
}
