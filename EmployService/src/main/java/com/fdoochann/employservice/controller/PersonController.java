package com.fdoochann.employservice.controller;

import java.util.ArrayList;
import java.util.List;

import com.fdoochann.employservice.filter.PersonFilter;
import com.fdoochann.employservice.exceptions.ResourceNotFoundException;
import com.fdoochann.employservice.model.Person;
import com.fdoochann.employservice.repository.PersonRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController
{
	@Autowired
	private PersonRepository personRepository;

	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public List<Person> get()
	{
		Iterable<Person> persons = personRepository.findAll();
		return IteratorUtils.toList(persons.iterator());
	}

	@RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
	public Person get(@PathVariable("id") long id)
	{
		Person filteredPerson = personRepository.findOne(id);
		if (filteredPerson == null)
		{
			throw new ResourceNotFoundException(id);
		}
		return filteredPerson;
	}

	@RequestMapping(value = "/persons", method = RequestMethod.POST)
	public Person post(@RequestBody Person person)
	{
		return personRepository.save(person);
	}

	@RequestMapping(value = "/persons/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id)
	{
		personRepository.delete(id);
	}

}