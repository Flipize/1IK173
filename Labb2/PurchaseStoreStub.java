package Labb2;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseStoreStub extends PurchaseStore {

    List<Purchase> entity =null;
    List<Category> categories = null;

    public PurchaseStoreStub () {
        entity = new ArrayList<Purchase>();
        categories = new ArrayList<Category>();
    }
    public void addPuchase (int Id, Date date, float Amount, String Comment, int CategoryId){
        Purchase ett = new Purchase(Id,date,Amount,Comment,CategoryId);
        entity.add(ett);
    }
    public void addCategory(int id, String description){
        Category ce = new Category(id, description);
        categories.add(ce);
    }
    public Purchase[] getPurchases(Date StartDate, Date EndDate) {
        Purchase[] arr = new Purchase[entity.size()];
        return entity.toArray(arr);
    }
    public Category[] getAllCategories () {
        Category [] cat= new Category[categories.size()];
        return categories.toArray(cat);
    }

}
