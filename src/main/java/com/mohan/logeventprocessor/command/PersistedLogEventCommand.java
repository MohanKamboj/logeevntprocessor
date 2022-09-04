package com.mohan.logeventprocessor.command;

import com.mohan.logeventprocessor.exception.InvalidParameterException;
import com.mohan.logeventprocessor.handler.LogEventCommandHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersistedLogEventCommand implements Command {

    private LogEventCommandHandler handler;
    public PersistedLogEventCommand(LogEventCommandHandler handler){
        this.handler=handler;
    }
    @Override
    public String helpText() {
        return "PERSISTED_LOG_EVENT <GET_ALL>,<GET_ALERT_EVENT>,<DELETE_ALL_EVENT>, this command play operation persisted log events in DB";
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if (params.length == 0) {
            handler.getAllPersistedLogEvents();;
        }else if(params.length > 1){
            throw new InvalidParameterException("Expected parameter is only One , PERSISTED_LOG_EVENT <GET_ALL> or <GET_ALERT_EVENT> or <DELETE_ALL_EVENT>");
        }else if ("GET_ALL".equalsIgnoreCase(params[0])) {
            handler.getAllPersistedLogEvents();
        } else if ("GET_ALERT_EVENT".equalsIgnoreCase(params[0])){
            handler.selectAlertEvent();
        }else if ("DELETE_ALL_EVENT".equalsIgnoreCase(params[0])){
            handler.selectAlertEvent();
        }else{
            throw new InvalidParameterException("Expected parameter is true or false , GET_ALL_PERSISTED_LOG_EVENT or GET_ALL_PERSISTED_LOG_EVENT <alert>");
        }
        log.info("Command completed ");
    }
}
