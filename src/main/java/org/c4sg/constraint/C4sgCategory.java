package org.c4sg.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

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
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
@Constraint(validatedBy = C4sgCategoryPattern.class)
public @interface C4sgCategory {
	
	String message() default "Entries must be  one of [NOUMTS]";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	String[] value() default {"N", "O", "U", "S", "T", "M"};

}
