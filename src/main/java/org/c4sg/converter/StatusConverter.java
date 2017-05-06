package org.c4sg.converter;

import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.c4sg.constant.UserStatus;

@Converter
public class StatusConverter implements AttributeConverter<UserStatus, String> {
	@Override
	public String convertToDatabaseColumn(UserStatus s) {
		StringBuilder sb = new StringBuilder();
		sb.append(s.getValue());
		return sb.toString();
	}
	
	@Override
	public UserStatus convertToEntityAttribute(String s) {
    	if(!Optional.ofNullable(s.toUpperCase()).isPresent()) 
        {
             return UserStatus.PENDING;
        }
        return UserStatus.valueOf(UserStatus.class, UserStatus.getStatus(s));
	}
}
