package br.com.apiRest;

import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class verbosTest {

	@Test
	public void deveSalvarUsuário() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\":\"Leonardo\",\"age\":25}")
		 .when()
		 	 .post("https://restapi.wcaquino.me/users")
		 .then()
		 	.log().all()
		 	.statusCode(201)
		 	.body("id", is(notNullValue()))
		 	.body("name", is("Leonardo"))
		 	.body("age", is(25))
		 ;
	}
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
     given()
		.log().all()
		.contentType("application/json")
		.body("{\"age\":25}")
	 .when()
	 	 .post("https://restapi.wcaquino.me/users")
	 .then()
	 	.log().all()
	 	.statusCode(400)
	 	.body("id", is(nullValue()))
	 	.body("error",is("Name é um atributo obrigatório"))
	 ;
	}
	@Test
	public void deveSalvarUsuárioViaXml() {
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body("<user><name>Larissa</name><age>30</age></user>")
		 .when()
		 	 .post("https://restapi.wcaquino.me/usersXML")
		 .then()
		 	.log().all()
		 	.statusCode(201)
		 	.body("user.@id", is(notNullValue()))
		 	.body("user.name", is("Larissa"))
		 	.body("user.age", is("30"))
		 ;
	}
	@Test
	public void deveAlterarUsuário() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\":\"Usuario Alterado\",\"age\":208}")
		 .when()
		 	 .put("https://restapi.wcaquino.me/users/1")
		 .then()
		 	.log().all()
		 	.statusCode(200)
		 	.body("id", is(1))
		 	.body("name", is("Usuario Alterado"))
		 	.body("age", is(208))
		 	.body("salary", is(1234.5678f))
		 ;
	}
	@Test
	public void devoCustomizarUrl() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\":\"Usuario Alterado\",\"age\":208}")
		 .when()
		 	 .put("https://restapi.wcaquino.me/{entidade}/{userId}","users","1")
		 .then()
		 	.log().all()
		 	.statusCode(200)
		 	.body("id", is(1))
		 	.body("name", is("Usuario Alterado"))
		 	.body("age", is(208))
		 	.body("salary", is(1234.5678f))
		 ;
}
	@Test
	public void devoCustomizarUrlParte2() {
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body("{\"name\":\"Usuario Alterado\",\"age\":208}")
			.pathParam("entidade", "users")
			.pathParam("userId","1")
		 .when()
		 	 .put("https://restapi.wcaquino.me/{entidade}/{userId}")
		 .then()
		 	.log().all()
		 	.statusCode(200)
		 	.body("id", is(1))
		 	.body("name", is("Usuario Alterado"))
		 	.body("age", is(208))
		 	.body("salary", is(1234.5678f))
		 ;
	}
	@Test
	public void deveRemoverusuario() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204)
		;
	}
	
	@Test
	public void naoDeveRemoverUsuarioExistente() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/1000")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
			;
	}
}	