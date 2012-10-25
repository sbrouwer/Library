package application;

import view.BuookMaster;
import domain.Library;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Library library = new Library();
		LibraryApp libraryApp = new LibraryApp(library);		
		BuookMaster buchMaster = new BuookMaster(library);
	}
}