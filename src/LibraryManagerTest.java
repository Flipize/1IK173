import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LibraryManagerTest {
    DBManager dbMock;
    ArrayList<Book> bookAL;

    public LibraryManagerTest(DBManager dbManager) {
        dbMock = dbManager;
    }

    @BeforeAll
    static void setUp() {
        ArrayList<Book> bookAL = new ArrayList<>();
        bookAL.add(new Book(1, 1234, "Book", true));
    }

    @Test
    void isBookAvailable() {
        when(dbMock.getBookArrayList()).thenReturn(bookAL);
        assertEquals(true, isBookAvailable(1234));
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