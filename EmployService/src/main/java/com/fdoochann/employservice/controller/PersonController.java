package com.fdoochann.employservice.controller;

import java.util.ArrayList;
import java.util.List;

import com.fdoochann.employservice.filter.PersonFilter;
import com.fdoochann.employservice.exceptions.ResourceNotFoundException;
import com.fdoochann.employservice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController
{
	private List<Person> persons = new ArrayList();
	@Autowired
	private PersonFilter personFilter;

	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public List<Person> get()
	{
		return persons;
	}

	@RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
	public Person get(@PathVariable("id") long id)
	{
		Person filteredPerson = personFilter.getPersonById(persons, id);
		if (filteredPerson == null)
		{
			throw new ResourceNotFoundException(id);
		}
		return filteredPerson;
	}

	@RequestMapping(value = "/persons", method = RequestMethod.POST)
	public Person post(@RequestBody Person person)
	{
		persons.remove(person);
		persons.add(person);
		return person;
	}

	@RequestMapping(value = "/persons", method = RequestMethod.DELETE)
	public void delete(@RequestBody Person person)
	{
		persons.remove(person);
	}

}