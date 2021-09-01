package com.beta.limited.telegram;


import com.beta.limited.servise.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    private final ReportService service;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String user = update.getMessage().getFrom().getUserName();
        service.messageToReport(update.getMessage().getText(), user);
    }
    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }


}
