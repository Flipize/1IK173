import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.Integer.parseInt;

public class MainProgram {
    private static Logger logger = LogManager.getLogger(MainProgram.class.getName());

    public static void main(String[] args) {

        DBManager dbM = new DBManager();
        LibraryManager lbm = new LibraryManager(dbM);
        Scanner input = new Scanner(System.in);
        Scanner textInput = new Scanner(System.in);
        int choice = 0;
        boolean validChoice = false;

        System.out.println("Log in");
        int id = 0;

        do {
            System.out.println("User ID: ");
            try {
                id = Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e) {
                System.out.println("ID not valid. Enter an ID consisting of 4 digits.");
                logger.error("Tried to log in to the system but there was no valid ID");
            }
            if (!lbm.validLibrarian(id)) {
                System.out.println("ID is not registered");
                logger.error("The ID was not resgistered");
            }
        }while (!lbm.validLibrarian(id));

        System.out.println("You are logged in as " + lbm.getLibrarian(id).getName());
        logger.info(lbm.getLibrarian(id).getName() + " (ID: " + lbm.getLibrarian(id).getId() + ") logged in to the system");
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
            System.out.println("\t9 - Show content");
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
                    logger.error("Did not type in a number between 0 and 9");
                }
            }

            switch (choice)
            {
                case 1:
                    logger.info("Register member selected");
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
                            logger.error("Wrong input information for personal number");
                        }
                    }
                    if (!lbm.checkIfExistingMember(personalNum)){
                        System.out.println("Registration has been cancelled.");
                        logger.info("Registration was cancelled because the personal number existed");
                    }

                    else {
                        Member newMember = new Member();
                        int rndId = lbm.getRandId();
                        while (!lbm.idIsValid(rndId)) {
                            rndId = lbm.getRandId();
                        }
                        System.out.println("Enter name: ");
                        name = textInput.nextLine();
                        System.out.println("Enter type: ");
                        type = getAnswer();
                        lbm.addMember(rndId, name, personalNum, type);
                        System.out.println("Member (Name: " + name + ", ID number: " + rndId + ") was successfully added.");
                        logger.info("A new member was created. Ended" );
                    }
                    break;
                case 2:
                        logger.info("Add book selected");
                        System.out.println("Add book");
                        System.out.println("Please enter book ID: ");
                        int ID = Integer.parseInt(input.nextLine());
                        System.out.println("Please enter book ISBN: ");
                        int ISBN = Integer.parseInt(input.nextLine());
                        System.out.println("Please enter book title: ");
                        String Title = textInput.nextLine();

                    try {
                        if (lbm.addBook(ID, ISBN, Title, true)) {
                            System.out.println("Book successfully added.");
                            logger.info("Book added");
                        }
                        else {
                            System.out.println("Something went wrong. Book was not added. Make sure to input a unique ID.");
                            logger.info("Wrong input, no book was added");
                        }

                    }catch (NumberFormatException nfe){
                        System.out.println("Wrong information input");
                        logger.info("End");
                    }break;


                case 3:
                    logger.info("Lend a book selected");
                    System.out.println("Lend Book");
                    System.out.println("Please enter your member ID: ");
                    int memID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter the ISBN of your requested book: ");
                    int borrowISBN = Integer.parseInt(input.nextLine());
                    lbm.lendItem(memID, borrowISBN);
                    logger.info("End");
                    break;
                case 4:
                    logger.info("Return a book selected");
                    int book_ID = 0;
                    int memb_ID = 0;
                    Book book = new Book();
                    Member member = new Member();

                    System.out.println("Return Book");
                    do {
                        System.out.println("Please enter book ID: ");
                        try {
                            book_ID = Integer.parseInt(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("The input for ID has to be digits.");
                            logger.error("Wrong input info on BookID");
                        }
                        book = lbm.getBookById(book_ID);
                        if (book == null) {
                            System.out.println("The book does not exist.");
                            logger.info("The book that was requested did not exist ");
                        }
                    }while (book == null);

                    do {
                        try {
                            System.out.println("Please enter your member ID: ");
                            memb_ID = Integer.parseInt(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("The input for member ID has to be digits.");
                            logger.error("Wrong input information");
                        }
                        member = lbm.getMemberById(memb_ID);
                        if (member == null) {
                            System.out.println("The member is not registered.");
                            logger.error("The member was not registered");
                        }
                    } while (member == null);

                    String[] loan = lbm.getLoanById(memb_ID, book_ID);
                    if (loan == null) {
                        System.out.println("No such loan exists.");
                        logger.error("The was no such loan found in the database.");
                        break;
                    }
                    else {

                        book = lbm.returnBook(book_ID, memb_ID);
                        if (!lbm.returnedInTime(loan)) {
                            System.out.println("Book is returned too late. Suspension has been added to member.");
                            logger.info("A book was returned late");
                        }
                        if (book != null) {
                            System.out.println("Book: (" + book.getTitle() + ") was successfully returned.");
                            logger.info("Book was returned");
                        } else {
                            System.out.println("Book could not be returned.");
                        }
                        logger.info("End");
                    }
                    break;
                case 5:
                    logger.info("Remove book selected");
                    System.out.println("Remove Book");
                    System.out.println("Enter the book ID to remove it: ");
                    int bookID = Integer.parseInt(input.nextLine());
                    dbM.deleteBook(bookID);
                    System.out.println("Book successfully removed.");
                    logger.info("Book removed. End");
                    break;
                case 6:
                    logger.info("Suspend member selected");
                    System.out.println("Suspend Member");
                    System.out.println("Enter member ID to suspend: ");
                    int suspendMemberId = Integer.parseInt(input.nextLine());
                    Suspension susp = lbm.suspendMember(suspendMemberId);
                    if (susp != null && susp.getSuspensions() <= 2) {
                        System.out.println("Member with ID: " + susp.getMemberID() + " suspended to " + susp.getEndDate() + "");
                        logger.info("Suspension added");
                    } else if (susp != null && susp.getSuspensions() == 3) {
                    System.out.println("Member with ID: " + susp.memberID + " had too many suspensions and was banned.");
                    logger.info("Member got more than two suspensions and was deleted");
                     } else System.out.println("Member was not suspended.");
                    logger.info("End");
                    break;
                case 7:
                    logger.info("Ban member selected");
                    System.out.println("Ban Member");
                    System.out.println("Enter member ID to ban: ");
                    int banMember = Integer.parseInt(input.nextLine());
                    lbm.ban(lbm.getMemberById(banMember));
                    System.out.println("Member successfully banned.");
                    logger.info("Member banned. End");
                    break;
                case 8:
                    logger.info("Remove member selected");
                    System.out.println("Remove Member");
                    System.out.println("Enter the members ID to remove the member: ");
                    int usedMemberID = Integer.parseInt(input.nextLine());
                    dbM.deleteMember(usedMemberID);
                    System.out.println("Member successfully removed.");
                    logger.info("Member removed. End");
                    break;
                case 9:
                    logger.info("Show content was selected");
                    System.out.println("Show content");
                    System.out.println("\t1. Show members\n\t2. Show books\n\t3. Show loans");
                    int choiceShowContent = 0;
                    do {
                        try {
                            choiceShowContent = Integer.parseInt(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Type a digit (1-3)");
                            logger.error("User did not type in a number between 1-3");
                        }
                        switch (choiceShowContent) {
                            case 1:
                                logger.info("Option 1 selected");
                                ArrayList<Member> members = lbm.getMembers();
                                for (Member m : members) {
                                    System.out.println(m.toString());
                                    System.out.println("-----------------------------");
                                }
                                logger.info("End");
                                break;
                            case 2:
                                logger.info("Option 2 selected");
                                ArrayList<Book> books = lbm.getBooks();
                                for (Book b : books) {
                                    System.out.println(b.toString());
                                    System.out.println();
                                }
                                logger.info("End");
                                break;
                            case 3:
                                logger.info("Option 3 selected");
                                ArrayList<String[]> loans = dbM.getLoanArrayList();
                                for (String[] loan2 : loans) {
                                    System.out.println("Book ID: " + loan2[0] + " Member ID: " + loan2[1] + " Start date: " + loan2[2] + " End date: " +loan2[3]);
                                }
                                logger.info("End");
                                break;
                                default:
                                    System.out.println("Enter a number in the range of 1-3");

                        }

                    }while (choiceShowContent != 1 && choiceShowContent != 2 && choiceShowContent != 3);

                        break;
                default:
                    break;
                //  System.out.println("Choice must be a value between 1 and 9.");

            } validChoice = false;
        }
        while (choice != 0) ;
        System.out.println("Thanks for using library manager.");
        logger.info("Application ended");


    }

    private static String getAnswer() {
        logger.info("Method getAnswer called");
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
        }logger.error("Not digit between 1-4");
        }logger.info("End");
        return "Invalid";

    }
}
