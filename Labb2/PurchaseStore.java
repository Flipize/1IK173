package Labb2;

import java.util.Date;

public class PurchaseStore implements IPurchaseStore{



    @Override
    public Purchase[] getPurchases(Date startDate, Date endDate) {
        return new Purchase[0];
    }

    @Override
    public Purchase[] getPurchasesByCategory(Date startDate, Date endDate, int catId) {
        return new Purchase[0];
    }

    @Override
    public Category[] getAllCategories() {
        return new Category[0];
    }
}
