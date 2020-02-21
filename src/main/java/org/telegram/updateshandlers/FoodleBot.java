package org.telegram.updateshandlers;

import org.telegram.BotConfig;
import org.telegram.Utils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

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

    public FoodleBot() {
        super();
//        startAlertTimers();
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
        UserInformation userinformation = new UserInformation();

        switch (INFO_REQUESTED) {

            case "/start":
                messageOnstart(message);

            case INITIAL_SELECTION:
                userinformation.setInitialSelection(message.getText());
                if (userinformation.getInitialSelection().equals("1")) {
                    canteenSelectionMessage(message, Utils.MESSAGE_LIST_OF_CANTEENS);
                }
                break;
            case CANTEEN_NUMBER:
                //TODO check if message contains any non numeric elements
                userinformation.setCanteen(Utils.getCanteenFromIndexNumber(message.getText().trim()));
                foodStallSelectionMessage(message, userinformation.getCanteen());
                break;
            case STALL_NUMBER:
                userinformation.setStallNumber(message.getText().trim());
                foodItemSelectMessage(message, userinformation.getStallNumber());
                break;


            case "/stop":
            case "/help":
            case "/menu":
        }

    }

    private void foodItemSelectMessage(Message message, String stallNumber) {
        sendMessageToUser(message,"Stopping here for now ");
    }

    private void foodStallSelectionMessage(Message incomingMessage, String canteen) {
        sendMessageToUser(incomingMessage,Utils.getFoodStallSelectionMessage(canteen));
        INFO_REQUESTED = STALL_NUMBER;
    }

    private void canteenSelectionMessage(Message incomingMessage, String outgoingMessage) {
        sendMessageToUser(incomingMessage,outgoingMessage);
        INFO_REQUESTED = CANTEEN_NUMBER;
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

    private void messageOnstart(Message message) {
        sendMessageToUser(message, Utils.OUTGOING_MESSAGE_ON_START);
        INFO_REQUESTED = INITIAL_SELECTION;
    }

}
