package org.c4sg;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.c4sg.controller.ProjectController;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {C4SgApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest extends C4SGTest {

    @Autowired
    private ProjectController projectController;

    private MockMvc mockMvc;
    
    /* ProjectController Operations
       
       GET /api/projects Find all projects
       GET /api/projects/search Find project by name or keyWord
       GET /api/projects/organizations/{id} Find projects by Organization ID
       GET /api/projects/users/{id} Find projects by user ID
       GET /api/projects/applicant/{id} Find applicants of a given project
       GET /api/projects/applied/users/{id} Find projects, with status applied, related to a given user ** TODO Remove               
       GET /api/projects/{id} Find project by ID
       GET /api/projects/{id}/image Retrieves project image       
       POST /api/projects Add a new project
       POST /api/projects/{id}/image Add new image file for project   
       POST /api/projects/{id}/users/{userId}  Create a relation between user and project
       PUT /api/projects/{id} Update an existing project
       DELETE /api/projects/{id} Deletes a project
    */
    
    private static final String URI_GET_PROJECTS = "/api/projects";
    private static final String URI_GET_PROJECTS_BY_KEYWORD = "/api/projects/search";
    private static final String URI_GET_PROJECTS_BY_ORGANIZATION = "/api/projects/organizations/{id}"; 
    private static final String URI_GET_PROJECTS_BY_USER = "/api/projects/user"; 
    private static final String URI_GET_PROJECT_BY_ID = "/api/projects/{id}";
    // private static final String URI_GET_APPLICANTS_FOR_PROJECT = " /api/projects/applicant/{id}"; TODO JW Move to UserController
    // private static final String URI_GET_APPLIED_PROJECTS_FOR_USER = "/api/projects"; TODO JW duplicate, to remove  
    private static final String URI_ADD_PROJECT = "/api/projects";
    private static final String URI_DELETE_PROJECT = "/api/projects/{id}";
    private static final String URI_UPDATE_PROJECT = "/api/projects/{id}";    
    private static final String URI_GET_PROJECT_IMAGE = "/api/projects/{id}/image";
    private static final String URI_SAVE_PROJECT_IMAGE = "/api/projects/{id}/image";    
    private static final String URI_ADD_PROJECT_FOR_USER = "/api/projects/{id}/users/{userId}";
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }
    
	@After
	public void tearDown() throws Exception {
	}

    @Test
    public void testGetProjects() throws Exception {
    	
        this.mockMvc.perform(
        	get(URI_GET_PROJECTS).accept(MediaType.APPLICATION_JSON_UTF8))
        	.andExpect(status().isOk())
            	.andExpect(jsonPath("$", hasSize(greaterThan(5)))) 
         	.andExpect(jsonPath("$[0].id", is(TestDataSet.PROJECT_1_ID))) 
            	.andExpect(jsonPath("$[0].name", is(TestDataSet.PROJECT_1_NAME)))
         	.andExpect(jsonPath("$[0].organizationId", is(TestDataSet.PROJECT_1_ORG_ID)))
         	.andExpect(jsonPath("$[0].organizationName", is(TestDataSet.PROJECT_1_ORG_NAME)))     	
         	.andExpect(jsonPath("$[0].description", is(TestDataSet.PROJECT_1_DESCRIPTION)))
         	.andExpect(jsonPath("$[0].status", is(TestDataSet.PROJECT_1_STATUS)))
     		.andExpect(jsonPath("$[0].createdTime", is(TestDataSet.PROJECT_1_CREATED_TIME)))
        	.andExpect(jsonPath("$[1].name", is(TestDataSet.PROJECT_2_NAME)))
        	.andExpect(jsonPath("$[2].name", is(TestDataSet.PROJECT_3_NAME)))
        	.andExpect(jsonPath("$[3].name", is(TestDataSet.PROJECT_4_NAME)))
        	.andExpect(jsonPath("$[4].name", is(TestDataSet.PROJECT_5_NAME)));
        
        	// TODO JW Should we return images for projects 
    }
/*
    @Test
    public void testGetProjectsByUserAndStatus() throws Exception {
    	
    	// 1. Missing required field
        this.mockMvc.perform(
        	get(URI_GET_PROJECTS_BY_USER).accept(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(status().isBadRequest());
  		
    	// 2. Only required input field is user ID
        this.mockMvc.perform(
        	get(URI_GET_PROJECTS_BY_USER).param("userId", "1")
        	.accept(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$", hasSize(8)))
            	.andExpect(jsonPath("$[0].id", is(1)))
            	.andExpect(jsonPath("$[0].name", is(TestDataSet.PROJECT_1_NAME)))
     		.andExpect(jsonPath("$[0].organizationId", is(TestDataSet.PROJECT_1_ORG_ID)))
     		.andExpect(jsonPath("$[0].organizationName", is(TestDataSet.PROJECT_1_ORG_NAME)))     	
     		.andExpect(jsonPath("$[0].description", is(TestDataSet.PROJECT_1_DESCRIPTION)))
     		.andExpect(jsonPath("$[0].status", is(TestDataSet.PROJECT_1_STATUS)));
    		
    	// 3. Gets applied projects for a user
        this.mockMvc.perform(
        	get(URI_GET_PROJECTS_BY_USER).param("userId", "1").param("userProjectStatus", "A")
        	.accept(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$", hasSize(4)))
            	.andExpect(jsonPath("$[0].id", is(1)))
        	.andExpect(jsonPath("$[1].id", is(2)))
        	.andExpect(jsonPath("$[2].id", is(3)))
        	.andExpect(jsonPath("$[3].id", is(4))); 
        
    	// 4. Gets bookmarked projects for a user
        this.mockMvc.perform(
            get(URI_GET_PROJECTS_BY_USER).param("userId", "1").param("userProjectStatus", "B")
            	.accept(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$", hasSize(4)))
            	.andExpect(jsonPath("$[0].id", is(1)))
        	.andExpect(jsonPath("$[1].id", is(2)))
        	.andExpect(jsonPath("$[2].id", is(3)))        	
		.andExpect(jsonPath("$[3].id", is(4))); 
 
    	// 5. No projects found
        this.mockMvc.perform(
            get(URI_GET_PROJECTS_BY_USER).param("userId", "6")
        	.accept(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$", hasSize(0)));
        
    	// 6. Exception: invalid input on Status
        this.mockMvc.perform(
        	get(URI_GET_PROJECTS_BY_USER).param("userId", "1").param("userProjectStatis", "C")
        	.accept(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(status().isOk()); 
    }
    
    @Test
    public void testGetProjectsByKeyword() throws Exception {
    	fail("Not yet implemented");  		
    }
    
    @Test
    public void testGetProjectsByOrganization() throws Exception {
    	fail("Not yet implemented");  		
    }
    
    @Test
    public void testGetProjectByID() throws Exception {
    	fail("Not yet implemented");  	   	
    }
    
    @Test
    public void testAddProject() throws Exception {
    	fail("Not yet implemented");  	
    }
        
    @Test
    public void testDeleteProject() throws Exception {
    	fail("Not yet implemented");  	   	
    }
        
    @Test
    public void testUpdateProject() throws Exception {
    	fail("Not yet implemented");  	   	
    }
    
    @Test
    public void testGetProjectImaget() throws Exception {
    	fail("Not yet implemented");  	 	
    }
    
    @Test
    public void testSaveProjectImage() throws Exception {
    	fail("Not yet implemented");  	   
    }
    
    @Test
    public void testAddProjectForUser() throws Exception {
    	fail("Not yet implemented");  	
    }*/

}
