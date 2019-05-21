import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private static String password = "eldorado5";
    private static String driver = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //private static String driver = "jdbc:mysql://localhost/Library?useSSL=false";

    private static ArrayList<Member> getMemberArrayList() {
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
                memberArrayList.add(new Member(rs_member.getInt(1), rs_member.getString(2), rs_member.getString(4)));

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

    public static ArrayList<Book> getLoanArrayList() {
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
            ResultSet rs_loans = statement.executeQuery(
                    "SELECT * FROM book");

            while (rs_loans.next()) {
                bookArrayList.add(new Book(rs_loans.getInt(1), rs_loans.getInt(2), rs_loans.getString(3)));

            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return bookArrayList;
    }

}
