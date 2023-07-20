package com.management.services;


import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.json.JsonMapper;

@Service
public class RestExchanger {

	public ResponseEntity<String> postData(String url, Map<String, String> obj){
		
		String json = null;
		JsonMapper mapper = new JsonMapper();
		try {
			json = mapper.writeValueAsString(obj);
		}catch(Exception e) {
			System.out.println(e);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(json,headers);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange(url, HttpMethod.POST,request, String.class);
	}
	
	public <T> T getDataForClass(String url, Class<T> valueClass) {
		T t = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request,String.class);
		JsonMapper mapper = new JsonMapper();
		try {
			t = mapper.readValue(response.getBody(), valueClass);
		}catch(Exception e) {
			System.out.println(e);
		}
		return t;
	}

	public ResponseEntity<String> getData(String url) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request,String.class);
		return response;
	}
}
