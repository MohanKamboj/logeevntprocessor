package com.mohan.logeventprocessor.parser;

import com.google.gson.Gson;
import com.mohan.logeventprocessor.domain.LogEvent;

import java.util.Objects;

public class LogEventParser {

    private LogEventParser(){}
    private Gson gson = new Gson();
    public static LogEventParser logEventParser;

    public static LogEventParser getInstance(){
        if(Objects.isNull(logEventParser)){
            synchronized (LogEventParser.class){
                if(Objects.isNull(logEventParser)){
                    logEventParser = new LogEventParser();
                }
            }
        }
        return logEventParser;
    }

    public LogEvent parse(String rawString){
        return gson.fromJson(rawString, LogEvent.class);
    }
}
