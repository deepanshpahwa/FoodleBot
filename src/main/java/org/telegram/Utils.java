package org.telegram;

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
}
