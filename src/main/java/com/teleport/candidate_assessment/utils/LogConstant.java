package com.teleport.candidate_assessment.utils;

/** The type Log constant. */
public class LogConstant {

  public static final String CREATE_USER = "Creating User: {}";
  public static final String GET_USER_BY_ID = "Fetching User by User ID: {}";

  public static final String CREATE_PROJECT = "Creating Project: {}";
  public static final String GET_PROJECT_BY_ID = "Fetching Project by project ID: {}";

  public static final String CREATE_TASK = "Creating Task: {}";
  public static final String GET_TASK_BY_ID = "Fetching Task by Task ID: {}";
  public static final String GET_FILTERED_TASK_BY_ID =
      "Fetching filtered tasks - Project ID: {}, Status: {}, Priority: {}";
  public static final String GET_ASSIGNMENT_BY_USER_ID =
      "Fetching all tasks assigned to the user: {}";
  public static final String UPDATE_STATUS_BY_TASK_ID = "Updates the status by Task ID: {}";
  public static final String GET_OVERDUE_TASKS_BY_CURRENT_DATE =
      "Fetches all tasks that are past due";
  public static final String TASKS_STATUS= "Task status: {}";
}
