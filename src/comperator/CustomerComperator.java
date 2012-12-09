package comperator;

import java.util.Comparator;

import domain.Customer;

public class CustomerComperator implements Comparator<Customer>
{
	@Override
	public int compare(Customer o1, Customer o2)
	{
		return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
	}
}