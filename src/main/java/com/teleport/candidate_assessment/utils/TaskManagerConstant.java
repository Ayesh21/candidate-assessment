package com.teleport.candidate_assessment.utils;

/** The type Constants. */
public class TaskManagerConstant {
  /** The enum Status. */
  public enum Status {
    /** New status. */
    NEW,
    /** In progress status. */
    IN_PROGRESS,
    /** Completed status. */
    COMPLETED
  }

  /** The enum Priority. */
  public enum Priority {
    /** Low priority. */
    LOW,
    /** Medium priority. */
    MEDIUM,
    /** High priority. */
    HIGH
  }

  public static final String USER_CONTROLLER_CREATE_ENDPOINT_SUMMARY = "Create a new user";
  public static final String USER_CONTROLLER_CREATE_ENDPOINT_DESCRIPTION =
      "Registers a new user with username and email";
  public static final String USER_CONTROLLER_CREATE_ENDPOINT_OUTPUT_OK = "OK";
  public static final String USER_CONTROLLER_GET_ENDPOINT_SUMMARY = "Get user by ID";
  public static final String USER_CONTROLLER_GET_ENDPOINT_DESCRIPTION =
      "Fetches user details by their unique ID";

  public static final String PROJECT_CONTROLLER_CREATE_ENDPOINT_SUMMARY = "Create a new project";
  public static final String PROJECT_CONTROLLER_CREATE_ENDPOINT_DESCRIPTION =
      "Creates a new project with a name and owner ID";
  public static final String PROJECT_CONTROLLER_GET_ENDPOINT_SUMMARY = "Get project by ID";
  public static final String PROJECT_CONTROLLER_GET_ENDPOINT_DESCRIPTION =
      "Fetches project details by its unique ID";

  public static final String TASK_CONTROLLER_CREATE_ENDPOINT_SUMMARY = "Create a task";
  public static final String TASK_CONTROLLER_CREATE_ENDPOINT_DESCRIPTION =
      "Creates a new task under a given project";
  public static final String TASK_CONTROLLER_GET_ENDPOINT_SUMMARY = "Get task by ID";
  public static final String TASK_CONTROLLER_GET_ENDPOINT_DESCRIPTION =
      "Fetches tasks details by task Id";
  public static final String FILTERED_TASK_CONTROLLER_GET_ENDPOINT_SUMMARY = "Get filtered tasks";
  public static final String FILTERED_TASK_CONTROLLER_GET_ENDPOINT_DESCRIPTION =
      "Returns tasks by project with optional filtering by status and priority";
  public static final String TASK_CONTROLLER_GET_ASSIGNMENT_ENDPOINT_SUMMARY =
      "View user assignments";
  public static final String TASK_CONTROLLER_GET_ASSIGNMENT_ENDPOINT_DESCRIPTION =
      "Returns all tasks assigned to the user";
  public static final String TASK_CONTROLLER_UPDATE_STATUS_ENDPOINT_SUMMARY = "Update task status";
  public static final String TASK_CONTROLLER_UPDATE_STATUS_ENDPOINT_DESCRIPTION =
      "Updates the status of a task if it's not completed";
  public static final String TASK_CONTROLLER_GET_OVERDUE_TASKS_ENDPOINT_SUMMARY =
      "Get overdue tasks";
  public static final String TASK_CONTROLLER_GET_OVERDUE_TASKS_ENDPOINT_DESCRIPTION =
      "Updates the status of a task if it's not completed";
  public static final String REFERENCE_CONTROLLER_CREATE_ENDPOINT_SUMMARY =
          "Create a Reference";
  public static final String REFERENCE_CONTROLLER_CREATE_DESCRIPTION =
          "Create new Reference for all the user Id , project Id and task Id";
  public static final String REFERENCE_CONTROLLER_GET_USER_ID_ENDPOINT_SUMMARY =
          "Get User Id";
  public static final String REFERENCE_CONTROLLER_GET_USER_ID_DESCRIPTION =
          "Get Reference using user Id";
  public static final String REFERENCE_CONTROLLER_GET_PROJECT_ID_ENDPOINT_SUMMARY =
          "Get Project Id";
  public static final String REFERENCE_CONTROLLER_GET_USERID_DESCRIPTION =
          "Get Reference using project Id";
  public static final String REFERENCE_CONTROLLER_GET_TASK_ID_ENDPOINT_SUMMARY =
          "Get Task Id";
  public static final String REFERENCE_CONTROLLER_GET_TASK_ID_DESCRIPTION =
          "Get Reference using task Id";
}
