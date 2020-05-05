package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {
    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "/getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoard = trelloClient.getTrelloBoards().stream()
                .filter(trelloBoardDto -> trelloBoardDto.getId() != null && (trelloBoardDto.getName() != null &&
                        trelloBoardDto.getName().contains("Kodilla")))
                .collect(Collectors.toList());
        trelloBoard.forEach(trelloBoardDto -> System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName()));
    }
}
