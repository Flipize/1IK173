import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DBManager {

    private static String password = "eldorado5";
    private static String driver = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //private static String driver = "jdbc:mysql://localhost/Library?useSSL=false";


    public static void main(String[] args) {
        /*ArrayList<Member> m_array = getMemberArrayList();
        for (Member m : m_array) {
            System.out.println(m.toString());
        }*/

        /*ArrayList<Book> b_array = getBookArrayList();
        for (Book b : b_array) {
            System.out.println(b.toString());
        }*/

        /*ArrayList<String[]> l_array = getLoanArrayList();
        for (String[] s : l_array){
            System.out.println(s[0] + " " + s[1] + " " + s[2] + " " + s[3]);
        }*/

        //deleteBook(10);

        //addLoan(1, 66, Date.valueOf("2018-11-23"), Date.valueOf("2018-12-23"));
        //deleteLoan(1,1);

        //Book newBook = new Book(69, 2626, "A Song of Ice and Fire", false);
        //updateBook(newBook);

        //Member newMember = new Member(69, "Ibra", "VIP", false);
        //updateMember(newMember);

    }

    public static ArrayList<Member> getMemberArrayList() {
        ArrayList<Member> memberArrayList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        }
        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

            Statement statement = conn.createStatement();
            ResultSet rs_member = statement.executeQuery(
                    "SELECT * FROM Member");

            while (rs_member.next()) {
                memberArrayList.add(new Member(rs_member.getInt(1), rs_member.getString(2), rs_member.getString(3), rs_member.getBoolean(4)));

            }

        } catch (SQLException ex) {
        }

        return memberArrayList;
    }

    public static ArrayList<Book> getBookArrayList() {
        ArrayList<Book> bookArrayList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        }
        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

            Statement statement = conn.createStatement();
            ResultSet rs_books = statement.executeQuery(
                    "SELECT * FROM book");

            while (rs_books.next()) {
                bookArrayList.add(new Book(rs_books.getInt(1), rs_books.getInt(2), rs_books.getString(3), rs_books.getBoolean(4)));

            }

        } catch (SQLException ex) {
        }

        return bookArrayList;
    }

    public static ArrayList<String[]> getLoanArrayList() {
        ArrayList<String[]> loanArrayList = new ArrayList<>();
        String[] temp = new String[4];

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver did not load");
        }
        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {
            System.out.println("Connected");

            Statement statement = conn.createStatement();
            ResultSet rs_loans = statement.executeQuery(
                    "SELECT * FROM loan");

            while (rs_loans.next()) {
                temp[0] = rs_loans.getString(1);
                temp[1] = rs_loans.getString(2);
                temp[2] = rs_loans.getString(3);
                temp[3] = rs_loans.getString(4);

                loanArrayList.add(temp);
                temp = new String[4];

            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return loanArrayList;
    }

    public static ArrayList <Book> addBook (int id, int isbn, String title, boolean status) {

        ArrayList <Book> bookArrayList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Book VALUES (?, ?, ?, ?)");
            statement.setInt(1, id);
            statement.setInt(2, isbn);
            statement.setString(3, title);
            statement.setBoolean(4, status);

            statement.executeUpdate();
            bookArrayList.add(new Book(id, isbn, title, status));

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return bookArrayList;
    }

    public static ArrayList <Member> addMember (int id, String name, String type, boolean suspended) {

        ArrayList <Member> addMemberArrayList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Member VALUES (?, ?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, type);
            statement.setBoolean(4, suspended);

            statement.executeUpdate();
            addMemberArrayList.add(new Member(id, name, type, suspended));

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return addMemberArrayList;
    }

    public static void deleteBook (int id) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

            PreparedStatement statement1 = conn.prepareStatement("DELETE from Loan where BookID = (?)");
            statement1.executeUpdate();

            PreparedStatement statement = conn.prepareStatement("DELETE from Book where BookID = (?) ");

            statement.executeUpdate();


        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }

    public static void deleteMember (int id) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

            PreparedStatement statement = conn.prepareStatement("DELETE from Member where MemberID = (?) ");
            statement.executeUpdate();


        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

    }

    public static void addLoan(int bookID, int memberID, Date startDate, Date endDate){

        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

            PreparedStatement statement = conn.prepareStatement("INSERT INTO loan VALUES (?, ?, ?, ?)");
            statement.setInt(1, bookID);
            statement.setInt(2, memberID);
            statement.setDate(3, startDate);
            statement.setDate(4, endDate);

            statement.executeUpdate();
            return;

        } catch (SQLException ex) {

        }
    }

    public static void deleteLoan(int bookID, int memberID){
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("DELETE from loan where MemberID = " + memberID +
                    " AND BookID = " + bookID);
            statement.executeUpdate();


        } catch (SQLException ex) {
        }

    }

    public static void updateBook(Book b){
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("UPDATE book set ISBN = (?), Title = (?), Status = (?) WHERE BookID = (?)");

            statement.setInt(1, b.getIsbn());
            statement.setString(2, b.getTitle());
            statement.setBoolean(3, b.isStatus());
            statement.setInt(4, b.getid());

            statement.executeUpdate();


        } catch (SQLException ex) {
        }
    }

    public static void updateMember(Member m){
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("UPDATE member set Name = (?), Type = (?), Suspended = (?) WHERE MemberID = (?)");

            statement.setString(1, m.getName());
            statement.setString(2, m.getMembershipType());
            statement.setBoolean(3, m.isSuspended());
            statement.setInt(4, m.getId());

            statement.executeUpdate();


        } catch (SQLException ex) {
        }
    }
}