public class Book {
    private int id;
    private int isbn;
    private String title;

    public Book() {
    }

    public Book(int id, int isbn, String title) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String toString() {
        String name = "";
        name = "ID: " + this.id + "ISBN: " + this.isbn + " Title: " + this.title;
        return name;
    }
}
