package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.service.CooperativeService;
import gr.hua.agricoop.service.CultivationLocationService;
import gr.hua.agricoop.service.ProductService;
import gr.hua.agricoop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cooperative")
@CrossOrigin(origins = "http://localhost:8080/",
        methods = {RequestMethod.GET, RequestMethod.POST},
        allowedHeaders = {"*", "Content-Type"})
public class CooperativeRestController {

    @Autowired
    private CooperativeService cooperativeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CultivationLocationService cultivationLocationService;

    @Secured("ROLE_MODERATOR")
    @GetMapping("")
    public List<Cooperative> showCooperatives() {
        return cooperativeService.getCooperatives();
    }

    @Secured("ROLE_USER")
    @GetMapping("{cooperative_id}")
    public Cooperative getCooperative(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id);
    }

    // δε χρειάζεται
    @Secured("ROLE_USER")
    @GetMapping("/new")
    public Cooperative addCooperative() {
        return new Cooperative();
    }

    @Secured("ROLE_USER")
    @PostMapping("/empty")
    public Cooperative addEmptyCooperative() {
        return cooperativeService.saveCooperative(new Cooperative());
    }

    @Secured("ROLE_USER")
    @PutMapping("{cooperative_id}")
    public Cooperative editCooperative(@PathVariable Integer cooperative_id, @RequestBody Cooperative updatedCooperative) {
        Cooperative existingCooperative = cooperativeService.getCooperative(cooperative_id);
        if (existingCooperative != null) {
            existingCooperative.setName(updatedCooperative.getName());
            existingCooperative.setVat(updatedCooperative.getVat());
            cooperativeService.saveCooperative(existingCooperative);
        }
        return existingCooperative; // επιστρέφει null αν το cooperative δεν υπάρχει
    }

    @Secured("ROLE_USER")
    @PostMapping("/new")
    public List<Cooperative> saveCooperative(@RequestBody Cooperative cooperative) {
        cooperativeService.saveCooperative(cooperative);
        return cooperativeService.getCooperatives();
    }

    @Secured("ROLE_USER")
    @DeleteMapping("{cooperative_id}")
    public List<Cooperative> deleteCooperative(@PathVariable Integer cooperative_id) {
        cooperativeService.deleteCooperative(cooperative_id);
        return cooperativeService.getCooperatives();
    }

    @GetMapping("/user/{user_id}")
    public Cooperative getUserCooperative(@PathVariable Long user_id) {
        return cooperativeService.getUserCooperative(user_id);
    }

    @GetMapping("{cooperative_id}/farmers")
    public List<User> getCooperativeFarmers(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getFarmers();
    }

    @GetMapping("{cooperative_id}/products")
    public List<Product> getCooperativeProducts(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getProducts();
    }

    @GetMapping("{cooperative_id}/cultivation_locations")
    public List<CultivationLocation> getCooperativeCultivationLocations(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getCultivationLocations();
    }

    @PostMapping("/users/{cooperative_id}/{user_id}")
    public List<User> addUser(@PathVariable Integer cooperative_id, @PathVariable Long user_id, Model model) {
        Cooperative cooperative = cooperativeService.getCooperative(cooperative_id);
        User user = userService.getUser(user_id);
        cooperative.addFarmer(user);
        cooperativeService.saveCooperative(cooperative);
        user.setCooperative(cooperative);
        userService.saveUser(user);
        model.addAttribute((cooperative));
        model.addAttribute((userService.getFarmersWithoutCooperative()));
        return cooperative.getFarmers();
    }

    @PostMapping("/products/{cooperative_id}/{product_id}")
    public List<Product> addProduct(@PathVariable Integer cooperative_id, @PathVariable Integer product_id, Model model) {
        Cooperative cooperative = cooperativeService.getCooperative(cooperative_id);
        Product product = productService.getProduct(product_id);
        cooperative.addProduct(product);
        cooperativeService.saveCooperative(cooperative);
        product.setCooperative(cooperative);
        productService.saveProduct(product);
        model.addAttribute(cooperative);
        model.addAttribute(productService.getProductsWithoutCooperative());
        return cooperative.getProducts();
    }

    @PostMapping("/cultivation_locations/{cooperative_id}/{cultivation_location_id}")
    public List<CultivationLocation> addCultivationLocation(@PathVariable Integer cooperative_id, @PathVariable Integer cultivation_location_id, Model model) {
        Cooperative cooperative = cooperativeService.getCooperative(cooperative_id);
        CultivationLocation cultivationLocation = cultivationLocationService.getCultivationLocation(cultivation_location_id);
        cooperative.addCultivationLocation(cultivationLocation);
        cooperativeService.saveCooperative(cooperative);
        cultivationLocation.setCooperative(cooperative);
        cultivationLocationService.saveCultivationLocation(cultivationLocation);
        model.addAttribute(cooperative);
        model.addAttribute(cultivationLocationService.getCultivationLocationsWithoutCooperative());
        return cooperative.getCultivationLocations();
    }
}
