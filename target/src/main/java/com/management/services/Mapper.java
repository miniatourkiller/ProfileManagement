package com.management.services;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.json.JsonMapper;

@Service
public class Mapper {
public  <T> T mapData(String data,Class<T> classValue) {
	T t = null;
	JsonMapper mapper = new JsonMapper();
	try {
		t = mapper.readValue(data, classValue);
		return t;
	}catch(Exception e) {
		return null;
	}
}
}
