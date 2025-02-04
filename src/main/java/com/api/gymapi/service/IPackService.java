package com.api.gymapi.service;

import com.api.gymapi.models.Pack;

import java.util.List;

public interface IPackService {
    public Pack savePack(Pack pack);
    public Pack getOnePack(long id);
    public List<Pack> getAllPacks();
    public void deletePack(long id);
    public Pack updatePack(Pack pack);
}
