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


    public static void deleteMemberLibrary (int id) {

        ArrayList <Member> tempArray = DBManager.getMemberArrayList();

        for (Member m : tempArray){
            if (m.getId() == id)  {
                DBManager.deleteMember(id);
                System.out.println("Member is deleted");
            }
            else
                System.out.println("No member found");
        }
    }





}
