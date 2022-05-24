package br.ce.scosta.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class HTMLTest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "https://restapi.wcaquino.me/v2";
		//RestAssured.port = ;
		//RestAssured.basePath = "";
	}

	@Test
	public void deveBuscarComHTML() {
		given()
			.log().all()
		.when()
			.get("/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body("html.body.div.table.tbody.tr.size()", is(3))
			.body("html.body.div.table.tbody.tr[1].td[2]", is(25))
		;
	}
	
	@Test
	public void deveBuscarComXpathEmHTML() {
		given()
			.log().all()
		.when()
			.get("/users?format=clean")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body(hasXPath("count(//table/tr)", is("4")))
			.body(hasXPath("//td[text() = '2']/../td[2]", is("Maria Joaquina")))
		;
	}
}
