import java.util.ArrayList;

public class libraryManager {

    public boolean isBookAvailable(String bookTitle) {
        ArrayList<Book> bookArrayList = DBManager.getBookArrayList();
        for (Book b : bookArrayList) {
            if (b.getTitle() == bookTitle) {
                if (b.isStatus()) {
                    return true;
                }
            }
        }
        return false;
    }
}
