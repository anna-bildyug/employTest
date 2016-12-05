package com.fdoochann.employservice.repository;

import com.fdoochann.employservice.Application;
import com.fdoochann.employservice.model.Company;
import com.fdoochann.employservice.model.Employee;
import com.fdoochann.employservice.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;

/**
 * Created by Администратор on 27.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeRepositoryTest
{
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void createEmployee()
	{
		Company company = new Company();
		company.setId(1L);

		Person person = new Person();
		person.setId(1L);

		Employee employee = new Employee();
		employee.setCompany(company);
		employee.setPerson(person);

		Employee savedEmployee = employeeRepository.save(employee);
		Employee employeeFromDB = entityManager.find(Employee.class, savedEmployee.getId());
		assertEquals(Long.valueOf(1), employeeFromDB.getCompany().getId());
		assertEquals(Long.valueOf(1), employeeFromDB.getPerson().getId());
	}
}
