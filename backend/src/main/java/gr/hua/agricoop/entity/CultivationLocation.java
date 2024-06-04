package gr.hua.agricoop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "cultivation_locations")
public class CultivationLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String address;

    @Column
    private String area;

    @Column
    private String zipCode;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "cultivationLocations")
    private List<Cooperative> cooperatives;

    public CultivationLocation() {
    }

    public CultivationLocation(String address, String area, String zipCode) {
        this.address = address;
        this.area = area;
        this.zipCode = zipCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    @PreRemove
    private void preRemove() {
        for (Cooperative cooperative : cooperatives) {
            cooperative.removeCultivationLocation(this);
        }
    }

    @Override
    public String toString() {
        return "CultivationLocation{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
