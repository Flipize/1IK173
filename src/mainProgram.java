import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class mainProgram {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Scanner textInput = new Scanner(System.in);
        int choice = 0;
        boolean validChoice = false;

        System.out.println("Log in");
        int id = 0;

        int testID = 1234;
        do {
            System.out.println("User ID: ");
            try {
                id = Integer.parseInt(input.nextLine());
                if (id != testID) {
                    System.out.println("ID is not registered.");
                }
            }catch (NumberFormatException e) {
                System.out.println("ID not valid. Enter an ID consisting of 4 digits.");
            }
        }while (id != testID);

        System.out.println("You are logged in as NAMN PÅ BIBLIOTEKARIE");
        System.out.println("Welcome to your library manager! Please enter a number below: ");

        do {
            System.out.println("----------- MENU ------------");
            System.out.println("\t1 - Register member");
            System.out.println("\t2 - Add book");
            System.out.println("\t3 - Lend book");
            System.out.println("\t4 - Return book");
            System.out.println("\t5 - Remove book");
            System.out.println("\t6 - Suspend member");
            System.out.println("\t7 - Ban member");
            System.out.println("\t8 - Remove member");
            System.out.println("\t0 - Exit");
            System.out.println("-----------------------------");

            while (!validChoice) {
                try {
                    choice = Integer.parseInt(input.nextLine());
                    if (choice <= 9) {
                        validChoice = true;
                    }
                    else {
                        System.out.println("The number has to be in the range of 0-9.");
                        System.out.println("Enter number: ");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Enter a digit (0-9): ");
                }
            }

            switch (choice) {
                case 1:
                    System.out.println("Register Member");
                    String name;
                    long personalNum = 0L;
                    String type;

                    System.out.println("Enter personal number: ");

                    while (personalNum == 0L) {
                        try {
                            personalNum = Long.parseLong(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Not a valid number. Try again.");
                        }
                    }
                    if (!libraryManager.checkIfExistingMember(personalNum)){
                        System.out.println("Registration has been cancelled.");
                    }

                    else {
                        Member newMember = new Member();
                        int rndId = libraryManager.getRandId();
                        while (!libraryManager.idIsValid(rndId)) {
                            rndId = libraryManager.getRandId();
                        }
                        System.out.println("Enter name: ");
                        name = textInput.nextLine();
                        System.out.println("Enter type: ");
                        type = getAnswer();
                        libraryManager.addMember(rndId, name, personalNum, type);
                        System.out.println("Member (Name: " + name + ", ID number: " + rndId + ") was successfully added.");
                    }
                    break;
                case 2:
                    System.out.println("Add book");
                    System.out.println("Please enter book ID: ");
                    int ID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter book ISBN: ");
                    int ISBN = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter book title: ");
                    String Title = textInput.nextLine();

                    if (libraryManager.addBook(ID, ISBN, Title, true)) {
                        System.out.println("Book successfully added.");
                    }
                    else {
                        System.out.println("Book could not be added. Choose a unique ID.");
                    }
                    break;
                case 3:
                    System.out.println("Add Member");
                    System.out.println("Please enter your member ID: ");
                    int memID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter the ISBN of your requested book: ");
                    int borrowISBN = Integer.parseInt(input.nextLine());
                    libraryManager.lendItem(memID, borrowISBN);
                    break;
                case 4:
                    System.out.println("Lend Book");
                    System.out.println("Please enter book ID: ");
                    int book_ID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter your member ID: ");
                    int memb_ID = Integer.parseInt(input.nextLine());
                    libraryManager.returnBook(book_ID, memb_ID);
                    break;
                case 5:
                    System.out.println("Remove Book");
                    System.out.println("Enter the book ID to remove it: ");
                    int bookID = Integer.parseInt(input.nextLine());
                    DBManager.deleteBook(bookID);
                    System.out.println("Book successfully removed.");
                    break;
                case 6:
                    System.out.println("Suspend Member");
                    System.out.println("Enter memberId to suspend: ");
                    int suspendMemberId = Integer.parseInt(input.nextLine());
                    DBManager.addSuspension(suspendMemberId);
                    System.out.println("Member suspended for 15 days.");
                    break;
                case 7:
                    System.out.println("Ban Member");
                    System.out.println("Enter member ID to ban: ");
                    int banMember = Integer.parseInt(input.nextLine());
                    libraryManager.ban(libraryManager.getMemberById(banMember));
                    System.out.println("Member successfully banned.");
                case 8:
                    System.out.println("Remove Member");
                    System.out.println("Enter the members ID to remove the member: ");
                    int usedMemberID = Integer.parseInt(input.nextLine());
                    DBManager.deleteMember(usedMemberID);
                    System.out.println("Member successfully removed.");
                    break;
                case 9:
                default:
                    break;
                //  System.out.println("Choice must be a value between 1 and 9.");

            }
        }
        while (choice != 0) ;
        System.out.println("Thanks for using library manager.");


    }

    private static String getAnswer() {
        Scanner input = new Scanner(System.in);
        int answer;
        System.out.println("1. Student\n2. Masterstudent\n3. PhD student\n4. Teacher");
        boolean validChoice = false;

        while (!validChoice) {
        try {
            answer = Integer.parseInt(input.nextLine());
            if (answer == 1) {
                return "Student";
            } else if (answer == 2) {
                return "Masterstudent";
            } else if (answer == 3) {
                return "PhD Student";
            } else if (answer == 4) {
                return "Teacher";
            }
        } catch (NumberFormatException ne) {
            System.out.println("Type a digit (1-4).");
        }
        }
        return "Invalid";
    }
}
