package org.c4sg.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.c4sg.dto.OrganizationDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.dto.afg.FeedDTO;
import org.c4sg.dto.afg.FeedDateTimeDurationDTO;
import org.c4sg.dto.afg.FeedInfoDTO;
import org.c4sg.dto.afg.FeedLocationDTO;
import org.c4sg.dto.afg.FeedSponsoringOrganizationDTO;
import org.c4sg.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/allforgood")
@Api(description = "AllforGood Feed Responses", tags = "feed")
public class AllForGoodFeedController {
	@Autowired
    private OrganizationService organizationService;
	
	@CrossOrigin
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    @ApiOperation(value = "Find all projects", notes = "Returns a collection of projects")
	public FeedDTO getProjects() {
		List<OrganizationDTO> organizations = organizationService.findOrganizations();
	    FeedDTO afg = new FeedDTO();
	    afg.setOrganizations(organizations);
	    FeedInfoDTO feedInfo = new FeedInfoDTO();
	    Date currentDateTime = new Date();
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss");
	    String tempDate = ft.format(currentDateTime);
	    feedInfo.setCreatedDateTime(tempDate);
	    afg.setFeedInfo(feedInfo);
		List<ProjectDTO> projects = new ArrayList<ProjectDTO>(); 
	    //TODO: Maybe move this to another place?
		for (OrganizationDTO o : organizations) {
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
			o.setLocation(location);
			for (int i = 0; i < o.getProjects().size(); i++) {
				FeedDateTimeDurationDTO dateTimeDuration = new FeedDateTimeDurationDTO();
				List<FeedDateTimeDurationDTO> dateTimeDurations = new ArrayList<FeedDateTimeDurationDTO>();
				dateTimeDurations.add(dateTimeDuration);
				o.getProjects().get(i).setDateTimeDurations(dateTimeDurations);
				List<FeedSponsoringOrganizationDTO> sponsoringOrganizations = new ArrayList<FeedSponsoringOrganizationDTO>();
				FeedSponsoringOrganizationDTO sponsoringOrganization = new FeedSponsoringOrganizationDTO();
				sponsoringOrganization.setOrganizationId(o.getProjects().get(i).getOrganizationId());
				sponsoringOrganizations.add(sponsoringOrganization);
				o.getProjects().get(i).setSponsoringIds(sponsoringOrganizations);
				String flag = o.getProjects().get(i).getRemoteFlag();
				if(flag.equals("Y")) {
					o.getProjects().get(i).setVirtual("true");
				} else {
					o.getProjects().get(i).setVirtual("false");
				}
				projects.add(o.getProjects().get(i));
			} 
			afg.setProjects(projects);
		}
        return afg;
    }
	
}
