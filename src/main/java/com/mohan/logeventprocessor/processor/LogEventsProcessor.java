package com.mohan.logeventprocessor.processor;

import com.mohan.logeventprocessor.dao.LogEventRepository;
import com.mohan.logeventprocessor.domain.LogEvent;
import com.mohan.logeventprocessor.parser.LogEventParser;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class LogEventsProcessor {

    private static Map<String, LogEvent> eventCache = new HashMap<>();


    public void processEvent(String rawEvent){
        CompletableFuture
                .supplyAsync(() -> LogEventParser.getInstance().parse(rawEvent) )
                .thenApply(logEvent ->  processOrPutInCacheLogEvent(logEvent))
                .thenAccept(logEvent -> persistInDB( logEvent));
    }

    private void persistInDB(LogEvent logEvent){
        if(logEvent != null){
            //persist in DB
            try{
                LogEventRepository.getInstance().writeEvent(logEvent);
                System.out.println(logEvent + " persist ");
            }catch (Exception ex){
                log.error("Error Occurred while persisting logEvent {} ",logEvent,ex);
            }
        }
    }
    private LogEvent processOrPutInCacheLogEvent(LogEvent logEvent){
        log.info(" After parsing {}",logEvent);
        String eventId = logEvent.getId();
        if (!eventCache.containsKey(eventId)) {
            eventCache.put(eventId, logEvent);
            return null;
        }
        LogEvent previousEvent = eventCache.remove(eventId);
        long duration = Math.abs(logEvent.getTimestamp() - previousEvent.getTimestamp());
        logEvent.setDuration(duration);
        if (duration > 4) {
            logEvent.setAlert(true);
        }else{
            logEvent.setAlert(false);
        }
        return logEvent;
    }
}
