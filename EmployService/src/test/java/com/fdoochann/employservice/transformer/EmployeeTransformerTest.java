package com.fdoochann.employservice.transformer;

import com.fdoochann.employservice.bindingmodel.EmployeeBindingModel;
import com.fdoochann.employservice.model.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
/**
 * Created by Anna_Bildyug on 12/5/2016.
 */
public class EmployeeTransformerTest
{
	private EmployeeTransformer transformer;

	@Before
	public void setUp()
	{
		transformer = new EmployeeTransformer();
	}

	@Test
	public void getEmployeeModelFromEmployeeBindingModelWithId()
	{
		EmployeeBindingModel bindingModel = new EmployeeBindingModel();
		bindingModel.setId(1L);
		bindingModel.setCompanyId(2L);
		bindingModel.setPersonId(3L);
		Employee model = transformer.transform(bindingModel);
		assertEquals("Employee id", bindingModel.getId(), model.getId());
		assertEquals("Company id", bindingModel.getCompanyId(), model.getCompany().getId());
		assertEquals("Person id", bindingModel.getPersonId(), model.getPerson().getId());
	}

	@Test
	public void getEmployeeModelFromEmployeeBindingModelWithoutId()
	{
		EmployeeBindingModel bindingModel = new EmployeeBindingModel();
		bindingModel.setCompanyId(2L);
		bindingModel.setPersonId(3L);
		Employee model = transformer.transform(bindingModel);
		assertEquals("Employee id", bindingModel.getId(), model.getId());
		assertEquals("Company id", bindingModel.getCompanyId(), model.getCompany().getId());
		assertEquals("Person id", bindingModel.getPersonId(), model.getPerson().getId());
	}
}
