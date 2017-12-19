package org.c4sg.dto;

import org.c4sg.entity.StoryType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This unit tests the various javax.validation.constraints.* annotations.
 */
public class CreateStoryDTOValidatorTest {

    private static final String LONG_STRING = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
    private static javax.validation.Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void createStoryDTO_Valid() {
        CreateStoryDTO createStoryDTO = buildValidCreateStoryDTO();
        Set<ConstraintViolation<CreateStoryDTO>> violations = validator.validate(createStoryDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void createStoryDTO_NullUserId() {
        CreateStoryDTO createStoryDTO = buildValidCreateStoryDTO();
        createStoryDTO.setUserId(null);
        Set<ConstraintViolation<CreateStoryDTO>> violations = validator.validate(createStoryDTO);
        assertTrue(violations.size()==1);
        assertEquals( "UserId is required", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void createStoryDTO_NullStoryType() {
        CreateStoryDTO createStoryDTO = buildValidCreateStoryDTO();
        createStoryDTO.setType(null);
        Set<ConstraintViolation<CreateStoryDTO>> violations = validator.validate(createStoryDTO);
        assertTrue(violations.size()==1);
        assertEquals( "StoryType is required", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void createStoryDTO_NullStoryTitle() {
        CreateStoryDTO createStoryDTO = buildValidCreateStoryDTO();
        createStoryDTO.setTitle(null);
        Set<ConstraintViolation<CreateStoryDTO>> violations = validator.validate(createStoryDTO);
        assertTrue(violations.size()==1);
        assertEquals( "Title is required", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void createStoryDTO_LongStoryTitle() {
        CreateStoryDTO createStoryDTO = buildValidCreateStoryDTO();
        createStoryDTO.setTitle(LONG_STRING);
        Set<ConstraintViolation<CreateStoryDTO>> violations = validator.validate(createStoryDTO);
        assertTrue(violations.size()==1);
        assertEquals( "Title cannot exceed 100 characters", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void createStoryDTO_LongImage() {
        CreateStoryDTO createStoryDTO = buildValidCreateStoryDTO();
        createStoryDTO.setImageUrl(LONG_STRING);
        Set<ConstraintViolation<CreateStoryDTO>> violations = validator.validate(createStoryDTO);
        assertTrue(violations.size()==1);
        assertEquals( "Image URL cannot exceed 100 characters", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void createStoryDTO_LongBody() {
        CreateStoryDTO createStoryDTO = buildValidCreateStoryDTO();
        createStoryDTO.setBody(LONG_STRING);
        Set<ConstraintViolation<CreateStoryDTO>> violations = validator.validate(createStoryDTO);
        assertTrue(violations.size()==1);
        assertEquals( "Body cannot exceed 100 characters", violations.stream().findFirst().get().getMessage());
    }

    private CreateStoryDTO buildValidCreateStoryDTO() {

        CreateStoryDTO request = new CreateStoryDTO();
        String title = "an open source story";
        String imageUrl = ""; // not required
        String body = ""; // not required

        request.setTitle(title);
        request.setType(StoryType.ORGANIZATION);
        request.setUserId(1);
        request.setBody(body);
        request.setImageUrl(imageUrl);

        return request;
    }
}