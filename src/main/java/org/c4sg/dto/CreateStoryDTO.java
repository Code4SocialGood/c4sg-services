package org.c4sg.dto;

import org.c4sg.entity.StoryType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateStoryDTO {

    @Size(max = 100, message = "Body cannot exceed 100 characters")
    private String body;

    @Size(max = 100, message = "Image URL cannot exceed 100 characters")
    private String imageUrl;

    @NotNull(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotNull(message = "StoryType is required")
    private StoryType type;

    @NotNull(message = "UserId is required")
    private Integer userId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StoryType getType() {
        return type;
    }

    public void setType(StoryType type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
