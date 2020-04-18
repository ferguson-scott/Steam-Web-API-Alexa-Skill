package com.scott.steam.alexa.dto;

@lombok.Getter
@lombok.Setter
public class ApiResponse<T> {

	private T response;
}
