public class Book {
    private int id;
    private int isbn;
    private String title;
    private boolean isAvailable;

    public Book() {
    }

    public Book(int id, int isbn, String title, boolean isAvailable) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.isAvailable = isAvailable;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public String toString() {
        String name = "";
        name = "ID: " + this.id + " ISBN: " + this.isbn + " Title: " + this.title;
        return name;
    }
}
