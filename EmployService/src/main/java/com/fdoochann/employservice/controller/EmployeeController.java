package com.fdoochann.employservice.controller;

import com.fdoochann.employservice.bindingmodel.EmployeeBindingModel;
import com.fdoochann.employservice.exceptions.ResourceNotFoundException;
import com.fdoochann.employservice.model.Employee;
import com.fdoochann.employservice.repository.EmployeeRepository;
import com.fdoochann.employservice.transformer.EmployeeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna_Bildyug on 11/22/2016.
 */
@RestController
public class EmployeeController
{
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeTransformer employeeTransformer;

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public List<EmployeeBindingModel> get()
	{
		Iterable<Employee> storedEmployees = employeeRepository.findAll();

		List<EmployeeBindingModel>  employees = new ArrayList<>();
		storedEmployees.forEach(employee ->
		{
			employees.add(employeeTransformer.transform(employee));
		});

		return employees;
	}

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
	public EmployeeBindingModel get(@PathVariable("id") long id)
	{
		Employee storedEmployee = employeeRepository.findOne(id);

		if (storedEmployee == null)
		{
			throw new ResourceNotFoundException(id);
		}
		return employeeTransformer.transform(storedEmployee);
	}

	@RequestMapping(value = "/employees", method = RequestMethod.POST)
	public EmployeeBindingModel post(@RequestBody EmployeeBindingModel employee)
	{
		Employee model = employeeTransformer.transform(employee);
		Employee storedModel = employeeRepository.save(model);
		EmployeeBindingModel bindingModel = employeeTransformer.transform(storedModel);
		return bindingModel;
	}

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id)
	{
		employeeRepository.delete(id);
	}
}
