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

}
