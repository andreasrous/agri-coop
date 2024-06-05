package gr.hua.agricoop.rest;

import gr.hua.agricoop.dto.CooperativeDto;
import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.service.CooperativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cooperative")
public class CooperativeRestController {

    @Autowired
    private CooperativeService cooperativeService;

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping("")
    public List<Cooperative> getCooperatives() {
        return cooperativeService.getCooperatives();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{cooperative_id}")
    public Cooperative getCooperative(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/user/{user_id}")
    public List<Cooperative> getUserCooperatives(@PathVariable Long user_id) {
        return cooperativeService.getUserCooperatives(user_id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/new")
    public List<Cooperative> addCooperative(@RequestBody Cooperative cooperative) {
        cooperativeService.saveCooperative(cooperative);
        return cooperativeService.getCooperatives();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/{cooperative_id}")
    public ResponseEntity<Cooperative> editCooperative(@PathVariable Integer cooperative_id, @RequestBody CooperativeDto cooperativeDto) {
        Cooperative updatedCooperative = cooperativeService.editCooperative(cooperative_id, cooperativeDto);
        return ResponseEntity.ok(updatedCooperative);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/{cooperative_id}")
    public List<Cooperative> deleteCooperative(@PathVariable Integer cooperative_id) {
        cooperativeService.deleteCooperative(cooperative_id);
        return cooperativeService.getCooperatives();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{cooperative_id}/farmers")
    public List<User> getCooperativeFarmers(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getFarmers();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{cooperative_id}/products")
    public List<Product> getCooperativeProducts(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getProducts();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{cooperative_id}/cultivation-locations")
    public List<CultivationLocation> getCooperativeCultivationLocations(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getCultivationLocations();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{cooperative_id}/farmer/{user_id}")
    public List<User> addFarmer(@PathVariable Integer cooperative_id, @PathVariable Long user_id) {
        return cooperativeService.addFarmer(cooperative_id, user_id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{cooperative_id}/product/{product_id}")
    public List<Product> addProduct(@PathVariable Integer cooperative_id, @PathVariable Integer product_id) {
        return cooperativeService.addProduct(cooperative_id, product_id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{cooperative_id}/cultivation-location/{cultivation_location_id}")
    public List<CultivationLocation> addCultivationLocation(@PathVariable Integer cooperative_id, @PathVariable Integer cultivation_location_id) {
        return cooperativeService.addCultivationLocation(cooperative_id, cultivation_location_id);
    }
}
