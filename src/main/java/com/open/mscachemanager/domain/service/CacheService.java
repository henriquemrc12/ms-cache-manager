package com.open.mscachemanager.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.open.mscachemanager.adpters.presentation.response.CacheDetailsResponse;
import com.open.mscachemanager.adpters.presentation.response.CacheStatusResponse;
import com.open.mscachemanager.domain.entities.Cache;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheService {

    void createAsyncMode(Cache cache) throws JsonProcessingException;

    void deleteAsyncMode(String key) throws JsonProcessingException;

    void updateAsyncMode(Cache cache) throws IOException;

    Cache createWithoutKey(Cache cache) throws JsonProcessingException;

    CacheStatusResponse create(Cache cache) throws JsonProcessingException, InterruptedException;

    CacheStatusResponse update(Cache cache) throws IOException;

    CacheStatusResponse delete(String key) throws IOException;

    Map<String, Object> findByKey(String key, List<String> responseModel) throws IOException;

    Set<String> findAll();

}
