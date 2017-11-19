package com.CarManagement.CategoryUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CategoryTable {
    private final SimpleIntegerProperty categoryID;
    private final SimpleStringProperty categoryName;
    private final SimpleStringProperty cateDescription;


    public CategoryTable(int categoryID, String categoryName, String cateDescription) {
        this.categoryID = new SimpleIntegerProperty(categoryID);
        this.categoryName = new SimpleStringProperty(categoryName);
        this.cateDescription = new SimpleStringProperty(cateDescription);
    }

    public int getCategoryID() {
        return categoryID.get();
    }

    public SimpleIntegerProperty categoryIDProperty() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID.set(categoryID);
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    public SimpleStringProperty categoryNameProperty() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }

    public String getCateDescription() {
        return cateDescription.get();
    }

    public SimpleStringProperty cateDescriptionProperty() {
        return cateDescription;
    }

    public void setCateDescription(String cateDescription) {
        this.cateDescription.set(cateDescription);
    }

}
