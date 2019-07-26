package com.example.orbital2019catch.personal.feedback;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class BrandFeedback {

    String brand;
    List<String> categories;
    List<String> idUsersWhoCompleted;
    HashMap<String, List<String>> usersWhoCompleted;

    public BrandFeedback(){}

    public BrandFeedback(String brand, List<String> categories, List<String> idUsersWhoCompleted, HashMap<String, List<String>> usersWhoCompleted) {
        this.brand = brand;
        this.categories = categories;
        this.idUsersWhoCompleted = idUsersWhoCompleted;
        this.usersWhoCompleted = usersWhoCompleted;
    }

    public String getBrand() {
        return brand;
    }

    public List<String> getCategories() { return categories; }

    public List<String> getIdUsersWhoCompleted() { return idUsersWhoCompleted; }

    public HashMap<String, List<String>> getUsersWhoCompleted() { return this.usersWhoCompleted; }
}
