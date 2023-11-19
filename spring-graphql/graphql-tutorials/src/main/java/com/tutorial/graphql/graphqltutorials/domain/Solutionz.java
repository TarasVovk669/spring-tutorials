package com.tutorial.graphql.graphqltutorials.domain;

import com.tutorial.graphql.graphqltutorials.generated.types.SolutionCategory;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "solutionz")
public class Solutionz {

    @Id
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private String content;

    private SolutionCategory category;

    private int voteAsGoodCount;

    private int voteAsBadCount;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Userz createdBy;

    @ManyToOne
    @JoinColumn(name = "problemz_id", nullable = false)
    private Problemz problem;

    public static SolutionzBuilder builder(){
        return new SolutionzBuilder();
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SolutionCategory getCategory() {
        return category;
    }

    public void setCategory(SolutionCategory category) {
        this.category = category;
    }

    public int getVoteAsGoodCount() {
        return voteAsGoodCount;
    }

    public void setVoteAsGoodCount(int voteAsGoodCount) {
        this.voteAsGoodCount = voteAsGoodCount;
    }

    public int getVoteAsBadCount() {
        return voteAsBadCount;
    }

    public void setVoteAsBadCount(int voteAsBadCount) {
        this.voteAsBadCount = voteAsBadCount;
    }

    public Userz getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Userz createdBy) {
        this.createdBy = createdBy;
    }

    public Problemz getProblem() {
        return problem;
    }

    public void setProblem(Problemz problem) {
        this.problem = problem;
    }


    public static final class SolutionzBuilder {
        private UUID id;
        private LocalDateTime createdDateTime;
        private String content;
        private SolutionCategory category;
        private int voteAsGoodCount;
        private int voteAsBadCount;
        private Userz createdBy;
        private Problemz problem;

        private SolutionzBuilder() {
        }

        public static SolutionzBuilder aSolutionz() {
            return new SolutionzBuilder();
        }

        public SolutionzBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public SolutionzBuilder createdDateTime(LocalDateTime createdDateTime) {
            this.createdDateTime = createdDateTime;
            return this;
        }

        public SolutionzBuilder content(String content) {
            this.content = content;
            return this;
        }

        public SolutionzBuilder category(SolutionCategory category) {
            this.category = category;
            return this;
        }

        public SolutionzBuilder voteAsGoodCount(int voteAsGoodCount) {
            this.voteAsGoodCount = voteAsGoodCount;
            return this;
        }

        public SolutionzBuilder voteAsBadCount(int voteAsBadCount) {
            this.voteAsBadCount = voteAsBadCount;
            return this;
        }

        public SolutionzBuilder createdBy(Userz createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public SolutionzBuilder problem(Problemz problem) {
            this.problem = problem;
            return this;
        }

        public Solutionz build() {
            Solutionz solutionz = new Solutionz();
            solutionz.setId(id);
            solutionz.setCreatedDateTime(createdDateTime);
            solutionz.setContent(content);
            solutionz.setCategory(category);
            solutionz.setVoteAsGoodCount(voteAsGoodCount);
            solutionz.setVoteAsBadCount(voteAsBadCount);
            solutionz.setCreatedBy(createdBy);
            solutionz.setProblem(problem);
            return solutionz;
        }
    }
}
