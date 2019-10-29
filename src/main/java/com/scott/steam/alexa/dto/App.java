package com.scott.steam.alexa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Getter
@lombok.Setter
public class App {
	private String appid;
	private String name;
	@JsonProperty("playtime_2weeks")
	private String playtime2weeks;
	@JsonProperty("playtime_forever")
	private int playtimeForever;
	@JsonProperty("img_icon_url")
	private String imgIconUrl;
	@JsonProperty("img_logo_url")
	private String imgLogoUrl;
	@JsonProperty("playtime_windows_forever")
	private String playtimeWindowsForever;
	@JsonProperty("playtime_mac_forever")
	private String playtimeMacForever;
	@JsonProperty("playtime_linux_forever")
	private String playtimeLinuxForever;
}
