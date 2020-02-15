package org.telegram.updateshandlers;

import org.telegram.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Handler for updates to Weather Bot
 * @date 24 of June of 2015
 */
public class FoodleBot extends TelegramLongPollingBot {
    private static final String LOGTAG = "WEATHERHANDLERS";

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
        if (message.getText().equals("/start")){
            messageOnstart(message);
        }



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
