package com.magicbox.redio.items;

public class Script
{
	private String name;
	private String code;
	private final int length;

	public Script(String name, String code)
	{
		setName(name);
		setCode(code);
		length = code.length();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}