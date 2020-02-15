package org.telegram.updateshandlers;

import org.telegram.BotConfig;
import org.telegram.Utils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.lang.invoke.SwitchPoint;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Handler for updates to Weather Bot
 * @date 24 of June of 2015
 */
public class FoodleBot extends TelegramLongPollingBot {
    private static final String LOGTAG = "WEATHERHANDLERS";
    private static final String CANTEEN_NUMBER = "1";
    private String INFO_REQUESTED = "";

    //TODO Srushti
    private static final String CANTEEN_1 = "canteen from Hall 1";
    private static final String CANTEEN_2 = "canteen from Hall 2";
    private static final String CANTEEN_3 = "canteen from Hall 16";
    private static final String CANTEEN_4 = "canteen from Hall Crescent";
    private static final String CANTEEN_5 = "canteen from Hall 9";
    private static final String STALL_NUMBER = "soso" ;


    public FoodleBot() {
        super();
//        startAlertTimers();
    }

    @Override
    public String getBotToken() {
        return BotConfig.WEATHER_TOKEN;
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
        UserInformation userinformation = new UserInformation();

        if (INFO_REQUESTED.equals(CANTEEN_NUMBER)){
            userinformation.setCanteen(getCanteenFromIndexNumber(message.getText().trim()));
            foodStallSelectionMessage(message,userinformation.getCanteen());
        }

        switch (message.getText().trim()){
            case "/start":
                messageOnstart(message);
            case "/stop":
            case "/help":
            case "/menu":
            case "1" :
                canteenSelectionMessage(message, "select a food stall");


        }


    }

    private String getCanteenFromIndexNumber(String indexnumber) {


        switch (indexnumber){
            case "1":
                return CANTEEN_1;
            case "2":
                return CANTEEN_2;
            case "3":
                return CANTEEN_3;
            case "4":
                return CANTEEN_4;
            case "5":
                return CANTEEN_5;

        }
        return "ERROR";//TODO Deepansh Catch Error
    }

    private void foodStallSelectionMessage(Message incomingMessage, String canteen) {
        switch (canteen){
            case CANTEEN_1:
                sendMessageToUser(incomingMessage, Utils.MESSAGE_LIST_OF_STALLS_IN_CANTEEN_1);
            case CANTEEN_2:
                sendMessageToUser(incomingMessage, Utils.MESSAGE_LIST_OF_STALLS_IN_CANTEEN_2);
            case CANTEEN_3:
                sendMessageToUser(incomingMessage, Utils.MESSAGE_LIST_OF_STALLS_IN_CANTEEN_3);
            case CANTEEN_4:
                sendMessageToUser(incomingMessage, Utils.MESSAGE_LIST_OF_STALLS_IN_CANTEEN_4);
            case CANTEEN_5:
                sendMessageToUser(incomingMessage, Utils.MESSAGE_LIST_OF_STALLS_IN_CANTEEN_5);
        }
        INFO_REQUESTED = STALL_NUMBER;
    }

    private void canteenSelectionMessage(Message incomingMessage, String outgoingMessage) {
        sendMessageToUser(incomingMessage,outgoingMessage);
        INFO_REQUESTED = CANTEEN_NUMBER;
    }

    private SendMessage sendMessageToUser(Message incomingMessage, String outgoingMessage) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(incomingMessage.getChatId())
                .setText(outgoingMessage);

        try {
            execute(sendMessageRequest); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return sendMessageRequest;
    }

    @Override
    public String getBotUsername() {
        return BotConfig.WEATHER_USER;
    }

    private SendMessage messageOnstart(Message message) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(message.getChatId())
                .setText("Hello, how may I help you? \n" +
                        "Enter 1, for placing food order \n" +
                        "Enter 2, to inquire food stall timings");

        try {
            execute(sendMessageRequest); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return sendMessageRequest;
    }
}
