import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryManagerTest {
    public ArrayList<Book> books = new ArrayList<>();
    public ArrayList<Member> members = new ArrayList<>();
    public ArrayList<Librarian> librarians = new ArrayList<>();
    public ArrayList<Suspension> suspensions = new ArrayList<>();
    public ArrayList<String[]> loans = new ArrayList<>();


    public LibraryManagerTest() {
        books.add(new Book(1,1010101,"Game of Thrones", true));
        books.add(new Book(2,2020202,"Game of Phonies", false));
        books.add(new Book(3,3030303,"Game of Bros", true));
        members.add(new Member(1, "Flipize", 12891212L, "Student"));
        members.add(new Member(2, "Gurr", 12891212L, "Techer"));
        members.add(new Member(1, "Beggan", 12891212L, "PhD Student"));
        librarians.add(new Librarian(1234, "Gunilla Andersson"));
        librarians.add(new Librarian(5678, "Alfred Persson"));
        suspensions.add(new Suspension(3,3, LocalDate.parse ("1330-12-21"), LocalDate.parse("2019-05-30")));
        loans.add(new String[]{"2", "2", "20190526", "20190603"});

    }

    @Test
   void testOst() {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Book> bookAL = books;
        when(db.getBookArrayList()).thenReturn(bookAL);
        assertTrue(lm.isBookAvailable(1010101));
    }

    @Test
    void isBookAvailable() {
    }

    @Test
    void returnBook() {
    }

    @Test
    void regApplicant() {
    }

    @Test
    void deleteMemberLibrary() {
    }

    @Test
    void lendItem() {
    }

    @Test
    void getRandId() {
    }

    @Test
    void idIsValid() {
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
    void getMemberById() {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Member> member = members;
        when(db.getMemberArrayList()).thenReturn(member);
       // assertEquals(new Member(1, "aa", 111, "Student"), lm.getMemberById(1));
    }

    @Test
    void suspendMember() {
    }

    @Test
    void isMemberIn() {
    }

    @Test
    void getMemberByPN() {
    }

    @Test
    void isSuspensionIn() {
    }

    @Test
    void addMember() {
    }

    @Test
    void checkIfExistingMember() {
    }

    @Test
    void addBook() {
    }

    @Test
    void getLibrarian() {
    }

    @Test
    void validLibrarian() {
    }
}