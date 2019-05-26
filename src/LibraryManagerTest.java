import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryManagerTest {

    @Test
    void testOst() {
        DBManager db = mock(DBManager.class);
        LibraryManager lm = new LibraryManager(db);
        ArrayList<Book> bookAL = new ArrayList<>();
        bookAL.add(new Book(7, 7766, "Hejsan", true));
        bookAL.add(new Book(8, 7765, "Hejsan", true));
        when(db.getBookArrayList()).thenReturn(bookAL);
        assertTrue(lm.isBookAvailable(7766));
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
    }

    @Test
    void isBanned() {
    }

    @Test
    void ban() {
    }

    @Test
    void getMemberById() {
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