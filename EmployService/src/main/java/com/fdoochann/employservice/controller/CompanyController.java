package com.fdoochann.employservice.controller;

import com.fdoochann.employservice.exceptions.ResourceNotFoundException;
import com.fdoochann.employservice.model.Company;
import com.fdoochann.employservice.repository.CompanyRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Anna_Bildyug on 11/22/2016.
 */
@RestController
public class CompanyController
{
	@Autowired
	private CompanyRepository companyRepository;

	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public List<Company> get()
	{
		Iterable<Company> companies = companyRepository.findAll();
		return IteratorUtils.toList(companies.iterator());
	}

	@RequestMapping(value = "/companies/{id}", method = RequestMethod.GET)
	public Company get(@PathVariable("id") long id)
	{
		Company filteredCompany = companyRepository.findOne(id);
		if (filteredCompany == null)
		{
			throw new ResourceNotFoundException(id);
		}
		return filteredCompany;
	}

	@RequestMapping(value = "/companies", method = RequestMethod.POST)
	public Company post(@RequestBody Company company)
	{
		return companyRepository.save(company);
	}

	@RequestMapping(value = "/companies/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id)
	{
		companyRepository.delete(id);
	}
}
