package br.ce.scosta.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundo {
	public static void main(String[] args) {
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		System.out.println(response.getBody().asString());
		System.out.println(response.statusCode());
		
		ValidatableResponse validacao = response.then();
		
		validacao.statusCode(201);
	}
}
