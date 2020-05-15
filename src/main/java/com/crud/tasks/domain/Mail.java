package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class Mail {
    private final String emailReceiver;
    private final String subject;
    private final String message;
    private final String toCc;
}
