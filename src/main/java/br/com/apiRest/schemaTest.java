package br.com.apiRest;

import org.junit.Test;

import io.restassured.matcher.RestAssuredMatchers;
import org.xml.sax.SAXParseException;

import com.jayway.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.given;

public class schemaTest {
	
	@Test
	public void deveValidarSchemaXML() {
		given()
			 .log().all()
		  .when()
		  	 .get("https://restapi.wcaquino.me/usersXML")
		  .then()
		     .log().all()
		     .statusCode(200)
		     .body(RestAssuredMatchers.matchesXsdInClasspath("user.xsd"))
		;
	}
	@Test (expected=SAXParseException.class)
	public void deveValidarSchemaXMLInvalido() {
		given()
			 .log().all()
		  .when()
		  	 .get("https://restapi.wcaquino.me/invalidUsersXML")
		  .then()
		     .log().all()
		     .statusCode(200)
		     .body(RestAssuredMatchers.matchesXsdInClasspath("user.xsd"))
		;
	}
	@Test
	public void deveValidarSchemaJSON() {
		given()
			 .log().all()
		  .when()
		  	 .get("https://restapi.wcaquino.me/users")
		  .then()
		     .log().all()
		     .statusCode(200)
		     .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("user.json"))
		;
	}

}
