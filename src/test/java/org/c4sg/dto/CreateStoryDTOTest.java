package org.c4sg.dto;

import org.c4sg.entity.StoryType;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateStoryDTOTest {

    @Test
    public void instantiate(){

        CreateStoryDTO request = new CreateStoryDTO();

        assertEquals(null, request.getTitle());
        assertEquals(null, request.getType());
        assertEquals(null, request.getBody());
        assertEquals(null, request.getImageUrl());
        assertEquals(null, request.getUserId());
    }

    @Test
    public void initialize(){
        CreateStoryDTO request = new CreateStoryDTO();
        String title = "an open source story";
        String imageUrl = "i";
        String body = "b";

        request.setTitle(title);
        request.setType(StoryType.ORGANIZATION);
        request.setUserId(1);
        request.setBody(body);
        request.setImageUrl(imageUrl);

        assertEquals(title, request.getTitle());
        assertEquals(StoryType.ORGANIZATION, request.getType());
        assertEquals(body, request.getBody());
        assertEquals(imageUrl, request.getImageUrl());
        assertEquals(new Integer(1), request.getUserId());
    }
}