package com.gretchen.tasktracker.model.enumeration;

public enum ProjectStatus {
    NOT_STARTED("NOT_STARTED"),
    ACTIVE("ACTIVE"),
    COMPLETED("COMPLETED");

    private final String projectStatus;

    ProjectStatus(final String taskStatus) {
        this.projectStatus = taskStatus;
    }

    @Override
    public String toString() {
        return projectStatus;
    }
}
