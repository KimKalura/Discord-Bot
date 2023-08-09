package com.spring.discordBot.events;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public abstract class MessageListener {

   /* public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!todo"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Things to do today:\n - write a bot\n - eat lunch\n - play a game"))
                .then();
    }*/

    private String author = "UNKNOWN";

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> {
                    final Boolean isNotBot = message.getAuthor()
                            .map(user -> {
                                if (!user.isBot()) {
                                    author = user.getUsername();
                                }
                                return !user.isBot();
                            })
                            .orElse(false);
                    return isNotBot;
                })
                .flatMap(message -> switch (message.getContent()) {
                    case "!todo monday", "!todo tuesday", "!todo wednesday", "!todo thursday", "!todo friday", "!todo saturday", "!todo sunday" ->
                            processTodoCommand(message);
                    case "hello" -> processHelloCommand(message);
                    default -> processDefaultCommand(message);
                });
    }

    private Mono<Void> processTodoCommand(Message eventMessage) {
       /* return eventMessage.getChannel()
                .flatMap(channel -> channel.createMessage("Things to do today:\n - write a bot\n - read a book\n - algorithms"))
                .then();*/

        String content = eventMessage.getContent();

        if (content.contains("!todo monday")) {
            return eventMessage.getChannel()
                    .flatMap(channel -> channel.createMessage("Things to do today:\n - read a book\n - algorithms\n - build an app"))
                    .then();
        } else if (content.contains("!todo tuesday")) {
            return eventMessage.getChannel()
                    .flatMap(channel -> channel.createMessage("Things to do today:\n - eine Sprache üben\n - build an app\n - algorithms"))
                    .then();

        } else if (content.contains("!todo wednesday")) {
            return eventMessage.getChannel()
                    .flatMap(channel -> channel.createMessage("Things to do today:\n - read a book\n - learn a new programming language\n - algorithms"))
                    .then();
        } else if (content.contains("!todo thursday")) {
            return eventMessage.getChannel()
                    .flatMap(channel -> channel.createMessage("Things to do today:\n - read a book\n - eine Sprache üben\n - algorithms"))
                    .then();
        } else if (content.contains("!todo friday")) {
            return eventMessage.getChannel()
                    .flatMap(channel -> channel.createMessage("Things to do today:\n - read something\n - play guitar\n - algorithms"))
                    .then();
        } else if (content.contains("!todo saturday")) {
            return eventMessage.getChannel()
                    .flatMap(channel -> channel.createMessage("Things to do today:\n - build an app\n -  read a book\n - algorithms"))
                    .then();
        } else if (content.contains("!todo sunday")) {
            return eventMessage.getChannel()
                    .flatMap(channel -> channel.createMessage("Things to do today:\n - learn a new programming language\n - read a book\n -  read a book"))
                    .then();
        } else {
            return eventMessage.getChannel()
                    .flatMap(channel -> channel.createMessage("Choose a day of the week. Example: !todo monday"))
                    .then();
        }
    }

    private Mono<Void> processHelloCommand(Message eventMessage) {
        return eventMessage.getChannel()
                .flatMap(channel -> channel.createMessage(String.format("你好 '%s'", author)))
                .then();
    }

    private Mono<Void> processDefaultCommand(Message eventMessage) {
        return eventMessage.getChannel()
                .flatMap(channel -> channel.createMessage("I can not give you more details. I am in the process of development!"))
                .then();
    }

    /*private String getTodayDate() {
        // Get the current date
        LocalDate today = LocalDate.now();
        // Format the date as a string in the format "YYYY-MM-DD"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return today.format(formatter);
    }*/
    //block()

}
