package org.c4sg.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.c4sg.dto.OrganizationDTO;
import org.c4sg.dto.afg.FeedDateTimeDurationDTO;
import org.c4sg.dto.afg.FeedInfoDTO;
import org.c4sg.dto.afg.FeedLocationDTO;
import org.c4sg.dto.afg.FeedSponsoringOrganizationDTO;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
	
	public FeedInfoDTO addFeedInfo() {
	    FeedInfoDTO feedInfo = new FeedInfoDTO();
	    Date currentDateTime = new Date();
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss");
	    String tempDate = ft.format(currentDateTime);
	    feedInfo.setCreatedDateTime(tempDate);
	    return feedInfo;
    }
    
    public FeedLocationDTO populateFeedLocation(OrganizationDTO o) {
    	FeedLocationDTO location = new FeedLocationDTO();
		location.setCity(o.getCity());
		location.setPostalCode(o.getZip());
		if (o.getAddress1() == null || o.getAddress2() == null) {
			if(o.getAddress1() == null && !((o.getAddress1() == null) && (o.getAddress2() == null))){
				location.setStreetAddress1(o.getAddress2());
			} else if (o.getAddress2() == null && !(o.getAddress1() == null && o.getAddress2() == null)) {
				location.setStreetAddress1(o.getAddress1());
			} 
		} else {
			location.setStreetAddress1(o.getAddress1() + o.getAddress2());
		}
		location.setRegion(o.getState());
		return location;
    }
	
    public List<FeedDateTimeDurationDTO> populateDateTimeDurations() {
    	FeedDateTimeDurationDTO dateTimeDuration = new FeedDateTimeDurationDTO();
		List<FeedDateTimeDurationDTO> dateTimeDurations = new ArrayList<FeedDateTimeDurationDTO>();
		dateTimeDurations.add(dateTimeDuration);
		return dateTimeDurations;
    }
    
    public FeedSponsoringOrganizationDTO populateSponsoringOrganization(OrganizationDTO o, int i) {
		FeedSponsoringOrganizationDTO sponsoringOrganization = new FeedSponsoringOrganizationDTO();
		sponsoringOrganization.setOrganizationId(o.getProjects().get(i).getOrganizationId());
		return sponsoringOrganization;
    }
    
    public void populateRemoteFlag(OrganizationDTO o, int i) {
		String flag = o.getProjects().get(i).getRemoteFlag();
		if(flag.equals("Y")) {
			o.getProjects().get(i).setVirtual("true");
		} else {
			o.getProjects().get(i).setVirtual("false");
		}
    }

}
