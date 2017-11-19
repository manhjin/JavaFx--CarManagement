package com.CarManagement.DBO;

public class Category {
    private int cateId;
    private String cateName;
    private String description;

    public Category(int cateId, String cateName, String description) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.description = description;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString(){
        return cateName;
    }
}
