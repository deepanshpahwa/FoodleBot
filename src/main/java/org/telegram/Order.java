package org.telegram;

public class Order {

    private String canteen;
    private String stall;
    private String foodItem;
    private String orderNumber;
    private Long orderPlacerChatID;
    private String orderPlacerUSername;

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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber() {
        this.orderNumber = Utils.generateUniqueID();
    }

    public void setOrderPlacerChatId(Long orderPlacer) {
        this.orderPlacerChatID = orderPlacer;
    }

    public Long getOrderPlacerChatId() {
        return orderPlacerChatID;
    }

    public void setOrderPlacerUsername(String orderPlacerUsername) {
        this.orderPlacerUSername = orderPlacerUsername;
    }

    public String getOrderPlacerUSername() {
        return orderPlacerUSername;
    }
}
