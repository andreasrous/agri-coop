package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/new")
    public List<Product> addProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return productService.getProducts();
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<Product> editProduct(@PathVariable Integer product_id, @RequestBody Product product) {
        Product updatedProduct = productService.editProduct(product_id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{product_id}")
    public List<Product> deleteProduct(@PathVariable Integer product_id) {
        productService.deleteProduct(product_id);
        return productService.getProducts();
    }
}