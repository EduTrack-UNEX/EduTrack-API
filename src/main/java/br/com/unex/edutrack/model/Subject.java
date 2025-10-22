package br.com.unex.edutrack.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @Column(name = "id",updatable = false,nullable = false,unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "average",nullable = false)
    private Float average;

    @Column(name = "progress", nullable = false)
    private int progress = 0;

    @Column(name = "created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

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

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
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

    public static class SubjectBuilder{
        private String name;
        private Float average;
        private int progress;

        public SubjectBuilder withName(String name){
            this.name = name;
            return this;
        }

        public SubjectBuilder withAverage(Float average){
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
}
