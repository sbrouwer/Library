package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Library extends Observable implements Observer
{
	private List<Copy> copies;
	private List<Customer> customers;
	private List<Loan> loans;
	private List<Book> books;
	public final static int MAX_AMOUNT_OF_LOANS = 3;

	public Library()
	{
		copies = new ArrayList<Copy>();
		customers = new ArrayList<Customer>();
		loans = new ArrayList<Loan>();
		books = new ArrayList<Book>();
	}

	public Loan createAndAddLoan(Customer customer, Copy copy)
	{
		if (!isCopyLent(copy))
		{
			Loan l = new Loan(customer, copy);
			loans.add(l);
			l.addObserver(this);
			libraryChanged();
			return l;
		} else
		{
			return null;
		}
	}

	public Customer createAndAddCustomer(String name, String surname)
	{
		Customer c = new Customer(name, surname);
		customers.add(c);
		c.addObserver(this);
		libraryChanged();
		return c;
	}

	public Book createAndAddBook(String name)
	{
		Book b = new Book(name);
		books.add(b);
		libraryChanged();
		return b;
	}

	public Copy createAndAddCopy(Book title)
	{
		Copy c = new Copy(title);
		copies.add(c);
		libraryChanged();
		return c;
	}

	public Book findByBookTitle(String title)
	{
		for (Book b : books)
		{
			if (b.getName().equals(title))
			{
				return b;
			}
		}
		return null;
	}

	public boolean isCopyLent(Copy copy)
	{
		for (Loan l : loans)
		{
			if (l.getCopy().equals(copy) && l.isLent())
			{
				return true;
			}
		}
		return false;
	}

	public List<Copy> getCopiesOfBook(Book book)
	{
		List<Copy> res = new ArrayList<Copy>();
		for (Copy c : copies)
		{
			if (c.getTitle().equals(book))
			{
				res.add(c);
			}
		}
		return res;
	}

	public List<Loan> getLentCopiesOfBook(Book book)
	{
		List<Loan> lentCopies = new ArrayList<Loan>();
		for (Loan l : loans)
		{
			if (l.getCopy().getTitle().equals(book) && l.isLent())
			{
				lentCopies.add(l);
			}
		}
		return lentCopies;
	}


	public List<Loan> getCustomerLoans(Customer customer) {
		List<Loan> lentCopies = new ArrayList<Loan>();
		for (Loan l : loans) {
			if (l.getCustomer().equals(customer)) {
				lentCopies.add(l);
			}
		}
		return lentCopies;
	}

	public List<Loan> getOverdueLoans()
	{
		List<Loan> overdueLoans = new ArrayList<Loan>();
		for (Loan l : getLoans())
		{
			if (l.isOverdue())
				overdueLoans.add(l);
		}
		return overdueLoans;
	}

	public Loan getLoanOfCopy(Copy copy)
	{
		List<Loan> lent = this.getLentCopiesOfBook(copy.getTitle());
		if (this.isCopyLent(copy))
		{
			for (Loan l : lent)
			{
				if (l.getCopy().equals(copy))
				{
					return l;
				}
			}
		}
		return null;
	}

	public List<Copy> getAvailableCopies()
	{
		return getCopies(false);
	}

	public List<Copy> getLentOutCopies()
	{
		return getCopies(true);
	}

	private List<Copy> getCopies(boolean isLent)
	{
		List<Copy> retCopies = new ArrayList<Copy>();
		for (Copy c : copies)
		{
			if (isLent == isCopyLent(c))
			{
				retCopies.add(c);
			}
		}
		return retCopies;
	}
	
	public List<Loan> getActualLoansByCustomer(Customer customer)
	{
		List<Loan> retLoans = new ArrayList<Loan>();
		for (Loan l : getCustomerLoans(customer))
		{
			if (l.isLent())
			{
				retLoans.add(l);
			}
		}
		return retLoans;
	}

	public List<Copy> getCopies()
	{
		return copies;
	}

	public List<Loan> getLoans()
	{
		return loans;
	}

	public List<Book> getBooks()
	{
		return books;
	}

	public List<Customer> getCustomers()
	{
		return customers;
	}

	public void removeCopy(Copy c)
	{
		copies.remove(c);
		libraryChanged();
	}

	public Copy getCopyByInventoryNumber(long inventoryNumber)
	{
		Copy copy = null;
		for (Copy c : copies)
		{
			if (c.getInventoryNumber() == inventoryNumber)
			{
				copy = c;
			}
		}
		return copy;
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		libraryChanged();		
	}

	private void libraryChanged()
	{
		setChanged();
		notifyObservers();
	}
	
    public boolean checkMaxLoanAmount(Customer customer) {
        return getActualLoansByCustomer(customer).size() < Library.MAX_AMOUNT_OF_LOANS;
    }
    
    public boolean checkInventoryNumberExists(String potentialInventoryNumber) {
        boolean result = true;
        try {
            if ((Long.parseLong(potentialInventoryNumber) < 0) || getCopyByInventoryNumber(Long.parseLong(potentialInventoryNumber)) == null) {
                result = false;
            }
        } catch (NumberFormatException nfe) {
            result = false;
        }
        return result;
    }
    
    public boolean checkHasOverdueLoans(Customer customer) {
        for (Loan l : getCustomerLoans(customer)) {
            if (l.isOverdue()) {
                return true;
            }
        }
        return false;
    }
    
    public List<Loan> getOverdueLoans(Customer customer) {
        List<Loan> customerOverdueLoans = new ArrayList<Loan>();     
        for (Loan l : getCustomerLoans(customer)) {
            if (l.isOverdue()) {
                customerOverdueLoans.add(l);
            }
        }
        return customerOverdueLoans;
    }
}
