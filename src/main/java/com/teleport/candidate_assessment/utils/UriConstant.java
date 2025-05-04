package com.teleport.candidate_assessment.utils;

public class UriConstant {

  public static final String USER_CONTROLLER_URL = "api/users";
  public static final String USER_CONTROLLER_GET_ENDPOINT = "/{userId}";
  public static final String PROJECT_CONTROLLER_URL = "api/projects";
  public static final String PROJECT_CONTROLLER_GET_ENDPOINT = "/{projectId}";
  public static final String TASK_CONTROLLER_URL = "api/tasks";
  public static final String TASK_CONTROLLER_GET_ENDPOINT = "/{taskId}";
  public static final String FILTERED_TASK_CONTROLLER_GET_ENDPOINT = "/{projectId}/tasks";
  public static final String TASK_CONTROLLER_GET_ASSIGNMENT_ENDPOINT = "/{userId}/assignments";
  public static final String TASK_CONTROLLER_UPDATE_STATUS_ENDPOINT = "/{taskId}/status";
  public static final String TASK_CONTROLLER_GET_OVERDUE_TASKS_ENDPOINT = "/overdue";
}
