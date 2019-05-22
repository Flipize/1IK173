public class Book {
    private int id;
    private int isbn;
    private String title;
    private boolean status;

    public Book() {
    }

    public Book(int id, int isbn, String title, boolean status) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.status = status;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String toString() {
        String name = "";
        name = "ID: " + this.id + " ISBN: " + this.isbn + " Title: " + this.title;
        return name;
    }
}
