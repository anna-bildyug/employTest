package com.fdoochann.employservice.repository;

import com.fdoochann.employservice.Application;
import com.fdoochann.employservice.model.Company;
import com.fdoochann.employservice.model.Employee;
import com.fdoochann.employservice.model.Person;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PERSIST_STORE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Администратор on 27.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void createEmployee()
    {
//        Company company = new Company();
//        company.setId(1L);
//
//        Person person = new Person();
//        person.setId(1L);
//        person.setFirstName("Billy");

        Employee employee = new Employee();
        employee.setCompany(entityManager.getReference(Company.class, 1L));
        employee.setPerson(entityManager.getReference(Person.class, 1L));
//
//        employee.setCompany(company);
//        employee.setPerson(person);

        Employee savedEmployee = employeeRepository.save(employee);

//        List employees = entityManager.createQuery("SELECT e FROM Employee e").getResultList();
//        Employee employeeFromDB = entityManager.find(Employee.class, savedEmployee.getId());
//        assertEquals(Long.valueOf(0), employees.get(1).getCompany().getId());
//        assertEquals(Long.valueOf(0), employees.get(1).getPerson().getId());
    }
}
