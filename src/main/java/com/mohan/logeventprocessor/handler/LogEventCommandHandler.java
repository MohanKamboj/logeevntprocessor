package com.mohan.logeventprocessor.handler;

import com.mohan.logeventprocessor.dao.LogEventRepository;
import com.mohan.logeventprocessor.processor.LogEventsProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogEventCommandHandler {

    ExecutorService executorService = Executors.newFixedThreadPool(10);
    private LogEventsProcessor logEventsProcessor = new LogEventsProcessor();

    public void startProcessingFile(String filepath) {

        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(filepath));
            String line ;
            while ((line = reader.readLine()) != null) {
                logEventsProcessor.processEvent(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllPersistedLogEvents(){
        LogEventRepository.getInstance().selectAll();
    }

    public void selectAlertEvent(){
        LogEventRepository.getInstance().selectAlertEvent();
    }

    public void deleteAllAlertEvent(){
        LogEventRepository.getInstance().deleteAll();
    }
}
