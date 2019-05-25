package Labb2;

import java.util.Date;

public interface IPurchaseStore {

    Purchase[] getPurchases(Date startDate, Date endDate);

    Purchase[] getPurchasesByCategory(Date startDate, Date endDate, int catId);

    Category[] getAllCategories();

}
