import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class LibraryManagerTest {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Librarian> librarians = new ArrayList<>();
    private ArrayList<Suspension> suspensions = new ArrayList<>();
    private ArrayList<String[]> loans = new ArrayList<>();

    private static Logger logger = LogManager.getLogger(LibraryManagerTest.class.getName());

    LibraryManagerTest() {
        books.add(new Book(1,1010101,"Game of Thrones", true));
        books.add(new Book(2,2020202,"Game of Phonies", false));
        books.add(new Book(3,3030303,"Game of Bros", true));
        members.add(new Member(1, "Flipize", 24242424L, "Student"));
        members.add(new Member(2, "Gurr", 25252525L, "Teacher"));
        members.add(new Member(1, "Beggan", 26262626L, "PhD Student"));
        librarians.add(new Librarian(1234, "Gunilla Andersson"));
        librarians.add(new Librarian(5678, "Alfred Persson"));
        suspensions.add(new Suspension(3,1, LocalDate.parse ("1330-12-21"), LocalDate.parse("2019-05-30")));
        loans.add(new String[]{"2", "2", "2019-05-26", "2019-06-03"});
    }

    @Test
    void isBookAvailable() throws SQLException{
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Book> bookAL = books;
        when(db.getBookArrayList()).thenReturn(bookAL);
        assertTrue(lm.isBookAvailable(3030303));

        verify(db).getBookArrayList();
    }

    @Test
    void returnBook() throws SQLException {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Book> booksAL = books;
        ArrayList<String[]> loansAL = loans;
        ArrayList<Member> membersAl = members;
        when(db.getLoanArrayList()).thenReturn(loansAL);
        when(db.getBookArrayList()).thenReturn(booksAL);
        when(db.getMemberArrayList()).thenReturn(membersAl);

        doNothing().when(db).deleteLoan(2,2);

        doAnswer((i) -> {
            booksAL.get(1).setAvailable(true);
            return null;
        }).when(db).updateBook(booksAL.get(1));



        lm.returnBook(2,2);

        assertTrue(booksAL.get(1).isAvailable());

    }

    @Test
    void idIsValid() throws SQLException {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Member> mem = new ArrayList<>();
        mem.add(new Member(7, "lillan lill", 9004090101L, "Student"));
        when(db.getMemberArrayList()).thenReturn(mem);
        assertTrue(lm.idIsValid(8));
        verify(db).getMemberArrayList();
    }

    @Test
    void isBanned() {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Long> banned = new ArrayList<>();
        banned.add(9003135218L);
        when(db.getBannedMembers()).thenReturn(banned);
        assertTrue(lm.isBanned(9003135218L));
        verify(db).getBannedMembers();
    }

   // @Test
   /* void ban() {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        Member test = new Member(10, "Henk", 9202020202, "Student");
        lm.ban(test);
        doNothing().when(db).addOldMember().thenReturn(test);
        assertTrue(lm.isBanned(9003135218L));
        verify(db).getBannedMembers();

        //        dbM.addOldMember(m, true);
        //        dbM.deleteMember(m.getId());
        //        dbM.deleteSuspension(m.getId());
    }*/

    @Test
    void getMemberById() throws SQLException {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Member> member = members;
        when(db.getMemberArrayList()).thenReturn(member);
        assertEquals(members.get(2), lm.getMemberById(1));
        verify(db).getMemberArrayList();
    }

    @Test
    void getMemberByPN() throws SQLException{
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Member> membersAL = members;
        when(db.getMemberArrayList()).thenReturn(membersAL);
        assertEquals(1 ,lm.getMemberByPN(24242424L).getId());

    }

    @Test
    void isSuspensionIn() throws SQLException {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Suspension> suspendedMembers = suspensions;
        when(db.getSuspensionsArrayList()).thenReturn(suspendedMembers);
        assertTrue(lm.isSuspensionIn(3));
        verify(db).getSuspensionsArrayList();
    }

    @Test
    void getBookById() throws SQLException{
            DBManager db = mock(DBManager.class);
            LibraryManager lm = new LibraryManager(db);
            ArrayList<Book> booksAL = books;
            when(db.getBookArrayList()).thenReturn(booksAL);
            assertEquals(booksAL.get(0) ,lm.getBookById(1));

    }

    @Test
    void returnedInTime() {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        String[] loan = new String[]{"1","2","2019-05-09", "2019-05-10"};

        assertFalse(lm.returnedInTime(loan));

        String[] loan2 = new String[]{"1","2","2019-05-09", "2019-06-10"};

        assertTrue(lm.returnedInTime(loan2));

    }
}