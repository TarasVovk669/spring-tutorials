package com.tutorial.graphql.graphqltutorials.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "problemz")
public class Problemz {

    @Id
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private String title;

    private String content;

    private String tags;

    @OneToMany(mappedBy = "problem")
    @OrderBy("createdDateTime desc")
    private List<Solutionz> solutions;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Userz createdBy;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Solutionz> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solutionz> solutions) {
        this.solutions = solutions;
    }

    public Userz getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Userz createdBy) {
        this.createdBy = createdBy;
    }


    public static final class ProblemzBuilder {
        private UUID id;
        private LocalDateTime createdDateTime;
        private String title;
        private String content;
        private String tags;
        private List<Solutionz> solutions;
        private Userz createdBy;

        private ProblemzBuilder() {
        }

        public static ProblemzBuilder aProblemz() {
            return new ProblemzBuilder();
        }

        public ProblemzBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ProblemzBuilder createdDateTime(LocalDateTime createdDateTime) {
            this.createdDateTime = createdDateTime;
            return this;
        }

        public ProblemzBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ProblemzBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ProblemzBuilder tags(String tags) {
            this.tags = tags;
            return this;
        }

        public ProblemzBuilder solutions(List<Solutionz> solutions) {
            this.solutions = solutions;
            return this;
        }

        public ProblemzBuilder createdBy(Userz createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Problemz build() {
            Problemz problemz = new Problemz();
            problemz.setId(id);
            problemz.setCreatedDateTime(createdDateTime);
            problemz.setTitle(title);
            problemz.setContent(content);
            problemz.setTags(tags);
            problemz.setSolutions(solutions);
            problemz.setCreatedBy(createdBy);
            return problemz;
        }
    }
}
