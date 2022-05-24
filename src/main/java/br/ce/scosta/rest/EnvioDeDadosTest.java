 package br.ce.scosta.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class EnvioDeDadosTest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "https://restapi.wcaquino.me/v2";
		//RestAssured.port = ;
		//RestAssured.basePath = "";
	}
	
	@Test
	public void deveEnviarValorViaQuery() {
		given()
			.log().all()
		.when()
			.get("/users?format=json")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON)
		;
	}
	
	@Test
	public void deveEnviarValorViaParametro() {
		given()
			.log().all()
			.queryParam("format", "xml")
		.when()
			.get("/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			.contentType(containsString("utf-8"))
		;
	}
	
	@Test
	public void deveEnviarValorViaHeader() {
		given()
			.log().all()
			.accept(ContentType.JSON)
		.when()
			.get("/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON)
		;
	}
}
