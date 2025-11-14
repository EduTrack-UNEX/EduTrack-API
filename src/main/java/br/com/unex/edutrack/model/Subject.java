package br.com.unex.edutrack.model;

import br.com.unex.edutrack.dto.task.TaskEditRequestDto;
import br.com.unex.edutrack.dto.task.TaskRequestDto;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @Column(name = "id",updatable = false,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "average",nullable = false,precision = 4,scale = 2)
    private BigDecimal average;

    @Column(name = "progress", nullable = false)
    private int progress;

    @Column(name = "created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> toDoList = new ArrayList<>();

    public Subject(){

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

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Task> getToDoList() {
        return Collections.unmodifiableList(this.toDoList);
    }


    public static class SubjectBuilder{
        private String name;
        private BigDecimal average;
        private int progress;

        public SubjectBuilder withName(String name){
            this.name = name;
            return this;
        }

        public SubjectBuilder withAverage(BigDecimal average){
            this.average = average;
            return this;
        }

        public SubjectBuilder withProgress(int progress){
            this.progress = progress;
            return this;
        }

        public Subject build(){
            Subject subject = new Subject();
            subject.setName(this.name);
            subject.setAverage(this.average);
            subject.setProgress(this.progress);
            return subject;
        }

    }

    public void addTask (Task task){
        this.toDoList.add(task);
        task.setSubject(this);
        this.calculateAverage();
        this.updateProgress();
    }

    public Task updateWith(int taskId, TaskEditRequestDto dto){
        Task task = this.toDoList.stream().filter(t -> t.getId() == taskId)
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException("Tarefa não encontrada com esse id: "+taskId));
        task.setName(dto.name());
        task.setGrade(dto.grade());
        task.setCompleted(dto.isCompleted());
        task.setDueDate(dto.dueDate());
        this.calculateAverage();
        this.updateProgress();
        return task;
    }

    private void calculateAverage(){

        if (this.toDoList == null || this.toDoList.isEmpty()) {
            this.average = BigDecimal.ZERO;
            return;
        }

        BigDecimal sum = this.toDoList.stream()
                .map(Task::getGrade)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal size = new BigDecimal(this.toDoList.size());
        this.average = sum.divide(size,2, RoundingMode.HALF_UP);
    }

    private void updateProgress(){
        int totalTasks = this.toDoList.size();

        if (totalTasks == 0) {
            this.setProgress(0);
            return;
        }

        long completedTasks = this.toDoList
                .stream()
                .filter(Task::isCompleted)
                .count();
        int progress = (int) ((completedTasks * 100.0) / totalTasks);
        this.setProgress(progress);
    }
}
