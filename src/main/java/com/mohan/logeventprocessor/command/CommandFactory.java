package com.mohan.logeventprocessor.command;


import com.mohan.logeventprocessor.exception.CommandNotFoundException;
import com.mohan.logeventprocessor.exception.InvalidParameterException;
import com.mohan.logeventprocessor.handler.LogEventCommandHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommandFactory {

    private static final String PROCESS_LOG_FILE_COMMAND ="PROCESS_LOG_FILE_COMMAND";
    private static final String GET_ALL_PERSISTED_LOG_EVENT ="GET_ALL_PERSISTED_LOG_EVENT";

    private Map<String, Command> commands;

    private CommandFactory() {
        commands = new HashMap<>();
    }

    public static CommandFactory init(LogEventCommandHandler  logEventCommandHandler) {
        final CommandFactory commandFactory = new CommandFactory();
        commandFactory.addCommand(PROCESS_LOG_FILE_COMMAND, new ProcessLogFileCommand(logEventCommandHandler));
        commandFactory.addCommand(GET_ALL_PERSISTED_LOG_EVENT, new PersistedLogEventCommand(logEventCommandHandler));
        return commandFactory;
    }

    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    public void executeCommand(String name, String[] params)throws CommandNotFoundException, InvalidParameterException {
        if (commands.containsKey(name)) {
            commands.get(name).execute(params);
        } else {
            throw new CommandNotFoundException(name);
        }
    }

    public void listCommandHelp() {
        commands
            .keySet()
            .stream()
            .map(command -> commands.get(command).helpText())
            .forEach(helpText-> log.info(helpText));
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
