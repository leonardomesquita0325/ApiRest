package br.com.apiRest;

import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;

public class fileText {

	@Test
	public void deveObrigarEnvioArquivo() {
		given()
			.log().all()
	    .when()
	        .post("http://restapi.wcaquino.me/upload")
	    .then()
	        .log().all()
	        .statusCode(404) //Deveria ser um 400
	        .body("error", is("Arquivo não enviado"))
	    ;
	}
	@Test
	public void deveFazerUploadArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/users.pdf"))
	    .when()
	        .post("http://restapi.wcaquino.me/upload")
	    .then()
	        .log().all()
	        .statusCode(200)
	        .body("name", is("users.pdf"))
	    ;
	}
	@Test
	public void naodeveFazerUploadArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/iText-2.1.0.jar"))
	    .when()
	        .post("http://restapi.wcaquino.me/upload")
	    .then()
	        .log().all()
	        .time(lessThan(5000L))
	        .statusCode(413)
	    ;
	}
	@Test
	public void deveFazerDownloadArquivo() throws IOException {
		byte[] image=given()
			.log().all()
	    .when()
	        .get("http://restapi.wcaquino.me/download")
	    .then()
	        .log().all()
	        .statusCode(200)
	        .extract().asByteArray();
	    ;
	    File imagem = new File("src/main/resources/file.jpg");
	    OutputStream out = new FileOutputStream(imagem);
		out.write(image);
	    out.close();
	    
	    Assert.assertThat(imagem.length(), lessThan(100000L));
	}
}
