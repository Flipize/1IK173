import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class mainProgram {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Scanner textInput = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Welcome to your library manager! Please enter a number below: ");
            System.out.println("1 - Add a member");
            System.out.println("2 - Add a book");
            System.out.println("3 - Borrow a book");
            System.out.println("4 - Return a book");
            System.out.println("5 - Remove a book");
            System.out.println("6 - Suspend a member");
            System.out.println("7 - Ban a member");
            System.out.println("8 - Remove a member");

            System.out.println("0 - Exit library manager. :(");

            choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Please enter member ID: ");
                    int memberID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter member name: ");
                    String memberName = textInput.nextLine();
                    System.out.println("Please enter personal number: ");
                    int personalNumber = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter membership type: ");
                    String type = textInput.nextLine();
                    System.out.println("Who do you want to register?");
                    //libraryManager.registerNewMember(input.nextLine());
                    input.nextLine();

                    Member newMember = new Member(memberID, memberName, personalNumber, type);
                    DBManager.addMember(newMember);
                    System.out.println("Member successfully added.");
                    break;
                case 2:
                    System.out.println("Please enter book ID: ");
                    int ID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter book ISBN: ");
                    int ISBN = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter book title: ");
                    String Title = textInput.nextLine();

                    Book newBook = new Book(ID, ISBN, Title, true);
                    DBManager.addBook(newBook);
                    System.out.println("Book successfully added.");
                    break;
                case 3:
                    System.out.println("Please enter your member ID: ");
                    int memID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter the ISBN of your requested book: ");
                    int borrowISBN = Integer.parseInt(input.nextLine());
                    libraryManager.lendItem(memID, borrowISBN);
                    break;
                case 4:
                    System.out.println("Please enter book ID: ");
                    int book_ID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter your member ID: ");
                    int memb_ID = Integer.parseInt(input.nextLine());
                    libraryManager.returnBook(book_ID, memb_ID);
                    break;
                case 5:
                    System.out.println("Enter the book ID to remove it: ");
                    int bookID = Integer.parseInt(input.nextLine());
                    DBManager.deleteBook(bookID);
                    System.out.println("Book successfully removed.");
                    break;
                case 6:
                    System.out.println("Enter memberId to suspend: ");
                    int suspendMemberId = Integer.parseInt(input.nextLine());
                    DBManager.addSuspension(suspendMemberId);
                    System.out.println("Member suspended for 15 days.");
                    break;
                case 7:
                    System.out.println("Enter memberId to ban: ");
                    int banMember = Integer.parseInt(input.nextLine());
                    libraryManager.ban(libraryManager.getMemberById(banMember));
                    System.out.println("Member successfully banned.");
                case 8:
                    System.out.println("Enter the members ID to remove the member: ");
                    int usedMemberID = Integer.parseInt(input.nextLine());
                    DBManager.deleteMember(usedMemberID);
                    System.out.println("Member successfully removed.");
                    break;
                case 9:

                    int medlem = 5;
                    int book = 5;
                    ArrayList<String[]> loanArray = DBManager.getLoanArrayList();
                    ArrayList<Book> bookArray = DBManager.getBookArrayList();
                    LocalDate todaysDate = LocalDate.now();
                    for (String[] st : loanArray) {
                        if (parseInt(st[1]) == medlem && (parseInt(st[0]) == book)) {
                           // LocalDate endDate = LocalDate.of(Integer.parseInt(st[3].substring(0,3)), Integer.parseInt(st[3].substring(4,5)), Integer.parseInt(st[3].substring(6,7)));
                            String date = st[3];
                            System.out.println(date);
                            LocalDate endDate = LocalDate.parse(date);
                            System.out.println(endDate);
                            if (todaysDate.isAfter(endDate)) {
                                libraryManager.suspendMember(medlem);
                            }
                        }
                    }


                            default:
                                break;
                            //  System.out.println("Choice must be a value between 1 and 9.");
                        }
                    }
                    while (choice != 0) ;
                    System.out.println("Thanks for using library manager.");


            }
        }
