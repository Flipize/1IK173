import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private static String password = "Hallonsaft1";
    //private static String driver = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String driver = "jdbc:mysql://localhost/Library?useSSL=false";

    private static ArrayList<Member> getMemberArrayList() {
        ArrayList<Member> memberArrayList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver did not load");
        }
        try (Connection conn = DriverManager.getConnection(
                driver, "root" , password)) {
            System.out.println("Connected");

            //Hämta data från tabellerna "superfigur" och "ingar_i"
            Statement statement = conn.createStatement();
            ResultSet rs_member = statement.executeQuery(
                    "SELECT * FROM Member");

            //Strängar som håller värden för de olika kolumnerna
            int id;
            String name;


            while (rs_member.next()) {
                id = rs_member.getInt(1);
                name = rs_member.getString(2);
                //Skapa Superfigur-object och lägg till värden
                memberArrayList.add(new Member(id, name, 56, "PhD"));

            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return memberArrayList;
    }

    public static void main(String[] args) {
        ArrayList<Member> array = getMemberArrayList();
        for (Member m : array) {
            System.out.println(m.toString());

    }
    }
}
