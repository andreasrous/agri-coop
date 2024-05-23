package gr.hua.agricoop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String firstName;

    private String lastName;

    private String vat;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "farmers")
    private List<Cooperative> cooperatives;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Cooperative> applications;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Cooperative> getCooperatives() {
        return cooperatives;
    }

    public void setCooperatives(List<Cooperative> cooperatives) {
        this.cooperatives = cooperatives;
    }

    public void addCooperative(Cooperative cooperative) {
        cooperatives.add(cooperative);
    }

    public void removeCooperative(Cooperative cooperative) {
        cooperatives.remove(cooperative);
    }

    public List<Cooperative> getApplications() {
        return applications;
    }

    public void setApplications(List<Cooperative> applications) {
        this.applications = applications;
    }

    public void addApplication(Cooperative cooperative) {
        applications.add(cooperative);
    }

    public void removeApplication(Cooperative cooperative) {
        cooperatives.remove(cooperative);
    }

    public void approveApplication(Cooperative cooperative) {
        cooperative.setStatus("approved");
        addApplication(cooperative);
    }

    public void rejectApplication(Cooperative cooperative) {
        cooperative.setStatus("rejected");
        addApplication(cooperative);
    }

    @PreRemove
    private void preRemove() {
        for (Cooperative application : applications) {
            application.setEmployee(null);
        }
        for (Cooperative cooperative : cooperatives) {
            cooperative.removeFarmer(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
