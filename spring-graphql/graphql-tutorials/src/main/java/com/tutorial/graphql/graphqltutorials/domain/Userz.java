package com.tutorial.graphql.graphqltutorials.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "userz")
public class Userz {

    @Id
    private UUID id;
    private String username;
    private String email;
    private String password;
    private URL photo;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    private String displayName;
    private boolean active;
    private String userRole;

    public static UserzBuilder builder(){
        return new UserzBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public URL getPhoto() {
        return photo;
    }

    public void setPhoto(URL photo) {
        this.photo = photo;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }


    public static final class UserzBuilder {
        private UUID id;
        private String username;
        private String email;
        private String password;
        private URL photo;
        private LocalDateTime creationDateTime;
        private String displayName;
        private boolean active;
        private String userRole;

        private UserzBuilder() {
        }

        public static UserzBuilder anUserz() {
            return new UserzBuilder();
        }

        public UserzBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserzBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserzBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserzBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserzBuilder photo(URL photo) {
            this.photo = photo;
            return this;
        }

        public UserzBuilder creationDateTime(LocalDateTime creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        public UserzBuilder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public UserzBuilder active(boolean active) {
            this.active = active;
            return this;
        }

        public UserzBuilder userRole(String userRole) {
            this.userRole = userRole;
            return this;
        }

        public Userz build() {
            Userz userz = new Userz();
            userz.setId(id);
            userz.setUsername(username);
            userz.setEmail(email);
            userz.setPassword(password);
            userz.setPhoto(photo);
            userz.setCreationDateTime(creationDateTime);
            userz.setDisplayName(displayName);
            userz.setActive(active);
            userz.setUserRole(userRole);
            return userz;
        }
    }
}
