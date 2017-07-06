package org.c4sg.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * @author Ogwara O. Rowland
 * @since  06/03/2017
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, ANNOTATION_TYPE, TYPE, PARAMETER })
@Constraint(validatedBy = ListEntryValidator.class)
public @interface ListEntry {
	
	String message() default "Entries must be  one of [NOSU]";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	String[] value() default {"N", "O", "S", "U"};

}
