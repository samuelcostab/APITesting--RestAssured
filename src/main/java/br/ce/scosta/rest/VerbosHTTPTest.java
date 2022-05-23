package br.ce.scosta.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class VerbosHTTPTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "https://restapi.wcaquino.me";
		//RestAssured.port = ;
		//RestAssured.basePath = "";
	}

	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"nameTest\", \"age\": 15}")
		.when()
			.post("/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("nameTest"))
			.body("age", is(15))
		;
	}
	
	@Test
	public void deveSalvarUsuarioUtilizandoMAP() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "UserMap");
		params.put("age", 25);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(params)
		.when()
			.post("/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("UserMap"))
			.body("age", is(25))
		;
	}
	
	@Test
	public void deveSalvarUsuarioUtilizandoObject() {
		User user = new User("UserMap", 25);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("UserMap"))
			.body("age", is(25))
		;
	}
	
	@Test
	public void deveDeserializarUsuarioSalvo() {
		User user = new User("UserDeserializado", 25);
		
		User usuarioDeserealizado = given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("/users")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
		;

		Assert.assertThat(usuarioDeserealizado.getId(), notNullValue());
		Assert.assertEquals(usuarioDeserealizado.getName(), "UserDeserializado");
		Assert.assertThat(usuarioDeserealizado.getAge(), is(25));
	}
	
	@Test
	public void naoDeveSalvarUserSemNome() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"age\": 15}")
		.when()
			.post("/users")
		.then()
			.log().all()
			.statusCode(400)
			.body("id", is(nullValue()))
			.body("error", is("Name é um atributo obrigatório"))

		;
	}

	@Test
	public void deveSalvarUsuarioXML() {
		given()
			.log().all()
			.contentType(ContentType.XML) //mais indicado do que o modo anterior
			.body("<user><name>NameTest</name><age>15</age></user>")
		.when()
			.post("/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("NameTest"))
			.body("user.age", is("15"))
		;
	}
	
	@Test
	public void deveAlterarUser () {
		given()
			.log().all()
			.contentType(ContentType.JSON) //mais indicado do que o modo anterior
			.body("{\"name\": \"nameChangedTest\", \"age\": 18}")
		.when()
			.put("/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("nameChangedTest"))
			.body("age", is(18))
		;
	}

	@Test
	public void deveCustomizarURL () {
		given()
			.log().all()
			.contentType(ContentType.JSON) //mais indicado do que o modo anterior
			.body("{\"name\": \"nameChangedTest\", \"age\": 18}")
			.pathParam("userID", 1) // chave + valor
		.when()
			.put("/users/{userID}")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("nameChangedTest"))
			.body("age", is(18))
		;
	}

	@Test
	public void deveDeletarUser() {
		given()
			.log().all()
		.when()
			.delete("/users/1")
		.then()
			.statusCode(204)
		;
	}
	
	@Test
	public void naoDeveDeletarUserInexistente() {
		given()
			.log().all()
		.when()
			.delete("/users/100")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
		;
	}
}
