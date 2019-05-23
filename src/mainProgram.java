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
            System.out.println("5 - Return a book");
            System.out.println("6 - Suspend a member");
            System.out.println("7 - Remove a member");
            System.out.println("8 - Remove a book");
            System.out.println("9 - Exit library manager. :(");

            choice = input.nextInt();

            switch (choice)
            {
                case 1:

                    break;
                case 2:
                    System.out.println("Please enter book ID: ");
                    int ID = input.nextInt();
                    System.out.println("Please enter book ISBN: ");
                    int ISBN = input.nextInt();
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
                    // do something
                case 5:
                    // do something
                    break;
                case 6:
                    // ..something else
                    break;
                case 7:
                    System.out.println("Enter the members ID to remove the member: ");
                    int memberID = input.nextInt();
                    DBManager.deleteMember(memberID);
                    System.out.println("Member successfully removed.");
                    break;
                case 8:
                    System.out.println("Enter the book ID to remove it: ");
                    int bookID = input.nextInt();
                    DBManager.deleteBook(bookID);
                    System.out.println("Book successfully removed.");
                    break;

                default:
                    break;
                  //  System.out.println("Choice must be a value between 1 and 9.");
            }
        } while (choice != 9);
            System.out.println("Thanks for using library manager.");




    }
}
