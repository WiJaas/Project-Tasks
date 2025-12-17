package com.hahn.tasks.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDING;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    public void markCompleted() {
        this.status = TaskStatus.COMPLETED;
    }
}
