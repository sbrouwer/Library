package domain;

import java.util.Observable;

public class Book extends Observable{
	
	private String title, author, publisher;
	private Shelf shelf;
	
	public Book(String name) {
		this.title = name;
	}

	public String getName() {
		return title;
	}

	public void setName(String name) {
		this.title = name;
		bookChanged();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String autor) {
		this.author = autor;
		bookChanged();
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
		bookChanged();
	}
	
	public Shelf getShelf() {
		return shelf;
	}
	
	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
		bookChanged();
	}
	
	@Override
	public String toString() {
		return title + ", " + author + ", " + publisher;
	}
	
	private void bookChanged(){
		setChanged();
		notifyObservers();
	}
}
