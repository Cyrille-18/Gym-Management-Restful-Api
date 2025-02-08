package com.api.gymapi.service;

import com.api.gymapi.models.Pack;
import com.api.gymapi.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Packservice implements IPackService{
    @Autowired
    private PackRepository packRepository;

    @Override
    public List<Pack> getAllPacks() {
        return packRepository.findAll();
    }
    @Override
    public Pack getOnePack(long packid){
        return packRepository.findById(packid).get();
    }
    @Override
    public Pack savePack(Pack pack) {
        return packRepository.save(pack);
    }
    @Override
    public void deletePack(long packid) {
        packRepository.deleteById(packid);
    }
    @Override
    public Pack updatePack(Pack pack) {
        return packRepository.save(pack);
    }

}
