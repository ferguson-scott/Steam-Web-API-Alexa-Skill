package com.scott.steam.alexa.dto;

@lombok.Getter
@lombok.Setter
public class GamesResponseParent implements SteamApiResponse {
	private GamesResponse response;
}
