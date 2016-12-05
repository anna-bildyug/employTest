package com.fdoochann.employservice.transformer;

import com.fdoochann.employservice.bindingmodel.EmployeeBindingModel;
import com.fdoochann.employservice.exceptions.BadRequestException;
import com.fdoochann.employservice.model.Company;
import com.fdoochann.employservice.model.Employee;
import com.fdoochann.employservice.model.Person;
import com.fdoochann.employservice.repository.CompanyRepository;
import com.fdoochann.employservice.repository.PersonRepository;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Anna_Bildyug on 11/22/2016.
 */
@Component
public class EmployeeTransformer
{
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private PersonRepository personRepository;

	public Employee transform(EmployeeBindingModel bindingModel)
	{
		Employee model = new Employee();
		model.setId(bindingModel.getId());

		Long companyId = bindingModel.getCompanyId();
		Company company = new Company();
		company.setId(companyId);
		model.setCompany(company);

		Long personId = bindingModel.getPersonId();
		Person person = new Person();
		person.setId(personId);
		model.setPerson(person);

		return model;
	}

	public EmployeeBindingModel transform(Employee model)
	{
		EmployeeBindingModel bindingModel = new EmployeeBindingModel();
		bindingModel.setId(model.getId());
		bindingModel.setCompanyId(model.getCompany().getId());
		bindingModel.setPersonId(model.getPerson().getId());
		return  bindingModel;
	}
}
