package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.repository.CooperativeRepository;
import gr.hua.agricoop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CooperativeRepository cooperativeRepository;

    public Product editProduct(Integer productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCooperative(updatedProduct.getCooperative());

        return productRepository.save(existingProduct);
    }

    @Transactional
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product saveProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public Product getProduct(Integer productId) {
        return productRepository.findById(productId).get();
    }

    @Transactional
    public List<Product> getProductsWithoutCooperative() {
        List<Product> products = productRepository.findAll();
        products.removeIf(product -> product.getCooperative() != null);
        return products;
    }
}
