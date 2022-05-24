package br.ce.scosta.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.portable.OutputStream;

public class FileTest {

	@Test
	public void devoObrigarEnvioDoArquivo() {
		given()
			.log().all()
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404)
			.body("error", is("Arquivo não enviado"))
		;
	}
	
	@Test
	public void devoEnviarOArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/java/br/ce/scosta/rest/UserXMLTest.java"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("UserXMLTest.java"))
		;
	}
	
	@Test
	public void deveNaoEnviarArquivosGrandes() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/WhatsApp Video 2022-05-18 at 10.39.29.mp4"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(413)
			.time(lessThan(3000L))
		;
	}
	
	@Test
	public void devoBaixarArquivo() throws IOException {
		byte[] image = given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/download")
		.then()
			.log().all()
			.statusCode(200)
			.extract().asByteArray()
		;
		
		File imagem = new File("src/main/resources/file.jpg");
		FileOutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		Assert.assertThat(imagem.length(), lessThan(100000L));
	}
}
