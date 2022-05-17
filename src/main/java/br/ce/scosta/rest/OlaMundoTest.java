package br.ce.scosta.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	@Test
	public void testOlaMundo() {
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == 200);
		Assert.assertEquals(201, response.statusCode());
		ValidatableResponse validacao = response.then();
		
		validacao.statusCode(200);
	}

	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		get("https://restapi.wcaquino.me/ola").then().statusCode(200);
		
		given()//PréConditions
		.when()//Actions
			.get("https://restapi.wcaquino.me/ola")
		.then()//Assertives
			.statusCode(200);
	}
	
	@Test
	public void devoCOnhecerMatchersHamcrest() {
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(123, Matchers.isA(Integer.class));
		Assert.assertThat(123, Matchers.greaterThan(120));
		Assert.assertThat(119, Matchers.lessThan(120));
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1,3,5,7,9));
		assertThat(impares, containsInAnyOrder(7,9,1,5,3));
		
		assertThat("Maria", is(not("Joao")));//Negative
		assertThat("Joaquina", anyOf(is("Maria"), is("Joaquina")));//OR
		assertThat("Maria", allOf(startsWith("Ma"), endsWith("ria"), containsString("ar")));//AND
		
		
	}

	@Test
	public void devoValidarOBody() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"));
	}
}
