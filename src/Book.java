public class Book {
    private int isbn;
    private String title;
    private int count;

    public Book() {
    }

    public Book(int isbn, String title, int count) {
        this.isbn = isbn;
        this.title = title;
        this.count = count;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        String name = "";
        name = "Title: " + this.title + "\nISBN: " + this.isbn + "\nCount: " + this.count;
        return name;
    }
}
