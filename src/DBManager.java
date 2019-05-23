import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DBManager {

    private static String password = "Hallonsaft1";
    //private static String driver = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String driver = "jdbc:mysql://localhost/Library?useSSL=false";


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

        deleteBook(2222);

        //addMember(new Member(6969, "Kim larksson", "Student", false));

        //addBook(new Book(2222, 233223, "Sopboken", true));

        //deleteMember(6969);

        //addLoan(1, 66, Date.valueOf("2018-11-23"), Date.valueOf("2018-12-23"));
        //deleteLoan(1,1);

        //Book newBook = new Book(69, 2626, "A Song of Ice and Fire", false);
        //updateBook(newBook);

        //Member newMember = new Member(69, "Ibra", "VIP", false);
        //updateMember(newMember);

        System.out.println(loanCount(5));



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
                memberArrayList.add(new Member(rs_member.getInt(1), rs_member.getString(2), rs_member.getInt(3), rs_member.getString(4)));

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

    public static ArrayList<Suspension> getSuspensionsArrayList() {
        ArrayList<Suspension> suspensionsList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        }
        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

            Statement statement = conn.createStatement();
            ResultSet rs_suspension = statement.executeQuery(
                    "SELECT * FROM Suspension");

            while (rs_suspension.next()) {
                suspensionsList.add(new Suspension(rs_suspension.getInt(1), rs_suspension.getBoolean(2), rs_suspension.getDate(3), rs_suspension.getDate(4)));
            }

        } catch (SQLException ex) {
        }

        return suspensionsList;
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

    public static void addBook (Book b) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Book VALUES (?, ?, ?, ?)");
            statement.setInt(1, b.getId());
            statement.setInt(2, b.getIsbn());
            statement.setString(3, b.getTitle());
            statement.setBoolean(4, b.isAvailable());

            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }

    public static void addMember (Member m) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Member VALUES (?, ?, ?, ?)");
            statement.setInt(1, m.getId());
            statement.setString(2, m.getName());
            statement.setInt(3, m.getPersonalNumber());
            statement.setString(4, m.getMembershipType());


            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }

    public static void deleteBook (int id) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");


            PreparedStatement statement = conn.prepareStatement("DELETE from Book where BookID = (?) ");
            statement.setInt(1, id);
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
            statement.setInt(1, id);
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
            statement.setBoolean(3, b.isAvailable());
            statement.setInt(4, b.getId());

            statement.executeUpdate();


        } catch (SQLException ex) {
        }
    }

    public static void updateMember(Member m){
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("UPDATE member set Name = (?), Type = (?), PersonalNumber = (?) WHERE MemberID = (?)");

            statement.setString(1, m.getName());
            statement.setString(2, m.getMembershipType());
            statement.setInt(3, m.getPersonalNumber());
            statement.setInt(4, m.getId());

            statement.executeUpdate();


        } catch (SQLException ex) {
        }
    }

  /*  public static void suspendMember (int memberID) {
        ArrayList<Member> memberList = getMemberArrayList();

        for (Member m: memberList) {
           if(m.getId() == memberID) {
               m.setSuspended(true);
               m.suspensions++;
               DBManager.updateMember(m);
           }
        }
    }*/

    public static int loanCount(int memberID){
        int count = 0;
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery ("SELECT count(MemberID) from loan where MemberID = " + memberID);
            while (result.next()){
                count = result.getInt(1);
            }

        } catch (SQLException ex) {
        }
        return count;
    }
}