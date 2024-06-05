package gr.hua.agricoop.service;

import gr.hua.agricoop.dto.CooperativeDto;
import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.repository.CooperativeRepository;
import gr.hua.agricoop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CooperativeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CooperativeRepository cooperativeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CultivationLocationService cultivationLocationService;

    @Transactional
    public List<Cooperative> getCooperatives() {
        return cooperativeRepository.findAll();
    }

    @Transactional
    public Cooperative getCooperative(Integer cooperativeId) {
        return cooperativeRepository.findById(cooperativeId).orElseThrow();
    }

    @Transactional
    public List<Cooperative> getUserCooperatives(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getCooperatives();
    }

    @Transactional
    public Cooperative saveCooperative(Cooperative cooperative) {
        cooperativeRepository.save(cooperative);
        return cooperative;
    }

    @Transactional
    public Cooperative editCooperative(Integer cooperativeId, CooperativeDto updatedCooperative) {
        Cooperative existingCooperative = cooperativeRepository.findById(cooperativeId).orElse(null);
        if (existingCooperative != null) {
            List<User> farmers = updatedCooperative.getFarmers().stream()
                    .map(farmerDto -> userService.getUser(farmerDto.getId())).collect(Collectors.toList());
            List<Product> products = updatedCooperative.getProducts().stream()
                    .map(productDto -> productService.getProduct(productDto.getId())).collect(Collectors.toList());
            List<CultivationLocation> cultivationLocations = updatedCooperative.getCultivationLocations().stream()
                    .map(locationDto -> cultivationLocationService.getCultivationLocation(locationDto.getId())).collect(Collectors.toList());
            existingCooperative.setName(updatedCooperative.getName());
            existingCooperative.setVat(updatedCooperative.getVat());
            existingCooperative.setFarmers(farmers);
            existingCooperative.setProducts(products);
            existingCooperative.setCultivationLocations(cultivationLocations);
            cooperativeRepository.save(existingCooperative);
        }
        return existingCooperative;
    }

    @Transactional
    public void deleteCooperative(Integer cooperativeId) {
        cooperativeRepository.deleteById(cooperativeId);
    }

    @Transactional
    public List<Cooperative> getUnprocessedApplications() {
        List<Cooperative> cooperatives = cooperativeRepository.findAll();
        cooperatives.removeIf(cooperative -> !cooperative.getStatus().equalsIgnoreCase("processing"));
        return cooperatives;
    }

    @Transactional
    public List<Cooperative> getProcessedApplications(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getApplications();
    }

    @Transactional
    public String checkApplication(Integer cooperativeId) {
        return "{\"checkResult\": \"" + getCooperative(cooperativeId).check() + "\"}";
    }

    @Transactional
    public List<Cooperative> approveApplication(Integer cooperativeId, Long userId, String notes) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        cooperative.setEmployee(user);
        cooperative.setNotes(notes);
        cooperative.setStatus("approved");
        cooperativeRepository.save(cooperative);
        user.approveApplication(cooperative);
        userRepository.save(user);
        return getProcessedApplications(userId);
    }

    @Transactional
    public List<Cooperative> rejectApplication(Integer cooperativeId, Long userId, String notes) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        cooperative.setEmployee(user);
        cooperative.setNotes(notes);
        cooperative.setStatus("rejected");
        cooperativeRepository.save(cooperative);
        user.rejectApplication(cooperative);
        userRepository.save(user);
        return getProcessedApplications(userId);
    }

    @Transactional
    public List<User> addFarmer(Integer cooperativeId, Long userId) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        User user = userService.getUser(userId);
        cooperative.addFarmer(user);
        user.addCooperative(cooperative);
        userService.saveUser(user);
        cooperativeRepository.save(cooperative);
        return cooperative.getFarmers();
    }

    @Transactional
    public List<Product> addProduct(Integer cooperativeId, Integer productId) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        Product product = productService.getProduct(productId);
        cooperative.addProduct(product);
        product.addCooperative(cooperative);
        productService.saveProduct(product);
        cooperativeRepository.save(cooperative);
        return cooperative.getProducts();
    }

    @Transactional
    public List<CultivationLocation> addCultivationLocation(Integer cooperativeId, Integer cultivationLocationId) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        CultivationLocation cultivationLocation = cultivationLocationService.getCultivationLocation(cultivationLocationId);
        cooperative.addCultivationLocation(cultivationLocation);
        cultivationLocation.addCooperative(cooperative);
        cultivationLocationService.saveCultivationLocation(cultivationLocation);
        cooperativeRepository.save(cooperative);
        return cooperative.getCultivationLocations();
    }
}
