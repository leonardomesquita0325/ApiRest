package br.com.apiRest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class hello {
	
	@Test
	public void testHello() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
		assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		assertTrue(response.statusCode()==200);
		assertTrue("O satus code deve ser o 200",response.statusCode()==200);
		assertEquals(200,response.statusCode());
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		get("https://restapi.wcaquino.me/ola").then().statusCode(200);
		
		//Given,When,Then(Dado, E e Então)
		
		given() //pré condição
		.when() //ação
		    .get("https://restapi.wcaquino.me/ola")
		.then() //Assertivas
		    .statusCode(200);
	}
	@SuppressWarnings("deprecation")
	@Test 
	public void devoConhecerMatchersHamcrest() {
		assertThat("Maria",is("Maria"));
	    assertThat(128,Matchers.is(128));
		assertThat(128,Matchers.isA(Integer.class));
		assertThat(128d,Matchers.isA(Double.class));
		assertThat(128d,Matchers.greaterThan(120d));
		assertThat(128d,Matchers.lessThan(130d));
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		assertThat(impares,hasSize(5));
		assertThat(impares,contains(1,3,5,7,9));
		assertThat(impares,containsInAnyOrder(1,3,5,9,7));
		assertThat(impares,hasItem(1));
		assertThat(impares,hasItems(1,3,5));
		
		assertThat("Maria",is(not("Joao")));
		assertThat("Maria",not("Joao"));
		assertThat("Maria",anyOf(is("Maria"),is("Joaquina")));
		assertThat("Joaquina",allOf(startsWith("Joa"),endsWith("ina"),containsString("qui")));
		
		//lista de matchers hamcrest (hamcrest.org/JavaHamcrest/javadoc
	}
	@Test
	public void devoValidarBody() {
		given()
		.when()
		    .get("https://restapi.wcaquino.me/ola")
		.then()
		    .statusCode(200)
		    .body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}
}
