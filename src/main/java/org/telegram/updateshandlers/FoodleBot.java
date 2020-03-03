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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


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

        if (chatIdHash.containsKey(String.valueOf(message.getChatId()))) {
            UserInformation currentUserInformation = chatIdHash.get(message.getChatId());


            switch (currentUserInformation.getInfoRequested()) {

                case INITIAL_SELECTION:
                    currentUserInformation.setInitialSelection(message.getText());
                    if (currentUserInformation.getInitialSelection().equals("1")) {
                        canteenSelectionMessage(message, Utils.MESSAGE_LIST_OF_CANTEENS, currentUserInformation);
                    }
                    break;
                case CANTEEN_NUMBER:
                    //TODO check if message contains any non numeric elements
                    currentUserInformation.setCanteen(Utils.getCanteenFromIndexNumber(message.getText().trim()));
                    foodStallSelectionMessage(message, currentUserInformation.getCanteen(), currentUserInformation);
                    break;
                case STALL_NUMBER:
                    currentUserInformation.setStallNumber(message.getText().trim());
                    foodItemSelectMessage(message, currentUserInformation.getStallNumber(), currentUserInformation);
                    break;


                case "/stop":
                case "/help":
                case "/menu":
            }
        }else{
            UserInformation userinformation = new UserInformation();
            chatIdHash.put(String.valueOf(message.getChatId()),userinformation);

            if (message.getText().equals("/start")) {
                messageOnstart(message, userinformation);
            }


        }

    }

    private void foodItemSelectMessage(Message message, String stallNumber, UserInformation currentUserInformation) {
        sendMessageToUser(message,"Stopping here for now ");
        currentUserInformation.setInfoRequested("TBD");//TODO
    }

    private void foodStallSelectionMessage(Message incomingMessage, String canteen, UserInformation currentUserInformation) {
        sendMessageToUser(incomingMessage,Utils.getFoodStallSelectionMessage(canteen));
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
        sendMessageToUser(message, Utils.OUTGOING_MESSAGE_ON_START);
        userinformation.setInfoRequested(INITIAL_SELECTION);
    }

}
