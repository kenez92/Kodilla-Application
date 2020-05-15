package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {
    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "/getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoard = trelloClient.getTrelloBoards();

        trelloBoard.forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getName() + " - " + trelloBoardDto.getId());

            System.out.println("This boards contains lists : ");

            trelloBoardDto.getLists().forEach(trelloList ->
                    System.out.println(trelloList.getName() + " - " + trelloList.getId()));
        });

    }

    @RequestMapping(method = RequestMethod.POST, value = "/createTrelloCard")
    public CreatedTrelloCard createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}
