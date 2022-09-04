package com.mohan.logeventprocessor.command;

import com.mohan.logeventprocessor.exception.InvalidParameterException;
import com.mohan.logeventprocessor.handler.LogEventCommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ProcessLogFileCommand implements Command {

    private LogEventCommandHandler handler;

    public  ProcessLogFileCommand(LogEventCommandHandler handler){
        this.handler = handler;
    }
    @Override
    public String helpText() {
        return "PROCESS_LOG_FILE_COMMAND <filepath.txt>";
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if (params.length < 1) {
            throw new InvalidParameterException("Expected one parameter as file name like <logfile.txt>");
        }

        if (!StringUtils.contains(params[0], ".txt")) {
            throw new InvalidParameterException("Expected file name like <logfile.txt>");
        }
        Path path = Paths.get(params[0]);
        if(!Files.exists(path)){
            throw new InvalidParameterException("Can't found file at path "+params[0]);
        }
        this.handler.startProcessingFile(params[0]);
        //log.info("Command completed ");
    }
}
