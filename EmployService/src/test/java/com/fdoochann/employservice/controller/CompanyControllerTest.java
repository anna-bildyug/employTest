package com.fdoochann.employservice.controller;

import com.fdoochann.employservice.Application;
import com.fdoochann.employservice.model.Company;
import com.fdoochann.employservice.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Anna_Bildyug on 11/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class CompanyControllerTest
{
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;


	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters)
	{
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);
		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getAllTest() throws Exception
	{
		mockMvc.perform(get("/companies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Home")));
	}

	@Test
	public void personNotFoundTest() throws Exception
	{
		mockMvc.perform(get("/companies/5")).andExpect(status().isNotFound());
	}

	@Test
	public void getOne() throws Exception
	{
		mockMvc.perform(get("/companies/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Home")));
	}

	@Test
	public void addCompany() throws Exception
	{
		Company company = new Company();
		company.setName("Mine");

		mockMvc.perform(post("/companies").content(this.json(company)).contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.name", is("Mine")));

		List companies = entityManager.createQuery("SELECT c FROM Company c").getResultList();
		assertEquals(2, companies.size());

		Company storedCompany = entityManager.find(Company.class, 2L);
		assertNotNull(storedCompany);
		assertEquals("Mine", storedCompany.getName());
	}

	@Test
	public void updateCompany() throws Exception
	{
		Company company = new Company();
		company.setName("SweetHome");

		mockMvc.perform(put("/companies/1").content(this.json(company)).contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("SweetHome")));

		List companies = entityManager.createQuery("SELECT c FROM Company c").getResultList();
		assertEquals(1, companies.size());

		Company storedCompany = entityManager.find(Company.class, 1L);
		assertNotNull(storedCompany);
		assertEquals("SweetHome", storedCompany.getName());
	}

	@Test
	public void updateNotExistedCompany() throws Exception
	{
		Company company = new Company();
		company.setName("SweetHome");

		mockMvc.perform(put("/companies/5").content(this.json(company)).contentType(contentType)).andExpect(status().isNotFound());

		List companies = entityManager.createQuery("SELECT c FROM Company c").getResultList();
		assertEquals(1, companies.size());

		Company storedCompany = entityManager.find(Company.class, 1L);
		assertNotNull(storedCompany);
		assertEquals("Home", storedCompany.getName());
	}

	@Test
	public void deleteCompanyWithoutLinkedEntities() throws Exception
	{
		mockMvc.perform(delete("/companies/1")).andExpect(status().isOk());

		List companies = entityManager.createQuery("SELECT p FROM Company p").getResultList();
		assertEquals(0, companies.size());
	}

	@Test
	public void deleteCompanyWithIncorrectId() throws Exception
	{
		mockMvc.perform(delete("/companies/5")).andExpect(status().isNotFound());

		List companies = entityManager.createQuery("SELECT p FROM Company p").getResultList();
		assertEquals(1, companies.size());
	}

	protected String json(Object o) throws IOException
	{
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
