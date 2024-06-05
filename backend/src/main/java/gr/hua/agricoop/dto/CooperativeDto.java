package gr.hua.agricoop.dto;

import java.util.List;

public class CooperativeDto {
    private String name;
    private String vat;
    private List<UserDto> farmers;
    private List<ProductDto> products;
    private List<CultivationLocationDto> cultivationLocations;

    public String getName() {
        return name;
    }

    public String getVat() {
        return vat;
    }

    public List<UserDto> getFarmers() {
        return farmers;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public List<CultivationLocationDto> getCultivationLocations() {
        return cultivationLocations;
    }
}
