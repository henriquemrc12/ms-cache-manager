package com.open.mscachemanager.adpters.presentation.mapper;

import com.open.mscachemanager.adpters.presentation.request.CacheCreateRequest;
import com.open.mscachemanager.adpters.presentation.request.CacheUpdateRequest;
import com.open.mscachemanager.domain.entities.Cache;
import com.open.mscachemanager.utils.Generator;

public final class CacheMapper {

    public static Cache map(CacheCreateRequest request) {
        return Cache.builder()
                .key(request.getKey() == null ? Generator.generateUUID() : request.getKey())
                .ttl(request.getTtl() == null ? 2000L : request.getTtl())
                .content(request.getContent())
                .isAsync(request.getIsAsync())
                .build();
    }

    public static Cache map(CacheUpdateRequest request) {
        return Cache.builder()
                .key(request.getKey())
                .ttl(request.getTtl())
                .content(request.getContent())
                .isAsync(request.getIsAsync())
                .build();
    }
}
