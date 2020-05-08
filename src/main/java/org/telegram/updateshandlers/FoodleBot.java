package org.telegram.updateshandlers;

import org.telegram.BotConfig;
import org.telegram.Order;
import org.telegram.Utils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.HashMap;


/**
 * @Srushti Sunder
 * @version 1.0
 * @brief Handler for updates to FoodleNTU Bot
 * @date 19 January 2020
 */
public class FoodleBot extends TelegramLongPollingBot {
    private static final String LOGTAG = "FOODLE_BOT_NTU_HANDLER";

    private static final String CANTEEN_NUMBER = "canteen number";
    private static final String STALL_NUMBER = "stall number" ;
    private static final String INITIAL_SELECTION = "initial selection";
    private static final String FOOD_ITEM = "food item";
    private static final String ORDER_PLACED = "order placed";

    private static final String ORDER_TO_BE_PICKED_UP = "order to be picked up";

    private HashMap<String, UserInformation> chatIdHash;

    public FoodleBot() {
        super();
        createListOfChats();
    }

    private void createListOfChats() {
        chatIdHash = new HashMap<>();
    }

    @Override
    public String getBotToken() {
        return BotConfig.FOODLEBOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BotConfig.FOODLEBOT_USER;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message.hasText()) {
                    handleIncomingMessage(message);
                }
            }
        } catch (Exception e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    private void handleIncomingMessage(Message message) {

        String inputMessage = message.getText();

        if (inputMessage.equals("/start")){
            if (chatIdHash.containsKey(message.getChatId().toString())){
                if (chatIdHash.get(message.getChatId().toString()).getInfoRequested().equals(ORDER_PLACED)){
                    sendMessageToUser(message, "Your order has already been placed.");
                }else {
                    sendMessageToUser(message, "Your order is under processing. If you would like to start from top, please enter /stop and start again.");
                }
            }else {
                UserInformation userinformation = new UserInformation();
                chatIdHash.put(String.valueOf(message.getChatId()), userinformation);
                messageOnstart(message, userinformation);
            }

        }else if (inputMessage.equals("/stop")) {
            sendMessageToUser(message, Utils.STOP_MESSAGE);
            chatIdHash.remove(message.getChatId().toString());
            return;
        } else if (inputMessage.equals("/help")) {
            sendMessageToUser(message, Utils.HELP_MESSAGE);
            return;
        } else if (inputMessage.equals("/menu")) {
            sendMessageToUser(message, Utils.MENU_MESSAGE);
            return;
        } else if (Utils.validate(message) && chatIdHash.containsKey(String.valueOf(message.getChatId()))) {

            UserInformation currentUserInformation = chatIdHash.get(message.getChatId().toString());
            switch (currentUserInformation.getInfoRequested()) {

                case INITIAL_SELECTION:
                    currentUserInformation.setInitialSelection(message.getText());
                    if (currentUserInformation.getInitialSelection().equals("1")) {
                        canteenSelectionMessage(message, Utils.MESSAGE_LIST_OF_CANTEENS+Utils.compileArrayToString(Utils.arrayOfCanteens), currentUserInformation);
                    }else if (currentUserInformation.getInitialSelection().equals("2")){
                        displayAvailableOrders(message, currentUserInformation);
                    }else {
                        sendMessageToUser(message,Utils.ERROR_MESSAGE);
                    }
                    break;
                case CANTEEN_NUMBER:
                    //TODO check if message contains any non numeric elements
                    currentUserInformation.setCanteen(Utils.getCanteenFromIndexNumber(message.getText().trim()));
                    foodStallSelectionMessage(message, Utils.FOOD_STALL_SELECTION_MESSAGE+Utils.getFoodStallSelectionMessage(currentUserInformation.getCanteen()), currentUserInformation);
                    break;
                case STALL_NUMBER:
                    currentUserInformation.setStallName(Utils.getStallNameFromIndexNumber(message.getText().trim(),currentUserInformation.getCanteen()));
                    foodItemSelectMessage(message,Utils.FOOD_ITEM_SELECTION_MESSAGE, currentUserInformation);
                    break;
                case FOOD_ITEM:
                    currentUserInformation.setFoodItem(Utils.getFoodItemFromIndexNumber(message.getText().trim(), currentUserInformation));
                    Utils.generateFoodOrder(message,currentUserInformation);
                    finalMessageToUser(message, currentUserInformation);
                    break;
                case ORDER_PLACED:
                    sendMessageToUser(message,"Your order has already been placed.");

                case ORDER_TO_BE_PICKED_UP:
                    String orderNumber = message.getText().trim();
                    if (Utils.validateOrderNumber(orderNumber)) {
                        if (Utils.searchListOfOrders(orderNumber)) {
                            currentUserInformation.setOrderToBePickedUp(orderNumber);
                            Order order = Utils.getOrderFromOrderNumber(orderNumber);
                            Utils.deleteOrderFromList(order);
                            orderPickUpConfirmationMessage(message, Utils.ORDER_PICKED_UP_MESSAGE, currentUserInformation, orderNumber);//TODO Modify message to include order number
                            messageToOrderPlacer(order.getOrderPlacerChatId(), Utils.ORDER_PICKED_UP_MESSAGE_TO_ORDER_PLACER, message.getFrom().getUserName());
                            System.out.println(message.getFrom().getUserName());
                        } else {
                            sendMessageToUser(message, Utils.ERROR_MESSAGE);//TODO modify message
                        }
                    }


            }
        }else{
            sendMessageToUser(message,Utils.ERROR_MESSAGE);
        }

    }

    private void messageToOrderPlacer(Long chatID, String orderPickedUpMessageToOrderPlacer, String userName) {

        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(chatID).setText(orderPickedUpMessageToOrderPlacer + "@" +userName);

        try {
            execute(sendMessageRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void orderPickUpConfirmationMessage(Message message, String orderPickedUpMessage, UserInformation currentUserInformation, String orderNumber) {
        sendMessageToUser(message,orderPickedUpMessage+orderNumber);//TODO
    }

    private void displayAvailableOrders(Message message, UserInformation currentUserInformation) {
        StringBuilder ret = new StringBuilder();

        ret.append("\n");

        for (Order order:Utils.getListOfOrder()) {
            ret.append("\nOrder number: " + order.getOrderNumber());
            ret.append("\nCanteen: " + order.getCanteen());
            ret.append("\nFood Stall: " + order.getStall());
            ret.append("\nFood Item" + order.getFoodItem());
            ret.append("\nPlaced by: @"+ order.getOrderPlacerUsername());
            ret.append("\n\n");
        }
        sendMessageToUser(message,ret.toString());
        currentUserInformation.setInfoRequested(ORDER_TO_BE_PICKED_UP);

    }

    private void finalMessageToUser(Message message, UserInformation currentUserInformation) {
        currentUserInformation.setInfoRequested(ORDER_PLACED);
        sendMessageToUser(message,Utils.getFinalMessage());
    }

    private void foodItemSelectMessage(Message incomingMessage, String foodItemSelectionMessage, UserInformation currentUserInformation) {


        sendMessageToUser(incomingMessage,foodItemSelectionMessage + Utils.getMenuForStall(currentUserInformation));
        currentUserInformation.setInfoRequested(FOOD_ITEM);
    }

    private void foodStallSelectionMessage(Message incomingMessage, String outgoingMessage, UserInformation currentUserInformation) {
        sendMessageToUser(incomingMessage,outgoingMessage);
        currentUserInformation.setInfoRequested(STALL_NUMBER);
    }

    private void canteenSelectionMessage(Message incomingMessage, String outgoingMessage, UserInformation currentUserInformation) {
        sendMessageToUser(incomingMessage,outgoingMessage);
        currentUserInformation.setInfoRequested(CANTEEN_NUMBER);
    }

    private void sendMessageToUser(Message incomingMessage, String outgoingMessage) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(incomingMessage.getChatId()).setText(outgoingMessage);

        try {
            execute(sendMessageRequest); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void messageOnstart(Message message, UserInformation userinformation) {
        sendMessageToUser(message, Utils.START_MESSAGE);
        userinformation.setInfoRequested(INITIAL_SELECTION);
    }


}
