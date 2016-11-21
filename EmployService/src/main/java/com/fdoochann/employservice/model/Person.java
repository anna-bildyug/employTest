package com.fdoochann.employservice.model;

/**
 * Created by Anna_Bildyug on 11/21/2016.
 */
public class Person
{
	private long id;
	private String firstName;
	private String lastname;
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
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
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
