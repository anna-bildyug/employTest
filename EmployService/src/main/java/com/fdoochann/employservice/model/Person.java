package com.fdoochann.employservice.model;

import javax.persistence.*;

/**
 * Created by Anna_Bildyug on 11/21/2016.
 */
@Entity
@Table(name = "Persons")
public class Person
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String firstName;
	private String lastName;
	private int age;
	private boolean isHired;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastname()
	{
		return lastName;
	}

	public void setLastname(String lastName)
	{
		this.lastName = lastName;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public boolean isHired()
	{
		return isHired;
	}

	public void setHired(boolean hired)
	{
		isHired = hired;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
}
