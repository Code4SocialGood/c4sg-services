package org.c4sg;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.c4sg.constraint.ListEntry;
import org.c4sg.dto.CreateProjectDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

@Ignore
public class ConstraintValidatorTest {
	
	private static Validator validator;
	

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testinvalidCategory() {
    	CategoryListTest cat = new CategoryListTest("N", "I", "Z");
        Set<ConstraintViolation<CategoryListTest>> constraintViolations = validator.validate(cat);
        assertEquals(1, constraintViolations.size());
        assertEquals("Entries must be  one of [NOSU]", constraintViolations.iterator().next().getMessage());
    }
    
    @Test
    public void testValidCategory() {

    	CategoryListTest cat = new CategoryListTest("N", "O", "S", "U");
        Set<ConstraintViolation<CategoryListTest>> constraintViolations = validator.validate(cat);
        assertEquals(0, constraintViolations.size());
    }
    
    @Test
    public void testinvalidCustomCategory() {
    	CategoryCustomListTest cat = new CategoryCustomListTest("N", "I", "Z");
        Set<ConstraintViolation<CategoryCustomListTest>> constraintViolations = validator.validate(cat);
        assertEquals(1, constraintViolations.size());
        assertEquals("Entries must be  one of [IT]", constraintViolations.iterator().next().getMessage());
    }
    
    @Test
    public void testValidCustomCategory() {

    	CategoryCustomListTest cat = new CategoryCustomListTest("I", "T");
        Set<ConstraintViolation<CategoryCustomListTest>> constraintViolations = validator.validate(cat);
        assertEquals(0, constraintViolations.size());
    }
    
    @Test
    public void testValidateCreateProjectDTO() {
    	
    	CreateProjectDTO cpdto = new CreateProjectDTO();
    	cpdto.setName("AAAAmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
    	cpdto.setRemoteFlag("Y");
        Set<ConstraintViolation<CreateProjectDTO>> constraintViolations = validator.validate(cpdto);
        assertEquals(0, constraintViolations.size());
    }

}

class CategoryListTest {
	
	@ListEntry
	private List<String> cats;
	
	public CategoryListTest(String ... cat) {
		cats = Arrays.asList(cat);
	}
}

class CategoryCustomListTest {
	
	@ListEntry(value = {"I", "T"}, message = "Entries must be  one of [IT]")
	private List<String> cats;
	
	public CategoryCustomListTest(String ... cat) {
		cats = Arrays.asList(cat);
	}
	
	
}
