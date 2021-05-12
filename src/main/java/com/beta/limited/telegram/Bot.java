package com.beta.limited.telegram;


import com.beta.limited.entity.Report;
import com.beta.limited.servise.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    private final ReportService service;

    @Override
    public void onUpdateReceived(Update update) {
        List<String> list = Arrays.asList(update.getMessage().getText().split("\n"));
        System.out.println(list.toString());
        List<String> notEmptyList = list.stream().filter(str -> str.length() > 1).collect(Collectors.toList());
        List<String> reports = new ArrayList<>();
        service.removeAll();
        for (int i = 0; i < notEmptyList.size(); i++) {
            Report report = new Report();
            if (notEmptyList.get(i).contains("+375")) {

                report.setAddress(notEmptyList.get(i).substring(index(notEmptyList.get(i)),notEmptyList.get(i).length()-13));
                report.setPhoneNumber(notEmptyList.get(i).substring(notEmptyList.get(i).length() - 13));
                service.createReport(report);
                System.out.println("Phone :  " + notEmptyList.get(i).substring(notEmptyList.get(i).length() - 13));
                reports.add(notEmptyList.get(i));
            } else {
                String str = notEmptyList.get(i) + " " + notEmptyList.get(i + 1);
                if (str.contains("+375")) {
                    report.setPhoneNumber(str.substring(str.length()-13));
                    report.setAddress(str.substring(index(str),str.length()-13));
                    service.createReport(report);
                    System.out.println("Phone :  " + str.substring(str.length() - 13));
                    reports.add(str);
                    i++;
                } else {
                    String str1 = str + " " + notEmptyList.get(i + 2);
                    report.setPhoneNumber(str1.substring(str1.length()-13));
                    report.setAddress(str1.substring(index(str1),str1.length()-13));
                    service.createReport(report);
                    reports.add(str1);
                    i = i + 2;
                }
            }
        }
        for (String textMessage : reports) {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText(textMessage);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    public int index(String str){
        int indexOf = 0;
        indexOf = str.toLowerCase().indexOf("аг");
        if(indexOf == 0 || indexOf == -1){
            indexOf = str.toLowerCase().indexOf('г');
        }
        if(indexOf == 0 || indexOf == -1){
            indexOf = str.toLowerCase().indexOf("ул");
        }

        if(indexOf == -1){
            indexOf = 0;
        }
        return indexOf;
    }
}
