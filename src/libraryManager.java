import java.util.ArrayList;

public class libraryManager {

    public boolean isBookAvailable(String bookTitle) {
        ArrayList<Book> bookArrayList = DBManager.getBookArrayList();
        for (Book b : bookArrayList) {
            if (b.getTitle() == bookTitle) {
                if (b.isStatus() == true) {
                    return true;
                }
            }
        }
        return false;
    }
}
