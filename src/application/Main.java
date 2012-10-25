package application;

import view.BuchMaster;
import domain.Library;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Library library = new Library();
		LibraryApp libraryApp = new LibraryApp(library);		
		BuchMaster buchMaster = new BuchMaster(library);
	}
}
