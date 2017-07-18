package org.c4sg;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.c4sg.controller.OrganizationController;
import org.c4sg.dto.CreateOrganizationDTO;
import org.c4sg.service.OrganizationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {C4SgApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationControllerTest extends C4SGTest {

    @Autowired
    private OrganizationController organizationController;

    private MockMvc mockMvc;
    
	@Mock
	private OrganizationService organizationServiceMock;
    
    /* OrganizationController Operations
		GET /api/organizations Find all organizations
		GET /api/organizations/search Find organization by keyWord
		GET /api/organizations/user/{id} Find organizations by user id
		GET /api/organizations/{id} Find organization by ID				
		POST /api/organizations Add a new organization
		PUT /api/organizations/{id} Update an existing organization
		DELETE /api/organizations/{id} Deletes a organization
		GET /api/organizations/{id}/logo Retrieves organization logo
		POST /api/organizations/{id}/logo Upload Logo as Image File
    */
    private static final String URI_ADD_ORGANIZATION = "/api/organizations";
    private static final String URI_SEARCH_ORGANIZATION = "/api/organizations/search";
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(organizationController).build();
    }
    
	@After
	public void tearDown() throws Exception {
	}

    @Test
    public void testCreateOrganization() throws Exception {
    	
    	// 1. Only required input field is name
    	CreateOrganizationDTO organizationDto = new CreateOrganizationDTO();
    	organizationDto.setName("Test Organization 1"); 
    	    	 
    	mockMvc.perform(post(URI_ADD_ORGANIZATION)
    			.content(asJsonString(organizationDto))
    			.contentType(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.organization.id", new GreaterThan<Integer>(0))) 
    		.andExpect(jsonPath("$.organization.name",is("Test Organization 1"))) 
    		.andExpect(jsonPath("$.organization.category",is("N"))) // default category is N
    		.andExpect(jsonPath("$.organization.status",is("A"))); // default status is A
    	
    	// 2. Tests all the fields
    	organizationDto = new CreateOrganizationDTO();
    	organizationDto.setName("Test Organization 2"); 
    	organizationDto.setWebsiteUrl("websiteUrl");
    	organizationDto.setDescription("description");
    	organizationDto.setAddress1("address1");
    	organizationDto.setAddress2("address2");
    	organizationDto.setCity("city");
    	organizationDto.setState("state");
    	organizationDto.setZip("zip");
    	organizationDto.setCountry("country");
    	organizationDto.setContactName("contactName");
    	organizationDto.setContactPhone("contactPhone");
    	organizationDto.setContactEmail("contactEmail");
    	organizationDto.setCategory("O");
    	
    	mockMvc.perform(post(URI_ADD_ORGANIZATION)
    			.content(asJsonString(organizationDto))
    			.contentType(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.organization.id", new GreaterThan<Integer>(0))) 
    		.andExpect(jsonPath("$.organization.name",is("Test Organization 2"))) 
    		.andExpect(jsonPath("$.organization.websiteUrl",is("websiteUrl"))) 
    		.andExpect(jsonPath("$.organization.description",is("description"))) 
    		.andExpect(jsonPath("$.organization.address1",is("address1"))) 
    		.andExpect(jsonPath("$.organization.address2",is("address2"))) 
    		.andExpect(jsonPath("$.organization.city",is("city"))) 
    		.andExpect(jsonPath("$.organization.state",is("state"))) 
    		.andExpect(jsonPath("$.organization.zip",is("zip"))) 
    		.andExpect(jsonPath("$.organization.country",is("country"))) 
    		.andExpect(jsonPath("$.organization.contactName",is("contactName"))) 
    		.andExpect(jsonPath("$.organization.contactPhone",is("contactPhone"))) 
    		.andExpect(jsonPath("$.organization.contactEmail",is("contactEmail"))) 
    		.andExpect(jsonPath("$.organization.category",is("O")))
    		.andExpect(jsonPath("$.organization.status",is("A")))
 			.andExpect(jsonPath("$.organization.createdTime", is(nullValue())));
    }
    
    @Test
    public void testSearchOrganization() throws Exception {
    	
    	// 1. Only required input field is name
    	    	 
    	mockMvc.perform(request(HttpMethod.GET, URI_SEARCH_ORGANIZATION)
    			.param("keyword", "Michigan")
    			.param("countries", "")
    			.param("open", "true")
    			.param("status", "A")
    			.param("category", "N", "S")
    			.param("page", "0")
    			.param("size", "100")
    			
    			.accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk()); // default status is A
    	
    	
    }
}