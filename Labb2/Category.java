package Labb2;

public class Category {

    public int id;
    private String description;

    public Category(int id, String description) {
        this.id = id;
        this.description = description;
    }


    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
