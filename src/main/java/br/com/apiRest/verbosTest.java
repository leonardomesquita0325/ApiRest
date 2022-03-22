package br.com.apiRest;

import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

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
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Usuario via map");
		params.put("age", 50);
		given()
			.log().all()
			.contentType("application/json")
			.body(params)
		 .when()
		 	 .post("https://restapi.wcaquino.me/users")
		 .then()
		 	.log().all()
		 	.statusCode(201)
		 	.body("id", is(notNullValue()))
		 	.body("name", is("Usuario via map"))
		 	.body("age", is(50))
		 ;
	}
	@Test
	public void deveSalvarUsuarioUsandoObjeto() {
		User user = new User("Usuario via objeto",40);
		given()
			.log().all()
			.contentType("application/json")
			.body(user)
		 .when()
		 	 .post("https://restapi.wcaquino.me/users")
		 .then()
		 	.log().all()
		 	.statusCode(201)
		 	.body("id", is(notNullValue()))
		 	.body("name", is("Usuario via objeto"))
		 	.body("age", is(40))
		 ;
	}
	@Test
	public void deveDeserializarObejtoAoSalvarUsuraio() {
		User user = new User("Usuario deserializado",40);
		
		User usuarioInserido = given()
			.log().all()
			.contentType("application/json")
			.body(user)
		 .when()
		 	 .post("https://restapi.wcaquino.me/users")
		 .then()
		 	.log().all()
		 	.statusCode(201)
		 	.extract().body().as(User.class)
		 	;
		System.out.println(usuarioInserido);
		Assert.assertThat(usuarioInserido.getId(),notNullValue());
		Assert.assertEquals("Usuario deserializado", usuarioInserido.getName());
		Assert.assertThat(usuarioInserido.getAge(),is(40));
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
	public void deveSalvarUsuárioViaXmlViaObjeto() {
		User user = new User("Usuario XML",40);
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		 .when()
		 	 .post("https://restapi.wcaquino.me/usersXML")
		 .then()
		 	.log().all()
		 	.statusCode(201)
		 	.body("user.@id", is(notNullValue()))
		 	.body("user.name", is("Usuario XML"))
		 	.body("user.age", is("40"))
		 ;
	}
	@Test
	public void deveDeserializarXMLAoSalvarUsuario() {
		User user = new User("Usuario XML",40);
		User usuarioInserido = given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		 .when()
		 	 .post("https://restapi.wcaquino.me/usersXML")
		 .then()
		 	.log().all()
		 	.statusCode(201)
		 	.extract().body().as(User.class);
		 ;
		 Assert.assertThat(usuarioInserido.getId(), notNullValue());
		 Assert.assertThat(usuarioInserido.getName(),is("Usuario XML"));
		 Assert.assertThat(usuarioInserido.getAge(), is(40));
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