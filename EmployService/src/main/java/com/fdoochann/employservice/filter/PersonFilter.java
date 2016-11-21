package com.fdoochann.employservice.filter;

import com.fdoochann.employservice.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Anna_Bildyug on 11/21/2016.
 */
@Component
public class PersonFilter
{
	public Person getPersonById(List<Person> persons , long id)
	{
		Person filteredPerson = null;
		int i = 0;
		while (filteredPerson == null && i < persons.size())
		{
			Person person = persons.get(i);
			if (id == person.getId())
			{
				filteredPerson = person;
			}
			i++;
		}
		return filteredPerson;
	}
}
