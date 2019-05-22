import java.util.ArrayList;

import static java.lang.Integer.parseInt;

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


    public void returnBook(int bookID, int memberID) {

        ArrayList<String[]> loanArray = DBManager.getLoanArrayList();
        ArrayList<Book> bookArray = DBManager.getBookArrayList();

        for (String[] st: loanArray) {
            if (parseInt(st[1]) == memberID && (parseInt(st[0]) == bookID)) {
                for (Book b : bookArray){
                    if (b.getId() == bookID){
                        b.setAvailable(true);
                        DBManager.updateBook(b);
                    }
                    DBManager.deleteLoan(bookID, memberID);
                    System.out.println("Book succesfully returned.");
                }
            } else System.out.println("Book not eligible for return.");
        }
    }

    public Member regApplicant(String name){
        ArrayList<Member> members = DBManager.getMemberArrayList();
        for (Member m : members) {
            if (m.getName().equals(name)){
                return m;
            }
        }
        return null;
    }

    public boolean checkRegistration(Member m) {
        if (m != null) {
            if (m.isSuspended()){
                System.out.println("You are suspended.");
                return false;
            }
            else {
                System.out.println("You are already registered.");
                return false;
            }
        }
        else {
            Member newMember = new Member(19, "Filip Axel Nilsson", "Coolboy", false);
            DBManager.addMember(newMember);
            return true;
        }
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
