package org.telegram.updateshandlers;

public class UserInformation {
    private String canteen;
    private String stallNumber;
    private String initialSelection;

    public void setCanteen(String canteen) {
        this.canteen = canteen;
    }

    public String getCanteen() {
        return canteen;
    }

    public void setStallNumber(String stallNumber) {
        this.stallNumber = stallNumber;
    }

    public String getStallNumber() {
        return stallNumber;
    }

    public void setInitialSelection(String initialSelection) {
        this.initialSelection = initialSelection;
    }

    public String getInitialSelection() {
        return initialSelection;
    }
}
