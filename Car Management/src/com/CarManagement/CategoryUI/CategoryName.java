package com.CarManagement.CategoryUI;

import com.CarManagement.DBO.Category;
import javafx.beans.property.SimpleStringProperty;

public class CategoryName {

    private String categoryName;
    private int cateId;

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public CategoryName(String categoryName, int cateId) {
        this.categoryName = categoryName;
        this.cateId =cateId;

    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    @Override
    public String toString(){
        return categoryName;
    }
}
