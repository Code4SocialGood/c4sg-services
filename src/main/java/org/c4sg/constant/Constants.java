package org.c4sg.constant;

public final class Constants {
	
	public static final String YES = "Y";
	public static final String No = "N";			
	
	// Domain Object Status
	public static final String PROJECT_STATUS_NEW = "N";
	public static final String PROJECT_STATUS_ACTIVE = "A";
	public static final String PROJECT_STATUS_CLOSED = "C";
	
	public static final String ORGANIZATION_STATUS_NEW = "N";
	public static final String ORGANIZATION_STATUS_PENDIONG_REVIEW = "P";
	public static final String ORGANIZATION_STATUS_ACTIVE = "A";
	public static final String ORGANIZATION_STATUS_DECLINED = "C";
	public static final String ORGANIZATION_STATUS_DELETED = "D";
	
	public static final String USER_STATUS_NEW = "N";
	public static final String USER_STATUS_PENDING = "P"; // What is this status for?
	public static final String USER_STATUS_ACTIVE = "A";
	public static final String USER_STATUS_DELETED = "D";

	public static final String USER_PROJECT_STATUS_APPLIED = "A";
	public static final String USER_PROJECT_STATUS_BOOKMARKED = "B";
	public static final String USER_PROJECT_STATUS_ACCEPTED = "C";
	public static final String USER_PROJECT_STATUS_DECLINED = "D";
	
	// User Role
	public static final String USER_ROLE_VOLUNTEER = "V";
	public static final String USER_ROLE_ORGANIZATION = "O";
	public static final String USER_ROLE_ADMIN = "A";
	
	// Email Delivery Service
	public static final String C4SG_ADDRESS = "info@code4socialgood.org";
    
	public static final String SUBJECT_APPLICAITON_ORGANIZATION = "Code for Social Good: You received an application";
	public static final String SUBJECT_APPLICAITON_VOLUNTEER = "Code for Social Good: Your application is submitted";
	public static final String SUBJECT_NEW_PROJECT_NOTIFICATION = "Code for Social Good: New Project Notification";
	public static final String SUBJECT_NEW_ORGANIZATION_REVIEW = "Code for Social Good: A New Organization is ready for review";
	public static final String SUBJECT_NEW_ORGANIZATION_APPROVE = "Code for Social Good: Your Organization is approved";
	public static final String SUBJECT_NEW_ORGANIZATION_DECLINE= "Code for Social Good: Your Organization is declined";
	public static final String SUBJECT_DELETE_USER= "Code for Social Good: Delete User Notification";
    
	public static final String TEMPLATE_APPLICAITON_ORGANIZATION = "project-application-organization";
	public static final String TEMPLATE_APPLICAITON_VOLUNTEER = "project-application-volunteer";
	public static final String TEMPLATE_NEW_PROJECT_NOTIFICATION = "new-project-notification";
	public static final String TEMPLATE_NEW_ORGANIZATION_REVIEW = "new-organization-review";
	public static final String TEMPLATE_NEW_ORGANIZATION_APPROVE = "new-organization-approve";
	public static final String TEMPLATE_NEW_ORGANIZATION_DECLINE = "new-organization-decline";
	public static final String TEMPLATE_DELETE_USER = "delete-user";
}
