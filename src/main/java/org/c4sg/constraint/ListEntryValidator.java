package org.c4sg.constraint;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Handles validation for Lists annotated with {@link ListEntry}
 * @author Ogwara O. Rowland
 * @since  06/03/2017
 *
 */
public class ListEntryValidator implements ConstraintValidator<ListEntry, List<String>>{

	private String message;
	private String[] categories;

	@Override
	public void initialize(ListEntry ca) {
		this.message = ca.message();
		categories = ca.value();
		
	}

	@Override
	public boolean isValid(List<String> values, ConstraintValidatorContext context) {
		
		if(values == null || values.isEmpty()) {
			return true;
		}
		
		for (String val : values) {
			boolean valid = false;
			for(String cat: categories) {
				if(cat.equals(val)) {
					valid = true;
				}
			}
			
			if(!valid) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
				return false;
			}
		}
		return true;
	}

}
