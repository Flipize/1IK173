import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class libraryManager {

    public static void main(String[] args) {
        Member newMember = regApplicant("Bo Göran");
        checkRegistration(newMember);
    }

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


    public static void returnBook(int bookID, int memberID) {

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

    public static Member regApplicant(String name){
        ArrayList<Member> members = DBManager.getMemberArrayList();
        for (Member m : members) {
            if (m.getName().equals(name)){
                return m;
            }
        }
        Member newMember = new Member();
        newMember.setName(name);
        newMember.setSuspended(false);
        return newMember;
    }

    public static boolean checkRegistration(Member m) {
        if (m.getMembershipType() != null) {
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
            m.setId(9029);
            m.setMembershipType("PhD");
            DBManager.addMember(m);
            System.out.println("An account for " + m.getName() + "(" + m.getId() + ") has been created.");
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

    public static void lendItem (int id, String bookName) {

        ArrayList<Member> members = DBManager.getMemberArrayList();
        ArrayList<Book> books = DBManager.getBookArrayList();

        for (Member m : members) {
            if (m.getId() == id) {
                System.out.println("Member found");
            }
            else
                System.out.println("Please try again");

        }

        for (Member m : members) {
            if (m.getMembershipType().equals("Undergraduate")) {
            }
        }


    }


}
