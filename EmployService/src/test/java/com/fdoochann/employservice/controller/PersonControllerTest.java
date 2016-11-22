package com.fdoochann.employservice.controller;

import com.fdoochann.employservice.Application;
import com.fdoochann.employservice.model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.*;

/**
 * Created by Anna_Bildyug on 11/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonControllerTest
{
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters)
	{
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);
		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getAllTest() throws Exception
	{
		mockMvc.perform(get("/persons"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(0)))
				.andExpect(jsonPath("$[0].firstName", is("John")))
				.andExpect(jsonPath("$[0].lastName", is("Doe")))
				.andExpect(jsonPath("$[0].age", is(50)))
				.andExpect(jsonPath("$[1].id", is(1)))
				.andExpect(jsonPath("$[1].firstName", is("Tom")))
				.andExpect(jsonPath("$[1].lastName", is("Smith")))
				.andExpect(jsonPath("$[1].age", is(30)));
	}

	@Test
	public void personNotFoundTest() throws Exception
	{
		mockMvc.perform(get("/persons/5")).andExpect(status().isNotFound());
	}

	@Test
	public void addPerson() throws Exception
	{
		Person person = new Person();
		person.setAge(50);
		person.setLastName("lastName");
		person.setFirstName("firstName");

		mockMvc.perform(post("/persons").content(this.json(person)).contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.firstName", is("firstName")))
				.andExpect(jsonPath("$.lastName", is("lastName")))
				.andExpect(jsonPath("$.age", is(50)));

		List persons = entityManager.createQuery("SELECT p FROM Person p").getResultList();
		assertEquals(3, persons.size());

		Person storedPerson = entityManager.find(Person.class, 2L);
		assertNotNull(storedPerson);
		assertEquals("firstName", storedPerson.getFirstName());
		assertEquals("lastName", storedPerson.getLastName());
		assertEquals(50, storedPerson.getAge());
	}

	@Test
	public void updatePerson() throws Exception
	{
		Person person = new Person();
		person.setId(0L);
		person.setAge(50);
		person.setLastName("lastName");
		person.setFirstName("firstName");

		mockMvc.perform(post("/persons").content(this.json(person)).contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(0)))
				.andExpect(jsonPath("$.firstName", is("firstName")))
				.andExpect(jsonPath("$.lastName", is("lastName")))
				.andExpect(jsonPath("$.age", is(50)));

		List persons = entityManager.createQuery("SELECT p FROM Person p").getResultList();
		assertEquals(2, persons.size());

		Person storedPerson = entityManager.find(Person.class, 0L);
		assertNotNull(storedPerson);
		assertEquals("firstName", storedPerson.getFirstName());
		assertEquals("lastName", storedPerson.getLastName());
		assertEquals(50, storedPerson.getAge());
	}

	@Test
	public void deleteExistedPersonWithoutLinks() throws Exception
	{
		mockMvc.perform(delete("/persons/"+1)).andExpect(status().isOk());

		mockMvc.perform(get("/persons"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));

		List persons = entityManager.createQuery("SELECT p FROM Person p").getResultList();
		assertEquals(1, persons.size());

	}

	protected String json(Object o) throws IOException
	{
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
