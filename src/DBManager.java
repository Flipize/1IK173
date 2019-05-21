import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private static String password = "xxx";
    private static String driver = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //private static String driver = "jdbc:mysql://localhost/Library?useSSL=false";

    public static ArrayList<Member> getMemberArrayList() {
        ArrayList<Member> memberArrayList = new ArrayList<>();

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
            ResultSet rs_member = statement.executeQuery(
                    "SELECT * FROM Member");

            while (rs_member.next()) {
                memberArrayList.add(new Member(rs_member.getInt(1), rs_member.getString(2), rs_member.getString(3)));

            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return memberArrayList;
    }

    public static ArrayList<Book> getBookArrayList() {
        ArrayList<Book> bookArrayList = new ArrayList<>();

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
            ResultSet rs_books = statement.executeQuery(
                    "SELECT * FROM book");

            while (rs_books.next()) {
                bookArrayList.add(new Book(rs_books.getInt(1), rs_books.getInt(2), rs_books.getString(3)));

            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return bookArrayList;
    }

    public static ArrayList <Book> addBook (int id, int isbn, String title) {

        ArrayList <Book> bookArrayList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Book VALUES (?, ?, ?)");
            statement.setInt(1, id);
            statement.setInt(2, isbn);
            statement.setString(3, title);

            statement.executeUpdate();
            bookArrayList.add(new Book(id, isbn, title));

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return bookArrayList;
    }


    public static ArrayList <Member> addMember (int id, String name, String type) {

        ArrayList <Member> addMemberArrayList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Member VALUES (?, ?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, type);

            statement.executeUpdate();
            addMemberArrayList.add(new Member(id, name, type));

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return addMemberArrayList;
    }

    public static void deleteBook (int id) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

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


    public static void main(String[] args) {
        ArrayList<Member> m_array = getMemberArrayList();
        for (Member m : m_array) {
            System.out.println(m.toString());
        }

        ArrayList<Book> b_array = getBookArrayList();
        for (Book b : b_array) {
            System.out.println(b.toString());
        }
    }

}
