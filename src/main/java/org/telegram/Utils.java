package org.telegram;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.updateshandlers.UserInformation;

import java.util.ArrayList;

public class Utils {

    public static final String ORDER_PICKED_UP_MESSAGE = "You are picking up order number: ";
    public static final String ORDER_PICKED_UP_MESSAGE_TO_ORDER_PLACER = "Your order has been picked up by ";
    public static ArrayList<Order> listOfOrder = new ArrayList<>();
    public static int initialInt=0;
    public static int initialOrderNumber = 72000;


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




//    public static final String MESSAGE_LIST_OF_CANTEENS =  "Please choose from the following Canteens by replying with the index number.\n"
//            +"1. Canteen 1\n"
//            +"2. Canteen 2\n"
//            +"3. Canteen 3\n"
//            +"4. Canteen 4\n"
//            +"5. Canteen 5\n";

    public static final String MESSAGE_LIST_OF_CANTEENS =  "Please choose from the following Canteens by replying with the index number.\n";
    public static final String FOOD_STALL_SELECTION_MESSAGE = "Please choose from the following foos stalls";
    public static final String FOOD_ITEM_SELECTION_MESSAGE = "Please select food item";

    public static final String ERROR_MESSAGE = "Invalid input.\nPlease enter only numeric values!";

    public static final String START_MESSAGE = "Hello, how may I help you? \nEnter 1, for placing food order \nEnter 2, to inquire food stall timings";
    public static final String STOP_MESSAGE = "Your Order has been cancelled";
    public static final String HELP_MESSAGE = "This bot will help you order from various canteens from around NTU. Enter /start to begin.";
    public static final String MENU_MESSAGE = "Enter /start to start ordering or continue ordering if you were int he middle of it. We don't support /help. ";

    public static String[] arrayOfCanteens = {"Canteen 1", "Canteen 2", "Canteen 3", "Canteen 4", "Canteen 5"};

    private static String FINAL_MESSAGE= "Your order has been generated";

    private static String[] CANTEEN1_INDIAN_STALL= {"1. Roti","2. Naan", "3. Dal"};
    private static String[] CANTEEN1_CHINESE_STALL= {"1. schezwan paneer", "2. Chicken chili", "3. Noodles"};
    private static String[] CANTEEN2_SEAFOOD_STALL= {"1. food item 1", "2. food item 2", "3. food item 3"};
    private static String[] CANTEEN2_MALAYSIAN_STALL= {"1. food item 1", "2. food item 2", "3. food item 3"};

    private static String[][] canteen1FoodStallsArray = {
            {"Indian stall", compileArrayToString(CANTEEN1_INDIAN_STALL)},
            {"Chinese stall", compileArrayToString(CANTEEN1_CHINESE_STALL)}
    };


    private static String[][] canteen2FoodStallsArray = {
            {"Seafood stall", compileArrayToString(CANTEEN2_SEAFOOD_STALL)},
            {"Malaysian stall", compileArrayToString(CANTEEN2_MALAYSIAN_STALL)}
    };

    public static String compileArrayToString(String[] menu) {
        String ret = "\n";
        for (int i = 0; i < menu.length; i++) {
            ret = ret + menu[i]+"\n";
        }
        return ret;
    }


    public static String getCanteenFromIndexNumber(String indexnumber) {
        try {
            return arrayOfCanteens[Integer.parseInt(indexnumber)-1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "An error has occurred.";
    }

    public static String getFoodStallSelectionMessage(String canteen) {

        try {
            if (canteen.equals(arrayOfCanteens[0])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_1;

            } else if (canteen.equals(arrayOfCanteens[1])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_1;

            } else if (canteen.equals(arrayOfCanteens[2])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_2;

            } else if (canteen.equals(arrayOfCanteens[3])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_3;

            } else if (canteen.equals(arrayOfCanteens[4])) {

                return MESSAGE_LIST_OF_STALLS_IN_CANTEEN_4;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ERROR";
    }

    public static String getStallNameFromIndexNumber(String stallNumber, String canteen) {

        switch (canteen) {
            case "Canteen 1":
                return canteen1FoodStallsArray[Integer.parseInt(stallNumber)-1][0];
            case "Canteen 2":
                return canteen2FoodStallsArray[Integer.parseInt(stallNumber)-1][0];


            //TODO add more cases
            default:
                return ""; //Also send error to user and ask same question again//TODO
        }

    }

    public static String getMenuForStall(UserInformation userInformation) {

        String currentCanteen = userInformation.getCanteen();
        String currentStall = userInformation.getStallName();

        if (currentCanteen.equals("Canteen 1")){

            for (int i = 0; i < canteen1FoodStallsArray.length; i++) {

                if (currentStall.equals(canteen1FoodStallsArray[i][0])){
                    return canteen1FoodStallsArray[i][1];

                }


            }
        }

        if (currentCanteen.equals("Canteen 2")){
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

    public static void generateFoodOrder(Message message, UserInformation currentUserInformation) {
        Order order = new Order();
        order.setOrderNumber();
        order.setCanteen(currentUserInformation.getCanteen());
        order.setStall(currentUserInformation.getStallName());
        order.setFoodItem(currentUserInformation.getFoodItem());
        order.setOrderPlacerChatId(message.getChatId());
        order.setOrderPlacerUsername(message.getFrom().getUserName());

        addOrderToListOfOrders(order);


    }

    private static void addOrderToListOfOrders(Order order) {
        listOfOrder.add(order);
    }

    public static boolean searchListOfOrders(String number){
        for (Order aListOfOrder : listOfOrder) {
            if (aListOfOrder.getOrderNumber().equals(number)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Order> getListOfOrder() {
        return listOfOrder;
    }

    public static String getFinalMessage() {
        return FINAL_MESSAGE;
    }

    public static boolean validate(Message message) {
        String regex = "\\d+";
        return message.getText().matches(regex);
    }

    public static boolean validateOrderNumber(String str) {

        return str.length() == 5;
    }

    public static Order getOrderFromOrderNumber(String orderNumber) {

       for (Order order:listOfOrder){
           if (order.getOrderNumber().equals(orderNumber)){
               return order;
           }
       }
        return null;
    }

    public static String generateUniqueID() {
        initialInt++;
        return String.valueOf(initialOrderNumber +initialInt);
    }

    public static void deleteOrderFromList(Order order) {
        listOfOrder.remove(order);
    }
}
