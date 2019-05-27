import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class LibraryManager {

    private static Logger libManLogger = LogManager.getLogger(LibraryManager.class.getName());
    DBManager dbM = null;

    // skapar ett objekt av libman och skickar in ett objekt av databashanteraren
    public LibraryManager (DBManager db) {
        dbM = db;
    }


    // kollar ISBN och avgör om en bok finns tillgänglig eller ej
    public boolean isBookAvailable(int isbn) {
        ArrayList<Book> books;
        try {
            books = dbM.getBookArrayList();
            libManLogger.info("Someone connected to the database");
        }catch (SQLException e) {
            System.out.println("Something went wrong with database connection");
            libManLogger.error("SQLException in librarymanager.isBookAvailable ");
            return false;
        }
        for (Book b : books) {
            if (b.getIsbn() == isbn) {
                if (b.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    // returnerar en bok och tar bort medlemmens lån. Kollar lånet mot utgångstid och ropar på suspendMember om tiden överskridits
    public Book returnBook(int bookID, int memberID) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<String[]> loans = new ArrayList<>();
        try {
            loans = dbM.getLoanArrayList();
            books = dbM.getBookArrayList();
        } catch (SQLException e) {
            System.out.println("Something went wrong with database connection.");
            libManLogger.error("SQLException in librarymanager.returnBook ");
        }
        boolean bookReturned = false;
        Book book;

        /*for (String[] st : loans) {
            if (parseInt(st[1]) == memberID && (parseInt(st[0]) == bookID)) {
                for (Book b : books) {
                    if (b.getId() == bookID) {
                        book = b;
                        b.setAvailable(true);
                        dbM.updateBook(b);
                        bookReturned = true;
                        dbM.deleteLoan(bookID, memberID);
                    }
                }
            }
        }*/
        String[] loan = getLoanById(memberID, bookID);
        if (loan != null) {
            book = getBookById(bookID);
            book.setAvailable(true);
            dbM.updateBook(book);
            dbM.deleteLoan(bookID, memberID);
            if (!returnedInTime(loan)) {
                 suspendMember(getMemberById(memberID).getId());
            }
            return book;
        }
        return null;
    }

    public  void deleteMemberLibrary(int id) {

        ArrayList<Member> members = new ArrayList<>();
        try {
            members = dbM.getMemberArrayList();
        }catch (SQLException e) {
            System.out.println("Something wrong with database connection. " + e.getMessage());
        }

        for (Member m : members) {
            if (m.getId() == id) {
                dbM.deleteMember(id);
                System.out.println("Member is deleted");
            } else
                System.out.println("No member found");
        }

    }

    // kollar medlemmens id och tillgänglighet på bok,
    public  void lendItem(int memberID, int isbn) {

        ArrayList<Book> books;
        ArrayList<Member> members;

        try {
            members = dbM.getMemberArrayList();
            books = new DBManager().getBookArrayList();
        } catch (SQLException e) {
            System.out.println("Something went wrong with database connection.");
            return;
        }

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
        }
        else {
            System.out.println("Member not found.");
        }


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
            if (canMemberLend == 0) {
                System.out.println("You cannot borrow any more books. The loan count is currently at maximum " + dbM.loanCount(memberID) + " books");

            } else if (canMemberLend == 1) {
                System.out.println("Borrowing books allowed. Loan count is currently: " + dbM.loanCount(memberID) + " books.");

                boolean foundBook = false;
                for (Book b : books) {
                    if (isbn == b.getIsbn()) {
                        foundBook = true;
                        askedBook.add(b);

                    }
                }

                // skapar lån och uppdaterar status på bok
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

    // skapar ett slumpmässigt fyrsiffrigt ID som inte tidigare existerar
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

    // validerar en medlems ID
    public  boolean idIsValid(int id) {
        ArrayList<Member> members = new ArrayList<>();
        try {
            members = dbM.getMemberArrayList();
        }catch (SQLException e) {
            System.out.println("Something wrong with database connection. " + e.getMessage());
            return false;
        }

        for (Member m : members) {
            if (id == m.getId()) {
                return false;
            }
        }
        return true;
    }


    // kollar om medlem är bannad
    public  boolean isBanned(Long personalNumber){
        ArrayList<Long> bannedMembers = dbM.getBannedMembers();
        for (Long pn : bannedMembers) {
            if (personalNumber.equals(pn)) {
                return true;
            }
        }
        return false;
   }
    // bannar medlem genom att lägga till medlem i gamlamedlemstabell, ta bort medlemmens suspension och till sist ta bort medlemmen
    public  boolean ban(Member m){
        try {

            dbM.addOldMember(m, true);
            dbM.deleteSuspension(m.getId());
            dbM.deleteMember(m.getId());
        }catch (Exception e) {
            System.out.println("Something went wrong. Member did not get banned.");
            return false;
        }
        return true;
    }

   // letar upp ett medlemsobjekt via ID och anropar en databasmetod
    public  Member getMemberById(int id) {
        ArrayList<Member> members = new ArrayList<>();
        try {
            members = dbM.getMemberArrayList();
        }catch (SQLException e) {
            System.out.println("Something wrong with database connection. " + e.getMessage());
        }
        Member newMember = new Member();
        for(Member m : members) {
            if (m.getId() == id) {
                newMember = m;
            }
        }
        return newMember;
   }

   // skapar en ny suspension om medlemmen inte har en, adderar suspension och antal dagar om medlemmen redan har
    // till sist anropar den banmetoden om medlemmen redan har två suspensions
    public  Suspension suspendMember (int memberID) {
        ArrayList<Suspension> suspensionList = new ArrayList<>();
        ArrayList<Member> memberList = new ArrayList<>();
        try {
            suspensionList = dbM.getSuspensionsArrayList();
            memberList = dbM.getMemberArrayList();
        } catch (SQLException ex ) {
            System.out.println("Something went wrong.");
        }
        boolean found = false;
        Suspension suspendedMember = new Suspension();

        for (Suspension s: suspensionList) {
            if (s.getMemberID() == memberID) {
                found = true;
                suspendedMember = s;
            }}
            // skapar suspension om medlemmen inte tidigare haft
                if (!found) {
                    dbM.addSuspension(memberID);
                    ArrayList<Suspension> suspList = dbM.getSuspensionsArrayList();
                    for (Suspension s: suspList) {
                        if (s.getMemberID() == memberID) {
                            return s;
                        }
                    }
                }
                // om medlemmen har en suspension får den en till och 15 dagars påslag
                else if (suspendedMember.getMemberID() == memberID && suspendedMember.getSuspensions() == 1) {
                    int nmrOfsusp = suspendedMember.getSuspensions();
                    nmrOfsusp++;
                    suspendedMember.setSuspensions(nmrOfsusp);
                    LocalDate endDate = suspendedMember.getEndDate();
                    suspendedMember.setEndDate(endDate.plusDays(15));
                    dbM.updateSuspension(suspendedMember, memberID);
                    return suspendedMember;
                }
                // om medlemmen redan har två suspensions blir den bannad
             else if (suspendedMember.getMemberID() == memberID && suspendedMember.getSuspensions() >= 2) {
                ban(getMemberById(memberID));
                suspendedMember.setSuspensions(3);
                return suspendedMember;
            } return null;
        }

        // kollar om en medlem finns i databasen
    public  boolean isMemberIn(int id){

        ArrayList<Member> members = new ArrayList<>();
        try {
            members = dbM.getMemberArrayList();
        }catch (SQLException e) {
            System.out.println("Something wrong with database connection. " + e.getMessage());
        }

        boolean found = false;

        for (Member m : members){
            if (m.getId() == id) {
                found = true;
            }
        }
        return found;
    }

    // kollar om en medlem finns via personnummer
    public  Member getMemberByPN(long personalNum) {

        ArrayList<Member> members = new ArrayList<>();
        try {
            members = dbM.getMemberArrayList();
        }catch (SQLException e) {
            System.out.println("Something wrong with database connection. " + e.getMessage());
        }

        Member newMember;
        for(Member m : members) {
            if (m.getPersonalNumber() == personalNum) {
                newMember = m;
                return newMember;
            }
        }
        return null;
    }

    // kollar om medlemmen har en suspension
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

    // lägger till en medlem via databasmetod
    public  Member addMember(int id, String name, long personalNumber, String membershipType) {

        try {
            dbM.addMember(new Member(id, name, personalNumber, membershipType));
            ArrayList<Member> memlist = dbM.getMemberArrayList();
            for (Member m : memlist) {
                if (id == m.getId()) {
                    return m;
                }
            }
        } catch (SQLException ex){
            System.out.println("Something went wrong.");
        } return null;}

        // kollar om medlemmen tidigare varit medlem och om den isåfall varit bannad
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

    // lägger till en bok via databasmetod
    public  boolean addBook(int id, int isbn, String title, boolean available) {
        try {
            dbM.addBook(new Book(id, isbn, title, available));
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    // hämtar alla bibliotekarier via databasen
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

    // validerar librarians via inlogg
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

    // kollar så att en bok faktiskt får/har ett unikt ID
    public boolean isUniqueBookID(int id) {
        ArrayList<Book> books;
        try {
            books = dbM.getBookArrayList();
        } catch (SQLException e) {
            System.out.println("Something wrong with Database connection");
            return false;
        }
        for (Book b : books) {
            if (b.getId() == id) {
                return false;
            }
        }
        return true;
    }

    // hämtar alla böcker via databasmetod
    public ArrayList<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            books = dbM.getBookArrayList();
        }catch (SQLException e){
            System.out.println("Something wrong with database connection. " + e.getMessage());
            return null;
        }
        return books;
    }

    // hämtar alla medlemmar via databasmetod
    public ArrayList<Member> getMembers() {
        ArrayList<Member> members = new ArrayList<>();
        try {
            members = dbM.getMemberArrayList();
            libManLogger.info("Someone connected to the database");
        }catch (SQLException e){
            System.out.println("Something wrong with database connection. " + e.getMessage());
            return null;
        }
        return members;
    }

    // tittar vilka boklån en viss medlem har
    public String[] getLoanById(int memberID, int bookID) {
        ArrayList<String[]> loans;

        loans = dbM.getLoanArrayList();
        for (String[] loan : loans) {
            if (Integer.parseInt(loan[0]) == bookID && Integer.parseInt(loan[1]) == memberID) {
                return loan;
            }
        }
        return null;
    }

    // hämtar en specifik bok via ID
    public Book getBookById(int bookId) {
        ArrayList<Book> books = getBooks();
        if (books != null) {

            for (Book b : books) {
                if (b.getId() == bookId) {
                    return b;
                }
            }
        }
        return null;
    }
    // kollar om en bok returnerats i tid
    public boolean returnedInTime(String[] loan) {
        LocalDate todaysDate = LocalDate.now();

        LocalDate endDate = LocalDate.parse(loan[3]);
        if (todaysDate.isAfter(endDate)) {
            return false;
        }
        return true;
    }

}
