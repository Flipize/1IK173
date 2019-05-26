import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibraryManager {

    DBManager dbM = null;

    public LibraryManager (DBManager db) {
        dbM = db;
    }

    public static void main(String[] args) {

     /*   returnBook(1,1);
        //lendItem(2, 100005);
        ban(getMemberById(6));*/
    }

    public boolean isBookAvailable(int isbn) {
        ArrayList<Book> bookArrayList = dbM.getBookArrayList();
        for (Book b : bookArrayList) {
            if (b.getIsbn() == isbn) {
                if (b.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    public  void returnBook(int bookID, int memberID) throws NullPointerException {

        ArrayList<String[]> loanArray = dbM.getLoanArrayList();
        ArrayList<Book> bookArray = dbM.getBookArrayList();
        boolean bookReturned = false;
        Book book = new Book();

        for (String[] st : loanArray) {
            if (parseInt(st[1]) == memberID && (parseInt(st[0]) == bookID)) {
                for (Book b : bookArray) {
                    if (b.getId() == bookID) {
                        book = b;
                        b.setAvailable(true);
                        dbM.updateBook(b);
                        bookReturned = true;
                        dbM.deleteLoan(bookID, memberID);
                    }
                }
            }
        }
        if (bookReturned) {
            System.out.println("Book: " + book.getTitle() + " has been returned");
        }
        else {
            System.out.println("Something went wrong. The book is not eligible for return.");
        }
        LocalDate todaysDate = LocalDate.now();
        for (String[] st : loanArray) {
            if (parseInt(st[1]) == memberID && (parseInt(st[0]) == bookID)) {
                String date = st[3];
                LocalDate endDate = LocalDate.parse(date);
                if (todaysDate.isAfter(endDate)) {
                    suspendMember(memberID);
                }
            }
        }
    }

    public  Member regApplicant(long personalNumber) {
        ArrayList<Member> members = dbM.getMemberArrayList();
        for (Member m : members) {
            if (m.getPersonalNumber() == personalNumber) {
                return m;
            }
        }
        Member newMember = new Member();
        newMember.setPersonalNumber(personalNumber);
        return newMember;
    }

  /*  public static boolean checkRegistration(int id, String name, String type) {
        Scanner reader = new Scanner(System.in);
        if (get) {
            if (isBanned(m.getPersonalNumber())) {
                System.out.println("The account has been terminated.");
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
            System.out.println("Enter name: ");
            m.setName(reader.nextLine());

            System.out.println("What membership type? ");
            m.setMembershipType(reader.nextLine());
            DBManager.addMember(m);
            System.out.println("An account for " + m.getName() + " (" + m.getId() + ") has successfully been created.");
            return true;
        }
    } */

    public  void deleteMemberLibrary(int id) {

        ArrayList<Member> tempArray = dbM.getMemberArrayList();

        for (Member m : tempArray) {
            if (m.getId() == id) {
                dbM.deleteMember(id);
                System.out.println("Member is deleted");
            } else
                System.out.println("No member found");
        }

    }

    public  void lendItem(int memberID, int isbn) {

        ArrayList<Member> members = dbM.getMemberArrayList();
        ArrayList<Book> books = new DBManager().getBookArrayList();
        ArrayList<Book> askedBook = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        boolean foundMember = false;
        Member member = new Member();


        for (Member m : members) {
            if (m.getId() == memberID) {
                foundMember = true;
                member = m;
            }
        }
        if (foundMember) {
            System.out.println("Member found: " + member.getName());


            //Kollar om medlemmen får låna fler böcker
            int canMemberLend = 1;

            if (foundMember) {
                if (member.getMembershipType().equals("Student")) {
                    if (dbM.loanCount(memberID) >= 3) {
                        canMemberLend = 0;
                    }
                } else if (member.getMembershipType().equals("Masterstudent")) {
                    if (dbM.loanCount(memberID) >= 5) {
                        canMemberLend = 0;
                    }
                } else if (member.getMembershipType().equals("PhD Student")) {
                    if (dbM.loanCount(memberID) >= 7) {
                        canMemberLend = 0;
                    }
                } else if (member.getMembershipType().equals("Teacher")) {
                    if (dbM.loanCount(memberID) >= 10) {
                        canMemberLend = 0;
                    }
                } else {
                    System.out.println("Member does not have/has wrong membership type.");
                    canMemberLend = 2;
                }
            }
            if (canMemberLend == 0) {
                System.out.println("You cannot borrow any more books. The loan count is currently at maximum " + dbM.loanCount(memberID) + " books");

            }
            else if (canMemberLend == 1) {
                System.out.println("Borrowing books allowed. Loan count is currently: " + dbM.loanCount(memberID) + " books.");

                boolean foundBook = false;
                for (Book b : books) {
                    if (isbn == b.getIsbn()) {
                        foundBook = true;
                        askedBook.add(b);
                      /*  if (b.isAvailable()) {
                            System.out.println("Book available.");
                            System.out.println("Add " + b.getTitle() + " as a loan for " + member.getName() + "? (Y/N)");
                            DBManager.addLoan(b.getId(), memberID, LocalDate.now(), LocalDate.now().plusDays(7));
                            b.setAvailable(false);
                            DBManager.updateBook(b);
                        } else
                            System.out.println("Book is currently not available, please come again");
                    }*/
                    }
                }
                    if (foundBook) {
                        for (Book book : askedBook) {
                            if (book.isAvailable()) {
                                System.out.println("Book available.");
                                System.out.println("Add: " + book.getTitle() + " as a loan for: " + member.getName() + "? (Y/N)");
                                if (input.nextLine().toUpperCase().equals("Y")) {
                                    dbM.addLoan(book.getId(), memberID, LocalDate.now(), LocalDate.now().plusDays(7));
                                    book.setAvailable(false);
                                    dbM.updateBook(book);
                                }
                                break;
                            } else
                                System.out.println("Book is currently not available, please come again");
                        }
                    } else {
                        System.out.println("Book (ISBN: " + isbn + ") was not found");
                    }
                }
            }
    }

    public  int getRandId(){
        Random rnd = new Random();
        int number;
        char c;
        do {
            StringBuilder numberStringB = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                numberStringB.append(rnd.nextInt(9));
            }
            number = Integer.parseInt(numberStringB.toString());
            c = numberStringB.charAt(0);
        } while (c == '0');
        return number;
    }

    public  boolean idIsValid(int id) {
        ArrayList<Member> members = dbM.getMemberArrayList();
        for (Member m : members) {
            if (id == m.getId()) {
                return false;
            }
        }
        return true;
    }

    /*public static void registerNewMember(Long personalNumber){
        if (checkIfExistingMember(personalNumber)) {

        }
        if (checkRegistration(m)){
            System.out.println("success");
        }
    }*/

    public  boolean isBanned(Long personalNumber){
       ArrayList<Long> bannedMembers = dbM.getBannedMembers();
       for (Long pn : bannedMembers) {
           if (personalNumber.equals(pn)) {
               return true;
           }
       }
       return false;
   }

    public  void ban(Member m){
        dbM.addOldMember(m, true);
        dbM.deleteMember(m.getId());
        dbM.deleteSuspension(m.getId());
   }

    public  Member getMemberById(int id) {
        ArrayList<Member> members = dbM.getMemberArrayList();
        Member newMember = new Member();
        for(Member m : members) {
            if (m.getId() == id) {
                newMember = m;
            }
        }
        return newMember;
   }

    public  void suspendMember (int memberID) {
        ArrayList<Suspension> suspensionList = dbM.getSuspensionsArrayList();
        ArrayList<Member> memberList = dbM.getMemberArrayList();
        boolean found = false;
        Suspension eS = new Suspension();

        for (Suspension s: suspensionList) {
            if (s.getMemberID() == memberID) {
                found = true;
                eS = s;
            }}
                if (!found) {
                    dbM.addSuspension(memberID);
                }
                else if (eS.getMemberID() == memberID && eS.getSuspensions() == 1) {
                    int nmrOfsusp = eS.getSuspensions();
                    nmrOfsusp++;
                    eS.setSuspensions(nmrOfsusp);
                    LocalDate endDate = eS.getEndDate();
                    eS.setEndDate(endDate.plusDays(15));
                    dbM.updateSuspension(eS, memberID);
                }
             else if (eS.getMemberID() == memberID && eS.getSuspensions() >= 2) {
                ban(getMemberById(memberID));
            }
        }

    public  boolean isMemberIn(int id){
        ArrayList<Member> members = dbM.getMemberArrayList();
        boolean found = false;

        for (Member m : members){
            if (m.getId() == id) {
                found = true;
            }
        }
        return found;
    }

    public  Member getMemberByPN(long personalNum) {
        ArrayList<Member> members = dbM.getMemberArrayList();
        Member newMember = new Member();
        for(Member m : members) {
            if (m.getPersonalNumber() == personalNum) {
                newMember = m;
                return newMember;
            }
        }
        return null;
    }

    public  boolean isSuspensionIn(int id){
        ArrayList<Suspension> susp = dbM.getSuspensionsArrayList();
        boolean found = false;

        for (Suspension s : susp){
            if (s.getMemberID() == id) {
                found = true;
            }
        }
        return found;
    }

    public  void addMember(int id, String name, long personalNumber, String membershipType){
        dbM.addMember(new Member(id, name, personalNumber, membershipType));
    }

    public  boolean checkIfExistingMember(long personalNum){
        Member newMember = getMemberByPN(personalNum);
        if (isBanned(personalNum)) {
            System.out.println("The account has been terminated due to misconduct.");
            return false;
        }
        else if ((newMember != null) && (!(isBanned(personalNum)))) {
            System.out.println(newMember.getName() + " (" + newMember.getPersonalNumber() + ") is already registered.");
            return false;
        }
        else {
            return true;
        }
    }

    public  boolean addBook(int id, int isbn, String title, boolean available) {
        try {
            dbM.addBook(new Book(id, isbn, title, available));
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public  Librarian getLibrarian(int id) {
        ArrayList<Librarian> librarians = new ArrayList<>();
        try {
            librarians = dbM.getLibrarianArrayList();
        }catch (SQLException e){
            System.out.println("SQL error!");
            return null;
        }
        for (Librarian l : librarians) {
            if (id == l.getId()) {
                return l;
            }
        }
        return null;
    }

    public  boolean validLibrarian(int id) {
        ArrayList<Librarian> librarians = new ArrayList<>();
        try {
            librarians = dbM.getLibrarianArrayList();
        }catch (SQLException e) {
            System.out.println("SQL error!" + e.getMessage());
            return false;
    }
    for (Librarian l : librarians) {
        if (id == l.getId()) {
            return true;
        }
    }
    return false;
    }

}
