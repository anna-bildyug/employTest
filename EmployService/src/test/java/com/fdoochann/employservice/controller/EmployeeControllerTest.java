package com.fdoochann.employservice.controller;

import com.fdoochann.employservice.Application;
import com.fdoochann.employservice.bindingmodel.EmployeeBindingModel;
import com.fdoochann.employservice.model.Company;
import com.fdoochann.employservice.model.Employee;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class EmployeeControllerTest
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
		Person person = entityManager.getReference(Person.class, 1L);
		Company company = entityManager.getReference(Company.class, 1L);
		Employee employee = new Employee();
		employee.setPerson(person);
		employee.setCompany(company);
		entityManager.persist(employee);

		mockMvc.perform(get("/employees"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].personId", is(1)))
				.andExpect(jsonPath("$[0].companyId", is(1)));
	}

	@Test
	public void personNotFoundTest() throws Exception
	{
		mockMvc.perform(get("/employees/5")).andExpect(status().isNotFound());
	}

	@Test
	public void getEmployee() throws Exception
	{
		Person person = entityManager.getReference(Person.class, 1L);
		Company company = entityManager.getReference(Company.class, 1L);
		Employee employee = new Employee();
		employee.setPerson(person);
		employee.setCompany(company);
		entityManager.persist(employee);
		mockMvc.perform(get("/employees/1")).andExpect(status().isOk());
	}

	@Test
	public void addCorrectEmployee() throws Exception
	{
		EmployeeBindingModel model = new EmployeeBindingModel();
		model.setPersonId(1L);
		model.setCompanyId(1L);
		mockMvc.perform(post("/employees").content(this.json(model)).contentType(contentType)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.personId", is(1)))
				.andExpect(jsonPath("$.companyId", is(1)));
		List employees = entityManager.createQuery("SELECT e FROM Employee e").getResultList();
		assertEquals(1, employees.size());
	}

	@Test
	public void addEmployeeWithIncorrectCompany() throws Exception
	{
		EmployeeBindingModel model = new EmployeeBindingModel();
		model.setPersonId(1L);
		model.setCompanyId(2L);
		mockMvc.perform(post("/employees").content(this.json(model)).contentType(contentType)).andExpect(status().isBadRequest());
		List employees = entityManager.createQuery("SELECT e FROM Employee e").getResultList();
		assertEquals(0, employees.size());
	}

	@Test
	public void addEmployeeWithIncorrectPerson() throws Exception
	{
		EmployeeBindingModel model = new EmployeeBindingModel();
		model.setPersonId(3L);
		model.setCompanyId(1L);
		mockMvc.perform(post("/employees").content(this.json(model)).contentType(contentType)).andExpect(status().isBadRequest());
		List employees = entityManager.createQuery("SELECT e FROM Employee e").getResultList();
		assertEquals(0, employees.size());
	}

	@Test
	public void updateEmployee() throws Exception
	{
		Person person = entityManager.getReference(Person.class, 1L);
		Company company = entityManager.getReference(Company.class, 1L);
		Employee employee = new Employee();
		employee.setPerson(person);
		employee.setCompany(company);
		entityManager.persist(employee);


	}

	protected String json(Object o) throws IOException
	{
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
