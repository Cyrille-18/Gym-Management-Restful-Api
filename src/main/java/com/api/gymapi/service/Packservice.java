package com.api.gymapi.service;

import com.api.gymapi.Dtos.PackDto;
import com.api.gymapi.models.Pack;
import com.api.gymapi.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Packservice implements IPackService {
    @Autowired
    private PackRepository packRepository;

    @Override
    public List<Pack> getAllPacks() {
        return packRepository.findAll();
    }

    @Override
    public Pack getOnePack(long packId) {
        return packRepository.findById(packId)
                .orElseThrow(() -> new RuntimeException("Pack non trouvé avec l'ID : " + packId));
    }

    @Override
    public Pack addPack(PackDto packDto) {
        Pack pack = new Pack();
        pack.setPackName(packDto.getPackName());
        pack.setPackDescription(packDto.getPackDescription());
        pack.setDurationMonths(packDto.getDurationMonths());
        pack.setMonthlyPrice(packDto.getMonthlyPrice());

        return packRepository.save(pack);
    }

    @Override
    public boolean deletePack(long id) {
        if (!packRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : Pack introuvable avec l'ID : " + id);
        }
        packRepository.deleteById(id);
        return true;
    }

    @Override
    public Pack updatePack(long id, PackDto packDto) {
        Pack existingPack = packRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack non trouvé avec l'ID : " + id));

        // Mise à jour des champs
        existingPack.setPackName(packDto.getPackName());
        existingPack.setPackDescription(packDto.getPackDescription());
        existingPack.setDurationMonths(packDto.getDurationMonths());
        existingPack.setMonthlyPrice(packDto.getMonthlyPrice());

        return packRepository.save(existingPack);
    }
}
