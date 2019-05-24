import java.util.ArrayList;
import java.util.Scanner;

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
            System.out.println("5 - Suspend a member");
            System.out.println("6 - Remove a member");
            System.out.println("7 - Remove a book");
            System.out.println("0 - Exit library manager. :(");

            choice = Integer.parseInt(input.nextLine());



            switch (choice)
            {
                case 1:
                    System.out.println("Please enter member ID: ");
                    int memberID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter member name: ");
                    String memberName = textInput.nextLine();
                    System.out.println("Please enter personal number: ");
                    long personalNumber = Long.parseLong(input.nextLine());
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
                    // .. exit program
                    break;
                case 4:
                    System.out.println("Enter member ID");
                    int enterMemberID = Integer.parseInt(input.nextLine());
                    System.out.println("Enter book ID");
                    int enterBookID = Integer.parseInt(input.nextLine());
                    libraryManager.returnBook(enterBookID, enterMemberID);
                    break;
                case 5:
                    System.out.println("Enter memberId to suspend: ");
                    int suspendMemberId = Integer.parseInt(input.nextLine());
                    DBManager.addSuspension(suspendMemberId);
                    System.out.println("Member suspended for 15 days.");
                    break;
                case 6:
                    System.out.println("Enter the members ID to remove the member: ");
                    int usedMemberID = Integer.parseInt(input.nextLine());
                    DBManager.deleteMember(usedMemberID);
                    System.out.println("Member successfully removed.");
                    break;
                case 7:
                    System.out.println("Enter the book ID to remove it: ");
                    int bookID = Integer.parseInt(input.nextLine());
                    DBManager.deleteBook(bookID);
                    System.out.println("Book successfully removed.");
                    break;

                default:
                    break;
                  //  System.out.println("Choice must be a value between 1 and 9.");
            }
        } while (choice != 0);
            System.out.println("Thanks for using library manager.");




    }
}
