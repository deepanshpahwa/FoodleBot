package org.telegram.updateshandlers;

public class UserInformation {
    private String canteen;
    private String stallName;
    private String foodItem;
    private String initialSelection;
    private String infoRequested;
    private String orderToBePickedUp;

    public void setCanteen(String canteen) {
        this.canteen = canteen;
    }

    public String getCanteen() {
        return canteen;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public String getStallName() {
        return stallName;
    }

    public void setInitialSelection(String initialSelection) {
        this.initialSelection = initialSelection;
    }

    public String getInitialSelection() {
        return initialSelection;
    }

    public void setInfoRequested(String infoRequested) {
        this.infoRequested = infoRequested;
    }

    public String getInfoRequested() {
        return infoRequested;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getFoodItem() {
        return foodItem;
    }


    public void setOrderToBePickedUp(String orderToBePickedUp) {
        this.orderToBePickedUp = orderToBePickedUp;
    }

    public String getOrderToBePickedUp() {
        return orderToBePickedUp;
    }
}
