import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class libraryManager {

    public static void main(String[] args) {

        returnBook(1,1);
        //lendItem(2, 100005);
        ban(getMemberById(6));
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
        boolean bookReturned = false;
        Book book = new Book();

        for (String[] st : loanArray) {
            if (parseInt(st[1]) == memberID && (parseInt(st[0]) == bookID)) {
                for (Book b : bookArray) {
                    if (b.getId() == bookID) {
                        book = b;
                        b.setAvailable(true);
                        DBManager.updateBook(b);
                        bookReturned = true;
                        DBManager.deleteLoan(bookID, memberID);
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

    public static void lendItem(int memberID, int isbn) {

        ArrayList<Member> members = DBManager.getMemberArrayList();
        ArrayList<Book> books = DBManager.getBookArrayList();
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
            boolean canMemberLend = true;

            for (Member m : members) {
                if (m.getMembershipType().equals("Student")) {
                    if (DBManager.loanCount(memberID) >= 3) {
                        canMemberLend = false;
                        break;
                    }
                } else if (m.getMembershipType().equals("Masterstudent")) {
                    if (DBManager.loanCount(memberID) >= 5) {
                        canMemberLend = false;
                        break;
                    }
                } else if (m.getMembershipType().equals("PhD")) {
                    if (DBManager.loanCount(memberID) >= 7) {
                        canMemberLend = false;
                        break;
                    }
                } else if (m.getMembershipType().equals("Teacher")) {
                    if (DBManager.loanCount(memberID) >= 10) {
                        canMemberLend = false;
                        break;
                    }
                } else {
                    System.out.println("Member does not have/has wrong membership type.");
                }
            }
            if (!canMemberLend) {
                System.out.println("You cannot borrow any more books. The loan count is currently at maximum " + DBManager.loanCount(memberID) + " books");

            } else {
                System.out.println("Borrowing books allowed. Loan count is currently: " + DBManager.loanCount(memberID) + " books.");

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
                                    DBManager.addLoan(book.getId(), memberID, LocalDate.now(), LocalDate.now().plusDays(7));
                                    book.setAvailable(false);
                                    DBManager.updateBook(book);
                                }
                                break;
                            } else
                                System.out.println("Book is currently not available, please come again");
                        }
                    } else {
                        System.out.println("Book not found");
                    }
                }
            }
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

    public static void ban(Member m){
        DBManager.addOldMember(m, true);
        DBManager.deleteMember(m.getId());
   }

    public static Member getMemberById(int id) {
        ArrayList<Member> members = DBManager.getMemberArrayList();
        Member newMember = new Member();
        for(Member m : members) {
            if (m.getId() == id) {
                newMember = m;
            }
        }
        return newMember;
   }

    public static void suspendMember (int memberID) {
        ArrayList<Suspension> suspensionList = DBManager.getSuspensionsArrayList();
        ArrayList<Member> memberList = DBManager.getMemberArrayList();
        boolean found = false;
        Suspension eS = new Suspension();

        for (Suspension s: suspensionList) {
            if (s.getMemberID() == memberID) {
                found = true;
                eS = s;
            }}
                if (!found) {
                    DBManager.addSuspension(memberID);
                }
                else if (eS.getMemberID() == memberID && eS.getSuspensions() == 1) {
                    int nmrOfsusp = eS.getSuspensions();
                    nmrOfsusp++;
                    eS.setSuspensions(nmrOfsusp);
                    LocalDate endDate = eS.getEndDate();
                    eS.setEndDate(endDate.plusDays(15));
                    DBManager.updateSuspension(eS, memberID);
                }
             else if (eS.getMemberID() == memberID && eS.getSuspensions() >= 2) {
                ban(getMemberById(memberID));
            }
        }

    public static boolean isMemberIn(int id){
        ArrayList<Member> members = DBManager.getMemberArrayList();
        boolean found = false;

        for (Member m : members){
            if (m.getId() == id) {
                found = true;
            }
        }
        return found;
    }

    public static boolean isSuspensionIn(int id){
        ArrayList<Suspension> susp = DBManager.getSuspensionsArrayList();
        boolean found = false;

        for (Suspension s : susp){
            if (s.getMemberID() == id) {
                found = true;
            }
        }
        return found;
    }

    public static void addMember(int id, String name, long personalNumber, String membershipType){
        DBManager.addMember(new Member(id, name, personalNumber, membershipType));
    }
}
