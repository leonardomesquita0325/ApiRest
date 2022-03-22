package br.com.apiRest;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class envioDadosTest {

	@Test
	public void deveEnviarValorViaQuery() {
		given()
			.log().all()
		 .when()
		 	.get("https://restapi.wcaquino.me/v2/users?format=xml")
		 .then()
		 	.log().all()
		 	.statusCode(200)
		 	.contentType(ContentType.XML)
		 ;
	}
	@Test
	public void deveEnviarValorViaQueryViaParam() {
		given()
			.log().all()
			.queryParam("format", "xml")
			.queryParam("outra", "coisa")//verifica qualquer outro parametro passado
		 .when()
		 	.get("https://restapi.wcaquino.me/v2/users")
		 .then()
		 	.log().all()
		 	.statusCode(200)
		 	.contentType(ContentType.XML)
		 	.contentType(Matchers.containsString("utf-8"))
		 ;
	}
	@Test
	public void deveEnviarValorViaHeader() {
		given()
			.log().all()
			.accept(ContentType.XML)
		 .when()
		 	.get("https://restapi.wcaquino.me/v2/users")
		 .then()
		 	.log().all()
		 	.statusCode(200)
		 	.contentType(ContentType.XML)
		 ;
	}
	
	
}
