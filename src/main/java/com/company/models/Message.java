package com.company.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Message {
    private String from;
    private String text;
    private LocalDateTime time;
}
