import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class libraryManager {

    public static void main(String[] args) {
        //registerNewMember(199405019886L);

        lendItem(5, 2222);
    }

    public static boolean isBookAvailable(int isbn) {
        ArrayList<Book> bookArrayList = DBManager.getBookArrayList();
        for (Book b : bookArrayList) {
            if (b.getIsbn() == isbn) {
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

        for (String[] st : loanArray) {
            if (parseInt(st[1]) == memberID && (parseInt(st[0]) == bookID)) {
                for (Book b : bookArray) {
                    if (b.getId() == bookID) {
                        b.setAvailable(true);
                        DBManager.updateBook(b);
                    }
                    DBManager.deleteLoan(bookID, memberID);
                    System.out.println("Book succesfully returned.");
                }
            } else System.out.println("Book not eligible for return.");
        }
    }

    public static Member regApplicant(long personalNumber) {
        ArrayList<Member> members = DBManager.getMemberArrayList();
        for (Member m : members) {
            if (m.getPersonalNumber() == personalNumber) {
                return m;
            }
        }
        Member newMember = new Member();
        newMember.setPersonalNumber(personalNumber);
        return newMember;
    }

    public static boolean checkRegistration(Member m) {
        if (m.getMembershipType() != null) {
            if (isBanned(m.getPersonalNumber())) {
                System.out.println("You are suspended.");
                return false;
            } else {
                System.out.println("You are already registered.");
                return false;
            }
        } else {
            int rndId = getRandId();
            while (!idIsValid(rndId)){
                rndId = getRandId();
            }
            m.setId(rndId);
            Scanner reader = new Scanner(System.in);
            System.out.println("What membership type? ");
            m.setMembershipType(reader.nextLine());
            DBManager.addMember(m);
            System.out.println("An account for " + m.getName() + " (" + m.getId() + ") has successfully been created.");
            return true;
        }
    }

    public static void deleteMemberLibrary(int id) {

        ArrayList<Member> tempArray = DBManager.getMemberArrayList();

        for (Member m : tempArray) {
            if (m.getId() == id) {
                DBManager.deleteMember(id);
                System.out.println("Member is deleted");
            } else
                System.out.println("No member found");
        }

    }


    public static void lendItem(int memberID, int bookID) {

        ArrayList<Member> members = DBManager.getMemberArrayList();
        ArrayList<Book> books = DBManager.getBookArrayList();

        boolean foundMember = false;


        for (Member m : members) {
            if (m.getId() == memberID) {
                foundMember = true;

            }
        }
        if (!foundMember) {
            System.out.println("Member found: ");


            //Kollar om medlemmen får låna fler böcker

            boolean canMemberLend = false;

            for (Member m : members) {
                if (m.getMembershipType().equals("Student")) {
                    if (DBManager.loanCount(memberID) >= 3) {
                        canMemberLend = true;
                        break;
                    }
                } else if (m.getMembershipType().equals("Masterstudent")) {
                    if (DBManager.loanCount(memberID) >= 5) {
                        canMemberLend = true;
                        break;
                    }
                } else if (m.getMembershipType().equals("PhD")) {
                    if (DBManager.loanCount(memberID) >= 7) {
                        canMemberLend = true;
                        break;
                    }
                } else if (m.getMembershipType().equals("Teacher")) {
                    if (DBManager.loanCount(memberID) >= 10) {
                        canMemberLend = true;
                        break;
                    }
                }
            }
            if (!canMemberLend) {
                System.out.println("Borrowing books allowed. Loan count is currently: " + DBManager.loanCount(memberID) + " books");
            } else
                System.out.println("You cannot borrow any more books. The loan count is currently at maximum " + DBManager.loanCount(memberID) + " books");

        }
        boolean foundBook = false;
        for (Book b : books) {
            if (b.getId() == bookID) {
                if (b.isAvailable()) {
                    System.out.println("Book available");
                    foundBook = true;
                    DBManager.addLoan(b.getId(), memberID, LocalDate.now(), LocalDate.now().plusDays(7));
                    b.setAvailable(false);
                    DBManager.updateBook(b);
                }else
                    System.out.println("Book is currently not available, please come again");

            }

        }if (!foundBook){
            System.out.println("Book not found"); }

    }

    public static int getRandId(){
        StringBuilder numberStringB = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 4; i++){
            numberStringB.append(rnd.nextInt(9));
        }
        int number = Integer.parseInt(numberStringB.toString());
        return number;
    }

    public static boolean idIsValid(int id) {
        ArrayList<Member> members = DBManager.getMemberArrayList();
        for (Member m : members) {
            if (id == m.getId()) {
                return false;
            }
        }
        return true;
    }

    public static void registerNewMember(Long personalNumber){
        Member m = regApplicant(personalNumber);
        if (checkRegistration(m)){
            System.out.println("success");
        }
    }

   public static boolean isBanned(Long personalNumber){
       ArrayList<Long> members = DBManager.getBannedMembers();

       for (Long l : members) {
           if (personalNumber == l) {
               return true;
           }
       }
       return false;
   }
}


