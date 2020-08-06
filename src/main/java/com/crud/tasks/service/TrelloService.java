package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@AllArgsConstructor
@Service
public class TrelloService {
    private static final String SUBJECT = "Tasks: New trello card";
    @Autowired
    private final AdminConfig adminConfig;
    @Autowired
    private final SimpleEmailService simpleEmailService;
    @Autowired
    private final TrelloClient trelloClient;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card ->simpleEmailService.send(new Mail(
                adminConfig.getEmail(),
                SUBJECT,
                "New card : " + trelloCardDto.getName() + " has been created on you trello account",
                null,
                MailType.TRELLO_MAIL)));

        return newCard;
    }
}
