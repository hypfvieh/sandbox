package com.github.hypfvieh.sandbox.steelengine;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SteelClock {
	private static final String DISPLAY_NAME = "Bind Test";
	private static final  String APP_NAME = "BIND_TEST";
	private static final String DEVELOPER = "me";
	
	public static void main(String[] args) throws URISyntaxException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		
		File file = new File(System.getenv("programdata"), "SteelSeries\\SteelSeries Engine 3\\coreProps.json");
		if (!file.exists()) {
			System.err.println("SteelEngine not running");
			return;
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> readValue = objectMapper.readValue(file, new TypeReference<Map<String,String>>(){});
		
		String steelSeriesHost = readValue.get("address");
		
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest registerRequest = HttpRequest.newBuilder()
					.uri(new URI("http://" + steelSeriesHost + "/game_metadata"))
					.header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString("{ \"game\": \"" + APP_NAME + "\", \"game_display_name\": \""
						+ DISPLAY_NAME + "\", \"developer\": \"" + DEVELOPER + "\" }'"))
				.build();
								
		HttpResponse<String> registerResponse = client.send(registerRequest, BodyHandlers.ofString());
		if (registerResponse.statusCode() != 200) {
			System.err.println("Register failed: " + registerResponse.body());
			return;
		} else {
			System.out.println("Register successful");
		}
		
		HttpRequest bindRequest = HttpRequest.newBuilder()
				.uri(new URI("http://" + steelSeriesHost + "/bind_game_event"))
				.header("Content-Type", "application/json")
			.POST(BodyPublishers.ofString("{\"game\": \""+APP_NAME+"\",\"event\": \"MY_EVENT\",\"min_value\": 0,\"max_value\": 100,\"icon_id\": 1,\"handlers\": [{\"device-type\": \"keyboard\",\"zone\": \"function-keys\",\"color\": {\"gradient\": {\"zero\": {\"red\": 255, \"green\": 0, \"blue\": 0},\"hundred\": {\"red\": 0, \"green\": 255, \"blue\": 0}}},\"mode\": \"percent\"}]}"))
			.build();
		
		HttpResponse<String> bindResponse = client.send(bindRequest, BodyHandlers.ofString());
		if (bindResponse.statusCode() != 200) {
			System.err.println("Bind failed: " + bindResponse.body());
			return;
		} else {
			System.out.println("Bind successful");
		}

		HttpRequest unregisterRequest = HttpRequest.newBuilder()
				.uri(new URI("http://" + steelSeriesHost + "/remove_game"))
				.header("Content-Type", "application/json")
			.POST(BodyPublishers.ofString("{game: \""+APP_NAME+"\"}"))
			.build();
		
		HttpResponse<String> unregisterResponse = client.send(unregisterRequest, BodyHandlers.ofString());
		if (unregisterResponse.statusCode() != 200) {
			System.err.println("Unregister failed: " + unregisterResponse.body());
			return;
		} else {
			System.out.println("Unregister successful");
		}
	}
}

