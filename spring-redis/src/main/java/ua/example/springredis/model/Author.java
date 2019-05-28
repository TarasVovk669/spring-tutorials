package ua.example.springredis.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Author")
public class Author implements Serializable {

    private Long id;
    private String name;
    private Long age;
    private Double rating;
    private Gender sex;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
