package br.ce.wcaquino.tasks.apitest;

import java.net.MalformedURLException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
			;
	}
	
	@Test
	public void adicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2030-12-11\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2010-12-11\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
			;
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() {
		//inserir tarefa
		Integer id = RestAssured.given()
			.body("{ \"task\": \"Tarefa Teste\", \"dueDate\": \"2030-12-11\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
			.extract().path("id")
		;
		System.out.println(id);
		//remover
		RestAssured.given()
		.when()
			.delete("/todo/"+id)
		.then()
			.statusCode(204)

		;
		}

}


