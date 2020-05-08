package org.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.updateshandlers.*;

/**
 * @author Srushti Sunder
 * @version 1.0
 * @brief Main class to create all bots
 * @date 19 January 2020
 */
public class Main {

    private static final String LOGTAG = "MAIN";

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new FoodleBot());
            System.out.println("Bot is online!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
