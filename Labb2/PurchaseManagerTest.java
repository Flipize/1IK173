package Labb2;

/*
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Date;

    public class PurchaseManagerTest {
        PurchaseStoreStub stub = new PurchaseStoreStub();
        PurchaseManager cut = new PurchaseManager(stub);
        @Test
        void sumOfMonth(){
            stub.addPuchase(3, new Date(2019,2,4), 8f, "sumOfMonth", 4);
            stub.addPuchase(2, new Date(2019,2,4), 8f, "sumOfMonth", 2);
            stub.addPuchase(1, new Date(2019,4,2), 8f, "sumOfMonth", 1);

            assertEquals(8f, cut.sumOfMonth(2019, 4));
        }

        @Test
        void monthlyAverage(){
            float [] array = {0,6,7,7.5f,0,0,0,0,0,0,0,50}; //expected

            stub.addPuchase(4, new Date(2019,3,4), 8f, "monthlyAverage", 4);
            stub.addPuchase(4, new Date(2019,3,6), 7f, "monthlyAverage", 7);
            stub.addPuchase(3, new Date(2019,2,7), 7f, "monthlyAverage", 2);
            stub.addPuchase(2, new Date(2019,1,6), 6f,"monthlyAverage" ,1);
            stub.addPuchase(1, new Date(2019,11,25), 100f, "monthlyAverage", 12);
            stub.addPuchase(1, new Date(2019,11,27), 20f, "monthlyAverage", 12);
            stub.addPuchase(1, new Date(2019,11,26), 30f, "monthlyAverage", 12);

            assertArrayEquals(array, cut.monthlyAverage(2019));     //jämför expected med avg för 2019

        }

        @Test
        void yearlyAveragePerCategory(){
            float [] array = {53,75,50};

            stub.addCategory(1,"mat");
            stub.addCategory(2,"klader");
            stub.addCategory(3,"bars");
            stub.addPuchase(1, new Date(2019,1,19), 50f, "CategoryPYear", 2);
            stub.addPuchase(1, new Date(2019,1,24), 100f, "CategoryPYear", 2);
            stub.addPuchase(1, new Date(2019,1,26), 101f, "CategoryPYear", 1);
            stub.addPuchase(1, new Date(2019,1,20), 5f, "CategoryPYear", 1);
            stub.addPuchase(1, new Date(2019,1,19), 50f, "CategoryPYear", 3);

            assertArrayEquals(array, cut.yearlyAveragePerCategory(2019));
        }

        @Test
        void sumOfMonth_UsingMockito() {
            PurchaseStore trs = mock(PurchaseStore.class);
            PurchaseManager cut = new PurchaseManager(trs);


            when(trs.getPurchases(new Date(2019,1,1),new Date(2019,1,31)))
                    .thenReturn(new Purchase[] {
                            new Purchase(1, new Date(2019,1,1), 4.0f, "sumofMonthmock", 1),
                            new Purchase(1, new Date(2019,1,2), 4.0f, "sumofMonthmock", 1),
                            new Purchase(1, new Date(2019,1,3), 4.0f, "sumofMonthmock", 1),});

            assertEquals(cut.sumOfMonth(2019,1), 12.0f);
        }

        @Test
        void monthlyAverage_UsingMockito() {

            PurchaseStore ps = mock(PurchaseStore.class);
            PurchaseManager cut = new PurchaseManager(ps);
            Date date1 = new Date(2019,1,1);
            Date date2 = new Date(2019,11,31);
            float [] array = {0,0,0,45,0,0,0,0,90,100,110,120};

            when(ps.getPurchases(date1,date2))
                    .thenReturn(new Purchase[] {new Purchase(13, new Date(2019,11,21), 120f, "monthlyAverageMock", 12),
                            new Purchase(12, new Date(2019,10,18), 110f, "monthlyAverageMock", 12),
                            new Purchase(11, new Date(2019,9,16), 100f, "monthlyAverageMock", 12),
                            new Purchase(10, new Date(2019,8,13), 90f, "monthlyAverageMock", 12),
                            new Purchase(5, new Date(2019,3,4), 50f, "monthlyAverageMock", 4),
                            new Purchase(4, new Date(2019,3,6), 40f, "monthlyAverageMock", 7)});

            assertArrayEquals(array,cut.monthlyAverage(2019));
        }

        @Test
        void yearlyAveragePerCategory_UsingMockito() {
            float [] array = {53,75,50};

            PurchaseStore ps = mock(PurchaseStore.class);
            PurchaseManager cut = new PurchaseManager(ps);

            Date date1 = new Date(2019,0,1);
            Date date2 = new Date(2019,11,31);


            when(ps.getPurchases(date1,date2))
                    .thenReturn(new Purchase[] {new Purchase(13, new Date(2019,11,21), 120f, "CategoryPYearMock", 12),
                            new Purchase(1, new Date(2019,1,19), 50f, "CategoryPYearMock", 2),
                            new Purchase(1, new Date(2019,1,24), 100f, "CategoryPYearMock", 2),
                            new Purchase(1, new Date(2019,1,26), 101f, "CategoryPYearMock", 1),
                            new Purchase(1, new Date(2019,1,20), 5f, "CategoryPYearMock", 1),
                            new Purchase(1, new Date(2019,1,19), 50f, "CategoryPYearMock", 3)});

            when(ps.getAllCategories())
                    .thenReturn(new Category[]{
                            new Category(1,"food"),
                            new Category(2,"clothes"),
                            new Category(3,"clothes")
                    });
            assertArrayEquals(array,cut.yearlyAveragePerCategory(2019));
        }
    }



*/