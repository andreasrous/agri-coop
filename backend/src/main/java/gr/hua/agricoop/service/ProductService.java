package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product getProduct(Integer productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    @Transactional
    public List<Product> getProductsWithoutCooperative() {
        List<Product> products = productRepository.findAll();
        products.removeIf(product -> !product.getCooperatives().isEmpty());
        return products;
    }

    @Transactional
    public Product saveProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Transactional
    public Product editProduct(Integer productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct != null) {
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setName(updatedProduct.getName());
            productRepository.save(existingProduct);
        }
        return existingProduct;
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
}
