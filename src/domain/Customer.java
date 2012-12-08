package domain;

import java.util.Observable;

public class Customer extends Observable
{

	private String name, surname, street, city;
	private int zip;
	public static long nextIdentifier = 1;
	private final long identifier;

	public Customer(String name, String surname)
	{
		this.name = name;
		this.surname = surname;
		identifier = nextIdentifier++;
	}

	public void setAdress(String street, int zip, String city)
	{
		this.street = street;
		this.zip = zip;
		this.city = city;
		sometingChanged();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
		sometingChanged();
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
		sometingChanged();
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
		sometingChanged();
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
		sometingChanged();
	}

	public int getZip()
	{
		return zip;
	}

	public void setZip(int zip)
	{
		this.zip = zip;
		sometingChanged();
	}

	@Override
	public String toString()
	{
		return name + " " + surname + " , " + street + " , " + zip + " " + city;
	}

	public long getIdentifier()
	{
		return identifier;
	}
	
	private void sometingChanged() {
	    setChanged();
	    notifyObservers();
	}

}
