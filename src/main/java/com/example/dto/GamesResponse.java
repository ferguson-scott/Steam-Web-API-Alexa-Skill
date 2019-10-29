package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Getter
@lombok.Setter
public class GamesResponse {
	@JsonProperty("total_count")
	private String totalCount;
	private App[] games;
}
