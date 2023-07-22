package com.spring.discordBot.events;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KeepAliveService {
    @Scheduled(fixedRate = 1 * 1000 * 60) //1 minute
    public void reportCurrentTime(){
        System.out.println(System.currentTimeMillis());
    }
}
