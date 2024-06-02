package gr.hua.agricoop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cooperatives")
public class Cooperative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String vat;

    @Column
    private String status;

    @Column
    private String notes;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private User employee;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="cooperative_farmers",
            joinColumns = @JoinColumn(name="cooperative_id"),
            inverseJoinColumns = @JoinColumn(name="farmer_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"farmer_id", "cooperative_id"})}
    )
    private List<User> farmers;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="cooperative_products",
            joinColumns = @JoinColumn(name="cooperative_id"),
            inverseJoinColumns = @JoinColumn(name="product_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"product_id", "cooperative_id"})}
    )
    private List<Product> products;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="cooperative_locations",
            joinColumns = @JoinColumn(name="cooperative_id"),
            inverseJoinColumns = @JoinColumn(name="location_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"location_id", "cooperative_id"})}
    )
    private List<CultivationLocation> cultivationLocations;

    public Cooperative() {
        this.status = "processing";
        this.notes = "";
    }

    public Cooperative(String name, String vat) {
        this();
        this.name = name;
        this.vat = vat;
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

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public List<User> getFarmers() {
        return farmers;
    }

    public void setFarmers(List<User> farmers) {
        this.farmers = farmers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<CultivationLocation> getCultivationLocations() {
        return cultivationLocations;
    }

    public void setCultivationLocations(List<CultivationLocation> cultivationLocations) {
        this.cultivationLocations = cultivationLocations;
    }

    public void addFarmer(User farmer) {
        farmers.add(farmer);
    }

    public void removeFarmer(User farmer) {
        farmers.remove(farmer);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void addCultivationLocation(CultivationLocation cultivationLocation) {
        cultivationLocations.add(cultivationLocation);
    }

    public void removeCultivationLocation(CultivationLocation cultivationLocation) {
        cultivationLocations.remove(cultivationLocation);
    }

    public String check() {
        if (status.equalsIgnoreCase("approved")) {
            return "Application already approved.";
        } else if (status.equalsIgnoreCase("rejected")) {
            return "Application already rejected.";
        }
        if (!(farmers.isEmpty() || products.isEmpty() || cultivationLocations.isEmpty()) && isVatValid()) {
            return "Application is valid.";
        } else {
            return getString();
        }
    }

    private String getString() {
        String checkResult = "";
        if (farmers.isEmpty()) {
            checkResult += "Less than 1 member,";
        }
        if (products.isEmpty()) {
            checkResult += "Less than 1 product,";
        }
        if (cultivationLocations.isEmpty()) {
            checkResult += "Less than 1 cultivation location,";
        }
        if (!isVatValid()) {
            checkResult += "Invalid VAT number";
        }
        return checkResult;
    }

    private boolean isVatValid() {
        if (vat.length() == 9) {
            try {
                Integer.parseInt(vat);
            } catch (NumberFormatException error) {
                return false;
            }
            int sum = 0;
            for (int i = 0, product; i < 8; i++) {
                product = Character.getNumericValue(vat.charAt(i));
                product *= (int) Math.pow(2, 8 - i);
                sum += product;
            }
            return (sum % 11) % 10 == Character.getNumericValue(vat.charAt(8)) && !vat.equals("000000000");
        } else {
            return false;
        }
    }

    @PreRemove
    private void preRemove() {
        if (employee != null) {
            employee.removeApplication(this);
        }
        for (User farmer : farmers) {
            farmer.removeCooperative(this);
        }
        for (Product product : products) {
            product.removeCooperative(this);
        }
        for (CultivationLocation cultivationLocation : cultivationLocations) {
            cultivationLocation.removeCooperative(this);
        }
    }

    @Override
    public String toString() {
        return "Cooperative{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vat='" + vat + '\'' +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
