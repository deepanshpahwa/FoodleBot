package org.telegram;

import org.telegram.updateshandlers.UserInformation;

public class Utils {
    //TODO Stushti

    public static final String MESSAGE_PREFIX = "Please choose from the following food stalls by replying with the index number.";
    public static final String MESSAGE_LIST_OF_STALLS_IN_CANTEEN_1 = MESSAGE_PREFIX + "\n" +
            "1. Vegetarian Stall\n" +
            "2. Indian Food Stall\n"+
            "3. Sushi stall";
    public static final String MESSAGE_LIST_OF_STALLS_IN_CANTEEN_2 = MESSAGE_PREFIX + "\n" +
            "1. Vegetarian Stall\n" +
            "2. Indian Food Stall";
    public static final String MESSAGE_LIST_OF_STALLS_IN_CANTEEN_3 = MESSAGE_PREFIX + "\n" +
            "1. Vegetarian Stall\n" +
            "2. Indian Food Stall";
    public static final String MESSAGE_LIST_OF_STALLS_IN_CANTEEN_4 = MESSAGE_PREFIX + "\n" +
            "1. Vegetarian Stall\n" +
            "2. Indian Food Stall";
    public static final String MESSAGE_LIST_OF_STALLS_IN_CANTEEN_5 = MESSAGE_PREFIX + "\n" +
            "1. Vegetarian Stall\n" +
            "2. Indian Food Stall";

    public static final String OUTGOING_MESSAGE_ON_START = "Hello, how may I help you? \n" +
            "Enter 1, for placing food order \n" +
            "Enter 2, to inquire food stall timings";


    public static final String MESSAGE_LIST_OF_CANTEENS =  "Please choose from the following Canteens by replying with the index number.\n"
            +"1. Canteen 1\n"
            +"2. Canteen 2\n"
            +"3. Canteen 3\n"
            +"4. Canteen 4\n"
            +"5. Canteen 5\n";

    private static String[] arrayOfCanteens = {"Canteen 1", "Canteen 2", "Canteen 3", "Canteen 4", "Canteen 5"};

    private static String FINAL_MESSAGE= "Your order has been generated";

    private static String[] CANTEEN1_INDIAN_STALL= {"1. Roti","2. Naan", "3. Dal"};
    private static String[] CANTEEN1_CHINESE_STALL= {"1. schezwan paneer", "2. Chicken chili", "3. Noodles"};
    private static String[] CANTEEN2_SEAFOOD_STALL= {"1. food item 1", "2. food item 2", "3. food item 3"};
    private static String[] CANTEEN2_MALAYSIAN_STALL= {"1. food item 1", "2. food item 2", "3. food item 3"};

    private static String[][] canteen1FoodStallsArray = {
            {"Indian stall",compileMenuToString(CANTEEN1_INDIAN_STALL)},
            {"Chinese stall", compileMenuToString(CANTEEN1_CHINESE_STALL)}
    };


    private static String[][] canteen2FoodStallsArray = {
            {"Seafood stall",compileMenuToString(CANTEEN2_SEAFOOD_STALL)},
            {"Malaysian stall",compileMenuToString(CANTEEN2_MALAYSIAN_STALL)}
    };

    private static String compileMenuToString(String[] menu) {
        String ret = "";

        for (int i = 0; i < menu.length; i++) {
            ret = ret + menu[i];
        }

        return ret;
    }


    public static String getCanteenFromIndexNumber(String indexnumber) {
        try {
            return arrayOfCanteens[Integer.parseInt(indexnumber)];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
        //TODO Deepansh Catch Error
    }

    public static String getFoodStallSelectionMessage(String canteen) {

        try {
            if (canteen.equals(arrayOfCanteens[1])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_1;

            } else if (canteen.equals(arrayOfCanteens[2])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_2;

            } else if (canteen.equals(arrayOfCanteens[3])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_3;

            } else if (canteen.equals(arrayOfCanteens[4])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_4;

            } else if (canteen.equals(arrayOfCanteens[5])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_5;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ERROR";
    }

    public static String getStallNameFromIndexNumber(String stallNumber, String canteen) {

        switch (canteen) {
            case "canteen 1":
                return canteen1FoodStallsArray[Integer.parseInt(stallNumber)][0];
            case "canteen 2":
                return canteen2FoodStallsArray[Integer.parseInt(stallNumber)][0];


            //TODO add more cases
            default:
                return ""; //Also send error to user and ask same question again//TODO
        }

    }

    public static String getMenuForStall(UserInformation userInformation) {
        if (userInformation.getCanteen().equals("Canteen 1")){
            for (int i = 0; i < canteen1FoodStallsArray.length; i++) {
                System.out.println("::128");

                if (userInformation.getStallName().equals(canteen1FoodStallsArray[i][0])){
                    System.out.println("::131");
                    return canteen1FoodStallsArray[i][1];

                }

                System.out.println("::131");

            }
        }

        if (userInformation.getCanteen().equals("Canteen 2")){
            for (int i = 0; i < canteen2FoodStallsArray.length; i++) {
                if (userInformation.getStallName().equals(canteen2FoodStallsArray[i][0])){
                    return canteen2FoodStallsArray[i][1];
                }
            }
        }


        return null;//TODO
    }

    public static String getFoodItemFromIndexNumber(String foodItem, UserInformation currentUserInformation) {

        return foodItem;
    }

    public static void generateFoodOrder(UserInformation currentUserInformation) {
        Order order = new Order();
        order.setCanteen(currentUserInformation.getCanteen());
        order.setStall(currentUserInformation.getStallName());
        order.setFoodItem(currentUserInformation.getFoodItem());

        addOrderToListOfOrders(order);


    }

    private static void addOrderToListOfOrders(Order order) {

    }

    public static String getFinalMessage() {
        return FINAL_MESSAGE;
    }
}
