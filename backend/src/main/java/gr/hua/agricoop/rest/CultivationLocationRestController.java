package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.service.CultivationLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cultivation_location")
@CrossOrigin(origins = "http://localhost:8080/",
        methods = {RequestMethod.GET, RequestMethod.POST},
        allowedHeaders = {"*", "Content-Type"})
public class CultivationLocationRestController {

    @Autowired
    private CultivationLocationService cultivationLocationService;

    @GetMapping("")
    public List<CultivationLocation> showCultivationLocations() {
        return cultivationLocationService.getCultivationLocations();
    }

    // δεν κάνει τίποτα - το αντικείμενο δεν αποθηκεύεται στον πίνακα και άρα χάνεται (τζάμπα μέθοδος)
    @GetMapping("/new")
    public CultivationLocation addCultivationLocation() {
        return new CultivationLocation();
    }

    // αποθηκεύει το αντικείμενο στον πίνακα (με όλα τα πεδία null εκτός του id το οποίο γίνεται auto incremented)
    @PostMapping("/empty")
    public CultivationLocation addEmptyCultivationLocation() {
        return cultivationLocationService.saveCultivationLocation(new CultivationLocation());
    }

    @PutMapping("/{cultivation_location_id}")
    public ResponseEntity<CultivationLocation> editCultivationLocation(
            @PathVariable Integer cultivation_location_id,
            @RequestBody CultivationLocation updatedCultivationLocation) {
        CultivationLocation result = cultivationLocationService.editCultivationLocation(cultivation_location_id, updatedCultivationLocation);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/new")
    public List<CultivationLocation> saveCultivationLocation(@RequestBody CultivationLocation cultivationLocation) {
        cultivationLocationService.saveCultivationLocation(cultivationLocation);
        return cultivationLocationService.getCultivationLocations();
    }

    @DeleteMapping("{cultivation_location_id}")
    public List<CultivationLocation> deleteCultivationLocation(@PathVariable Integer cultivation_location_id) {
        cultivationLocationService.deleteCultivationLocation(cultivation_location_id);
        return cultivationLocationService.getCultivationLocations();
    }
}
