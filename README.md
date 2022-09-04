# logeventprocessor
Application used to read , process and write the log Event

# Log Event Processor

## To can implement this via Spring batch job with easy 
- I use plain java to implement it.
- implmented via Command design pattern.
- Used Completable Future to process the read , parse and write the in DB.

# dataBase files is at hsqldb

### _Pre Requisites java11 and maven_

## How to Run 

- Run main class from com.mohan.logeventprocessor.LogEventProcessorApplication
- it will give open console to enter command 
- Below are the command that 
    - PROCESS_LOG_FILE_COMMAND <logfie path> e.g PROCESS_LOG_FILE_COMMAND logfile.txt
    - To get persisted event 
    - GET_ALL_PERSISTED_LOG_EVENT 
    - GET_ALL_PERSISTED_LOG_EVENT GET_ALL (to return all persisted event)
    - GET_ALL_PERSISTED_LOG_EVENT GET_ALERT_EVENT (to return event having alert true)
    - GET_ALL_PERSISTED_LOG_EVENT DELETE_ALL_EVENT



