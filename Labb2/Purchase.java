package Labb2;

import java.util.Date;

public class Purchase {

    public int ID;
    public Date Date;
    public float Amount;
    public String Comment;
    public int CategoryId;

    public Purchase(int id, Date date, float amount, String comment, int categoryId) {
        this.ID = id;
        this.Date = date;
        this.Amount = amount;
        this.Comment = comment;
        this.CategoryId = categoryId;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }
}
