package org.c4sg.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
 * @author Ogwara O. Rowland
 * @since 06/03/2017
 *
 */
@Service
public interface GeocodeService {
	
	/**
	 * Returns longitude and Latitude for given location
	 * @param state
	 * @param country
	 * @return
	 * @throws Exception 
	 */
	Map<String, BigDecimal> getGeoCode(String state, String country) throws Exception;

}
