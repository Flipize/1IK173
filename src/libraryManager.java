import java.util.ArrayList;

public class libraryManager {

    public boolean isBookAvailable(String bookTitle) {
        ArrayList<Book> bookArrayList = DBManager.getBookArrayList();
        for (Book b : bookArrayList) {
            if (b.getTitle().equals(bookTitle)) {
                if (b.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }


    public returnBook


}
