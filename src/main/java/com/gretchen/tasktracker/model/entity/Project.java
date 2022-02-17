package com.gretchen.tasktracker.model.entity;

import com.gretchen.tasktracker.model.enumeration.ProjectStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinColumn(name = "task_id")
    private List<Task> tasks = new ArrayList<>();

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectStatus status;



    public void addTask(Task task) {
        this.tasks.add(task);
        task.setProject(this);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }
}
