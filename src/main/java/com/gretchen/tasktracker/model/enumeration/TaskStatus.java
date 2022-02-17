package com.gretchen.tasktracker.model.enumeration;

public enum TaskStatus {
    TODO("TODO"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private final String taskStatus;

    TaskStatus(final String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return taskStatus;
    }
}
