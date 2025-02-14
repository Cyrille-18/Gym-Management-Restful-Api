package com.api.gymapi.service;

import com.api.gymapi.Dtos.PackDto;
import com.api.gymapi.models.Pack;

import java.util.List;

public interface IPackService {
    public Pack addPack(PackDto pack);
    public Pack getOnePack(long packId);
    public List<Pack> getAllPacks();
    public boolean deletePack(long id);
    public Pack updatePack(long id, PackDto packDto);
}
