import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DBManager {

    private static String password = "Jim1337!";
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

        //deleteBook(2222);

        //addMember(new Member(6969, "Kim larksson", "Student", false));

        //addBook(new Book(2222, 233223, "Sopboken", true));

        //deleteMember(6969);

        //addLoan(1, 66, Date.valueOf("2018-11-23"), Date.valueOf("2018-12-23"));
        //deleteLoan(5,2);

        //Book newBook = new Book(2, 2626, "A Song of Ice and Fire", false);
        //updateBook(newBook);

        //Member newMember = new Member(69, "Ibra", "VIP", false);
        //updateMember(newMember);
        /*ArrayList<Long> banned = getBannedMembers();
        for (Long l : banned){
            System.out.println(l);
        }*/
       /* Member newMember = new Member(1211, "Testare", 1232233212L, "Student");

        //addLoan(3,4, LocalDate.now(), LocalDate.now().plusDays(7));

        addOldMember(newMember, true);
*/
    }

    public ArrayList<Member> getMemberArrayList() {
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

        } catch (SQLException ex) {
        }

        return memberArrayList;
    }

    public ArrayList<Book> getBookArrayList() {
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

    public  void addBook (Book b) {

        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

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

    public  void deleteLoan(int bookID, int memberID){
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            PreparedStatement statement = conn.prepareStatement("DELETE from loan where MemberID = " + memberID +
                    " AND BookID = " + bookID);
            statement.executeUpdate();


        } catch (SQLException ex) {
        }

    }

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

    public  void addSuspension (int memberId){


        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {

            //är member suspended

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


