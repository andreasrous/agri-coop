package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.repository.CultivationLocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CultivationLocationService {

    @Autowired
    private CultivationLocationRepository cultivationLocationRepository;

    @Transactional
    public List<CultivationLocation> getCultivationLocations() {
        return cultivationLocationRepository.findAll();
    }

    @Transactional
    public CultivationLocation editCultivationLocation(Integer cultivationLocationId, CultivationLocation updatedCultivationLocation) {
        CultivationLocation existingCultivationLocation = cultivationLocationRepository.findById(cultivationLocationId)
                .orElseThrow(() -> new ResourceNotFoundException("CultivationLocation not found with id: " + cultivationLocationId));
        existingCultivationLocation.setAddress(updatedCultivationLocation.getAddress());
        existingCultivationLocation.setArea(updatedCultivationLocation.getArea());
        existingCultivationLocation.setZipCode(updatedCultivationLocation.getZipCode());
        existingCultivationLocation.setCooperative(updatedCultivationLocation.getCooperative());

        return cultivationLocationRepository.save(existingCultivationLocation);
    }

    @Transactional
    public CultivationLocation saveCultivationLocation(CultivationLocation cultivationLocation) {
        cultivationLocationRepository.save(cultivationLocation);
        return cultivationLocation;
    }

    @Transactional
    public void deleteCultivationLocation(Integer cultivationLocationId) {
        cultivationLocationRepository.deleteById(cultivationLocationId);
    }

    @Transactional
    public CultivationLocation getCultivationLocation(Integer cultivationLocationId) {
        return cultivationLocationRepository.findById(cultivationLocationId).get();
    }

    @Transactional
    public List<CultivationLocation> getCultivationLocationsWithoutCooperative() {
        List<CultivationLocation> cultivationLocations = cultivationLocationRepository.findAll();
        cultivationLocations.removeIf(cultivationLocation -> cultivationLocation.getCooperative() != null);
        return cultivationLocations;
    }
}
