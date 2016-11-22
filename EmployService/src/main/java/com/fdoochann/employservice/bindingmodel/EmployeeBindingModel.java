package com.fdoochann.employservice.bindingmodel;

import javax.validation.constraints.NotNull;

/**
 * Created by Anna_Bildyug on 11/22/2016.
 */
public class EmployeeBindingModel
{
	private Long id;
	@NotNull
	private Long personId;
	@NotNull
	private Long companyId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getPersonId()
	{
		return personId;
	}

	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	public Long getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(Long companyId)
	{
		this.companyId = companyId;
	}
}
