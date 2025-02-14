package com.api.gymapi.controller;

import com.api.gymapi.Dtos.PackDto;
import com.api.gymapi.models.Pack;
import com.api.gymapi.service.Packservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/packs")
public class PackController {
    @Autowired
    private Packservice packservice;

    // Ajouter un pack
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Pack> addPack(@RequestBody PackDto pack) {
        Pack newPack = packservice.addPack(pack);
        return new ResponseEntity<>(newPack, HttpStatus.CREATED);
    }

    // Récupérer tous les packs
    @GetMapping("/getall")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Pack>> getAllPacks() {
        List<Pack> packs = packservice.getAllPacks();
        return ResponseEntity.ok(packs);
    }

    // Récupérer un pack par ID
    @GetMapping("/get/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Pack> getPackById(@PathVariable long id) {
        Pack pack = packservice.getOnePack(id);  // Utilise getOnePack() qui lève une exception si non trouvé
        return ResponseEntity.ok(pack);
    }

    // Modifier un pack
    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Pack> updatePack(@PathVariable long id, @RequestBody PackDto packDto) {
        Pack updatedPack = packservice.updatePack(id, packDto);
        return ResponseEntity.ok(updatedPack);
    }

    // Supprimer un pack
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePack(@PathVariable long id) {
        packservice.deletePack(id); // Lève une exception si le pack n'existe pas
        return ResponseEntity.noContent().build(); // 204 No Content (convention REST)
    }
}
