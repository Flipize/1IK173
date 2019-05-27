import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DBManager {


    private static String password = "eldorado5";
    private static String driver = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //private static String driver = "jdbc:mysql://localhost/Library?useSSL=false";

    // Hämtar medlemmar ifrån databasen och returnerar de i en ArrayList
    public ArrayList<Member> getMemberArrayList() throws SQLException{
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
                memberArrayList.add(new Member(rs_member.getInt(1), rs_member.getString(2), rs_member.getLong(3), rs_member.getString(4)));
            }
        }

        return memberArrayList;
    }

    // hämtar böcker ifrån databasen och returnerar de i en ArrayList
    public ArrayList<Book> getBookArrayList() throws SQLException {
        ArrayList<Book> bookArrayList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

            Statement statement = conn.createStatement();
            ResultSet rs_books = statement.executeQuery(
                    "SELECT * FROM book");

            while (rs_books.next()) {
                bookArrayList.add(new Book(rs_books.getInt(1), rs_books.getInt(2), rs_books.getString(3), rs_books.getBoolean(4)));

            }

        }

        return bookArrayList;
    }

    // hämtar suspensions ifrån databasen och returnerar de i en ArrayList
    public  ArrayList<Suspension> getSuspensionsArrayList() {
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
                suspensionsList.add(new Suspension(rs_suspension.getInt(1), rs_suspension.getInt(2), rs_suspension.getDate(3).toLocalDate(), rs_suspension.getDate(4).toLocalDate()));
            }

        } catch (SQLException ex) {
        }

        return suspensionsList;
    }

    // hämtar lån ifrån databasen och returnerar de i en ArrayList
    public  ArrayList<String[]> getLoanArrayList() {
        ArrayList<String[]> loanArrayList = new ArrayList<>();
        String[] temp = new String[4];

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        }
        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

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

    // lägger till en bok i databasen
    public  void addBook (Book b) throws SQLException{

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Book VALUES (?, ?, ?, ?)");
            statement.setInt(1, b.getId());
            statement.setInt(2, b.getIsbn());
            statement.setString(3, b.getTitle());
            statement.setBoolean(4, b.isAvailable());

            statement.executeUpdate();
        }
    }

    // lägger till en medlem i databasen
    public  void addMember (Member m) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Member VALUES (?, ?, ?, ?)");
            statement.setInt(1, m.getId());
            statement.setString(2, m.getName());
            statement.setLong(3, m.getPersonalNumber());
            statement.setString(4, m.getMembershipType());

            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }

    // tar bort en bok ifrån databasen
    public  void deleteBook (int id) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("DELETE from Book where BookID = (?) ");
            statement.setInt(1, id);
            statement.executeUpdate();


        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }

    // tar bort en medlem ifrån databasen
    public  void deleteMember (int id) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("DELETE from Member where MemberID = (?) ");
            statement.setInt(1, id);
            statement.executeUpdate();


        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

    }

    // lägger till ett lån i databasen
    public  void addLoan(int bookID, int memberID, LocalDate startDate, LocalDate endDate){

        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

            PreparedStatement statement = conn.prepareStatement("INSERT INTO loan VALUES (?, ?, ?, ?)");
            statement.setInt(1, bookID);
            statement.setInt(2, memberID);
            statement.setDate(3, Date.valueOf(startDate));
            statement.setDate(4, Date.valueOf(endDate));

            statement.executeUpdate();
            return;

        } catch (SQLException ex) {

        }
    }

    // tar bort ett lån ifrån databasen när en bok returnerats
    public  void deleteLoan(int bookID, int memberID){
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("DELETE from loan where MemberID = " + memberID +
                    " AND BookID = " + bookID);
            statement.executeUpdate();


        } catch (SQLException ex) {
        }

    }

    // uppdaterar tillgänglighet på en bok efter att den returnerats
    public  void updateBook(Book b){
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("UPDATE book set ISBN = (?), Title = (?), isAvailable = (?) WHERE BookID = (?)");

            statement.setInt(1, b.getIsbn());
            statement.setString(2, b.getTitle());
            statement.setBoolean(3, b.isAvailable());
            statement.setInt(4, b.getId());

            statement.executeUpdate();


        } catch (SQLException ex) {
        }
    }

    // uppdaterar en medlem ifall något behöver ändras (används ej i nuläget)
    public  void updateMember(Member m){
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("UPDATE member set Name = (?), Type = (?), PersonalNumber = (?) WHERE MemberID = (?)");

            statement.setString(1, m.getName());
            statement.setString(2, m.getMembershipType());
            statement.setLong(3, m.getPersonalNumber());
            statement.setInt(4, m.getId());

            statement.executeUpdate();


        } catch (SQLException ex) {
        }
    }

    // lägger på en suspension på en existerande medlem
    public  void addSuspension (int memberId){


        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            LocalDate date = LocalDate.now();

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Suspension VALUES (?, ?, ?, ?)");
            statement.setInt(1, memberId);
            statement.setInt(2, 1);
            statement.setDate(3, Date.valueOf(date));
            statement.setDate(4, Date.valueOf(date.plusDays(15)));

            statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }

    // uppdaterar suspension på en medlem, antingen för att de fått ytterligare suspension eller blivit bannade
    public  void updateSuspension (Suspension s, int memberId) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("UPDATE Suspension set Suspensions = (?), StartDate = (?), EndDate = (?) WHERE MemberID = (?)");

            statement.setInt(1, s.getSuspensions());
            statement.setDate(2, Date.valueOf(s.getStartDate()));
            statement.setDate(3, Date.valueOf(s.getEndDate()));
            statement.setInt(4, s.getMemberID());

            statement.executeUpdate();

        } catch (SQLException ex) {
        }
    }

    // räknar en medlems lånantal och returnerar det som en int
    public  int loanCount(int memberID){
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

    // hämtar bannade medlemmar och returnerar de som en ArrayList
    public  ArrayList<Long> getBannedMembers(){
        ArrayList<Long> bannedMembers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

            Statement statement = conn.createStatement();
            ResultSet rs_banned = statement.executeQuery(
                    "SELECT PersonalNumber FROM OldMembers WHERE Banned = true;");

            while (rs_banned.next()) {
                bannedMembers.add(rs_banned.getLong(1));
            }

        } catch (SQLException ex) {
        }

        return bannedMembers;
    }

    // lägger till medlem i tabellen för tidigare medlemmar, antingen pga avslutat medlemsskap eller pga av ban
    public  void addOldMember(Member m, boolean banned) {
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("INSERT INTO oldmembers VALUES (?, ?, ?)");
            statement.setLong(1, m.getPersonalNumber());
            statement.setString(2, m.getName());
            statement.setBoolean(3, banned);

            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }

    // tar bort en medlems suspension pga att de blivit bannade
    public  void deleteSuspension (int memberID) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

            PreparedStatement statement = conn.prepareStatement("DELETE from Suspension where MemberID = (?) ");
            statement.setInt(1, memberID);
            statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }

    // hämtar bibliotikarier och returnerar i en ArrayList
    public  ArrayList<Librarian> getLibrarianArrayList() throws SQLException {
        ArrayList<Librarian> registeredLibrarians = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(
                driver, "root", password)) {

            Statement statement = conn.createStatement();
            ResultSet rs_librarian = statement.executeQuery(
                    "SELECT * FROM Librarian");

            while (rs_librarian.next()) {
                registeredLibrarians.add(new Librarian(rs_librarian.getInt(1), rs_librarian.getString(2)));
            }
        }

        return registeredLibrarians;
    }
}


