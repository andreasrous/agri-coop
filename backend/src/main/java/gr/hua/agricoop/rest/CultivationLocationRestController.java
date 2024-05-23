package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.service.CultivationLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cultivation-location")
public class CultivationLocationRestController {

    @Autowired
    private CultivationLocationService cultivationLocationService;

    @GetMapping("")
    public List<CultivationLocation> getCultivationLocations() {
        return cultivationLocationService.getCultivationLocations();
    }

    @PostMapping("/new")
    public List<CultivationLocation> addCultivationLocation(@RequestBody CultivationLocation cultivationLocation) {
        cultivationLocationService.saveCultivationLocation(cultivationLocation);
        return cultivationLocationService.getCultivationLocations();
    }

    @PutMapping("/{cultivation_location_id}")
    public ResponseEntity<CultivationLocation> editCultivationLocation(@PathVariable Integer cultivation_location_id, @RequestBody CultivationLocation updatedCultivationLocation) {
        CultivationLocation result = cultivationLocationService.editCultivationLocation(cultivation_location_id, updatedCultivationLocation);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{cultivation_location_id}")
    public List<CultivationLocation> deleteCultivationLocation(@PathVariable Integer cultivation_location_id) {
        cultivationLocationService.deleteCultivationLocation(cultivation_location_id);
        return cultivationLocationService.getCultivationLocations();
    }
}
