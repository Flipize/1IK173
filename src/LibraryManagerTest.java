import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryManagerTest {
    DBManager dbMock = mock(DBManager.class);
    ArrayList<Book> bookAL = new ArrayList<>();

    @Test
    void isBookAvailable() {
        when(dbMock.getBookArrayList()).thenReturn(bookAL);
        assertEquals(true, LibraryManager.isBookAvailable(1234));
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