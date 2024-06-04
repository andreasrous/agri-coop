package gr.hua.agricoop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private Double price;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "products")
    private List<Cooperative> cooperatives;

    public Product() {
    }

    public Product(String name, String category, Double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
            cooperative.removeProduct(this);
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
