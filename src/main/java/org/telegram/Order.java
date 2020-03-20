package org.telegram;

public class Order {

    private String canteen;
    private String stall;
    private String foodItem;

    public void setCanteen(String canteen) {
        this.canteen = canteen;
    }

    public String getCanteen() {
        return canteen;
    }

    public void setStall(String stall) {
        this.stall = stall;
    }

    public String getStall() {
        return stall;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getFoodItem() {
        return foodItem;
    }
}
