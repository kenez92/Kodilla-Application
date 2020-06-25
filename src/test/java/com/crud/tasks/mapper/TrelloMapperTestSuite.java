package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class TrelloMapperTestSuite {
    @Autowired
    private TrelloMapper trelloMapper;

    private TrelloListDto createTrelloListDto() {
        return new TrelloListDto("Test list id", "test name list", true);
    }

    private TrelloList createTrelloList() {
        return new TrelloList("Test list id", "test name list", true);
    }

    private TrelloBoardDto createTrelloBoardDto() {
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(createTrelloListDto());
        trelloListDtos.add(createTrelloListDto());
        return new TrelloBoardDto("Test trello board", "test id", trelloListDtos);
    }

    private TrelloBoard createTrelloBoard() {
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(createTrelloList());
        trelloLists.add(createTrelloList());
        return new TrelloBoard("Test trello board", "test id", trelloLists);
    }

    private TrelloCardDto createTrelloCardDto() {
        return new TrelloCardDto("Test card name", "test description",
                "test pos", "test list id");
    }

    private TrelloCard createTrelloCard() {
        return new TrelloCard("Test card name", "test description",
                "test pos", "test list id");
    }

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(createTrelloBoardDto());
        trelloBoardDtos.add(createTrelloBoardDto());
        int trelloBoardListSize = trelloBoardDtos.get(0).getLists().size();
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        Assert.assertEquals(2, trelloBoards.size());
        Assert.assertEquals("Test trello board", trelloBoards.get(0).getName());
        Assert.assertEquals(trelloBoardListSize, trelloBoards.get(0).getLists().size());
        Assert.assertNotEquals("new board id", trelloBoards.get(0).getId());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(createTrelloBoard());
        trelloBoards.add(createTrelloBoard());
        trelloBoards.add(createTrelloBoard());
        int trelloBoardListSize = trelloBoards.get(0).getLists().size();
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        Assert.assertEquals(3, trelloBoardDtos.size());
        Assert.assertEquals("Test trello board", trelloBoardDtos.get(0).getName());
        Assert.assertEquals(trelloBoardListSize, trelloBoardDtos.get(0).getLists().size());
        Assert.assertNotEquals("new board id", trelloBoardDtos.get(0).getId());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(createTrelloListDto());
        trelloListDtos.add(createTrelloListDto());
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        Assert.assertEquals(2, trelloLists.size());
        Assert.assertEquals("Test list id", trelloLists.get(0).getId());
        Assert.assertNotEquals(false, trelloLists.get(0).isClosed());
        Assert.assertEquals("test name list", trelloLists.get(0).getName());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(createTrelloList());
        trelloLists.add(createTrelloList());
        trelloLists.add(createTrelloList());
        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        Assert.assertEquals(3, trelloListDtos.size());
        Assert.assertEquals("Test list id", trelloListDtos.get(0).getId());
        Assert.assertNotEquals(false, trelloListDtos.get(0).isClosed());
        Assert.assertEquals("test name list", trelloListDtos.get(0).getName());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = createTrelloCardDto();
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertEquals("Test card name", trelloCard.getName());
        Assert.assertEquals("test description", trelloCard.getDescription());
        Assert.assertEquals("test pos", trelloCard.getPos());
        Assert.assertEquals("test list id", trelloCard.getListId());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = createTrelloCard();
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertEquals("Test card name", trelloCardDto.getName());
        Assert.assertEquals("test description", trelloCardDto.getDescription());
        Assert.assertEquals("test pos", trelloCardDto.getPos());
        Assert.assertEquals("test list id", trelloCardDto.getListId());
    }
}