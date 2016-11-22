package com.fdoochann.employservice.model;

import javax.persistence.*;

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
}
