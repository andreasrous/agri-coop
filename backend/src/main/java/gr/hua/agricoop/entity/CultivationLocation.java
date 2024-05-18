package gr.hua.agricoop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cultivation_location")
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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "cooperative_id")
    private Cooperative cooperative;

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

    public Cooperative getCooperative() {
        return cooperative;
    }

    public void setCooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
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
