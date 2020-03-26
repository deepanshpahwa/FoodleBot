package org.telegram.updateshandlers;

import org.telegram.BotConfig;
import org.telegram.Chat;
import org.telegram.Utils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author
 * @version 1.0
 * @brief Handler for updates to FoodleNTU Bot
 * @date 24 of June of 2015
 */
public class FoodleBot extends TelegramLongPollingBot {
    private static final String LOGTAG = "FOODLE_BOT_NTU_HANDLER";

    private static final String CANTEEN_NUMBER = "canteen number";
    private static final String STALL_NUMBER = "stall number" ;
    private static final String INITIAL_SELECTION = "initial selection";
    private static final String FOOD_ITEM = "food item";
    //    private static final String FOOD_ITEM_SELECTION_MESSAGE = "Please seelct food item";
    private static final String ORDER_PLACED = "order placed";

    private String INFO_REQUESTED = "";

    private static ArrayList<Chat> chatList;
    private HashMap<String, UserInformation> chatIdHash;

    public FoodleBot() {
        super();
        createListOfChats();
    }

    private void createListOfChats() {
//        chatList = new ArrayList<Chat>();
        chatIdHash = new HashMap<>();
    }

    @Override
    public String getBotToken() {
        return BotConfig.WEATHER_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BotConfig.WEATHER_USER;
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
//        if (!Utils.validate(message)){
//            sendMessageToUser(message,Utils.ERROR_MESSAGE);
//        }
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
//        } else if ((!Utils.validate(message))){
//            sendMessageToUser(message,Utils.ERROR_MESSAGE);
//            return;
        } else if (Utils.validate(message) && chatIdHash.containsKey(String.valueOf(message.getChatId()))) {

            UserInformation currentUserInformation = chatIdHash.get(message.getChatId().toString());
            switch (currentUserInformation.getInfoRequested()) {

                case INITIAL_SELECTION:
                    currentUserInformation.setInitialSelection(message.getText());
                    if (currentUserInformation.getInitialSelection().equals("1")) {
                        canteenSelectionMessage(message, Utils.MESSAGE_LIST_OF_CANTEENS+Utils.compileArrayToString(Utils.arrayOfCanteens), currentUserInformation);
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
                    Utils.generateFoodOrder(currentUserInformation);
                    finalMessageToUser(message, currentUserInformation);
                    break;
                case ORDER_PLACED:
                    sendMessageToUser(message,"Your order has already been placed.");
            }
        }else{
            sendMessageToUser(message,Utils.ERROR_MESSAGE);
        }

    }

    private void finalMessageToUser(Message message, UserInformation currentUserInformation) {
        currentUserInformation.setInfoRequested(ORDER_PLACED);
        sendMessageToUser(message,Utils.getFinalMessage());
    }

    private void foodItemSelectMessage(Message incomingMessage, String foodItemSelectionMessage, UserInformation currentUserInformation) {


        sendMessageToUser(incomingMessage,foodItemSelectionMessage + Utils.getMenuForStall(currentUserInformation));
        currentUserInformation.setInfoRequested(FOOD_ITEM);//TODO
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

//    public static void logger(String s) {
//        System.out.println("::"+s);
//    }
}
