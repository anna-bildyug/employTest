package com.fdoochann.employservice.controller;

import com.fdoochann.employservice.exceptions.ResourceNotFoundException;
import com.fdoochann.employservice.model.Employee;
import com.fdoochann.employservice.model.Person;
import com.fdoochann.employservice.repository.EmployeeRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Anna_Bildyug on 11/22/2016.
 */
@RestController
public class EmployeeController
{
	@Autowired
	private EmployeeRepository employeeRepository;

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public List<Employee> get()
	{
		Iterable<Employee> employees = employeeRepository.findAll();
		return IteratorUtils.toList(employees.iterator());
	}

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
	public Employee get(@PathVariable("id") long id)
	{
		Employee filteredEmployee = employeeRepository.findOne(id);
		if (filteredEmployee == null)
		{
			throw new ResourceNotFoundException(id);
		}
		return filteredEmployee;
	}

	@RequestMapping(value = "/employees", method = RequestMethod.POST)
	public Employee post(@RequestBody Employee employee)
	{
		return employeeRepository.save(employee);
	}

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id)
	{
		employeeRepository.delete(id);
	}
}
