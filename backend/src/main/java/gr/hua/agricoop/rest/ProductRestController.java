package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:8080/",
        methods = {RequestMethod.GET, RequestMethod.POST},
        allowedHeaders = {"*", "Content-Type"})
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> showProducts() {
        return productService.getProducts();
    }

    // δεν αποθηκεύει το product στον πίνακα (τζάμπα μέθοδος)
    @GetMapping("/new")
    public Product addProduct() {
        return new Product();
    }

    // κάνει την αποθήκευση
    @PostMapping("/empty")
    public Product addEmptyProduct() {
        return productService.saveProduct(new Product());
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<Product> editProduct(@PathVariable Integer product_id, @RequestBody Product product) {
        Product updatedProduct = productService.editProduct(product_id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/new")
    public List<Product> saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return productService.getProducts();
    }

    @DeleteMapping("{product_id}")
    public List<Product> deleteProduct(@PathVariable Integer product_id) {
        productService.deleteProduct(product_id);
        return productService.getProducts();
    }
}