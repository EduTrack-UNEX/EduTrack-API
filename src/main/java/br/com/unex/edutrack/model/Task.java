package br.com.unex.edutrack.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "id",updatable = false,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "grade",precision = 4,scale = 2)
    private BigDecimal grade;

    @Column(name = "isCompleted",nullable = false)
    private boolean isCompleted;

    @Column(name = "dueDate")
    private LocalDate dueDate;

    @Column(name = "created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "subject_id",nullable = false)
    private Subject subject;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public static class TaskBuilder{
        private String name;
        private BigDecimal grade;
        private boolean isCompleted;
        private LocalDate dueDate;

        public TaskBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TaskBuilder withGrade(BigDecimal grade){
            this.grade = grade;
            return this;
        }

        public TaskBuilder withIsCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public TaskBuilder withDueData(LocalDate dueDate){
            this.dueDate = dueDate;
            return this;
        }

        public Task build(){
            Task task = new Task();
            task.setName(this.name);
            task.setGrade(this.grade);
            task.setCompleted(this.isCompleted);
            task.setDueDate(this.dueDate);
            return task;
        }
    }
}
