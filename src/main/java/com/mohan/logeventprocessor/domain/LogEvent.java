package com.mohan.logeventprocessor.domain;


import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEvent {

    private String id;
    private long duration;
    private boolean alert;
    private String type;
    private String host;
    private long timestamp;
    private String state;
}
