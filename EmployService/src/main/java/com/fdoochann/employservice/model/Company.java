package com.fdoochann.employservice.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Anna_Bildyug on 11/22/2016.
 */
@Entity
@Table(name = "Companies")
public class Company
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Employee> employees;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
