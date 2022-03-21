package com.open.mscachemanager.adpters.presentation.response;

import com.open.mscachemanager.domain.entities.Cache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CacheDetailsResponse {

    private String key;

    private HashMap<?, ?> content;

    private long ttl;

    private long currentTtl;

    public CacheDetailsResponse(Cache cache, long currentTtl) {
        setKey(cache.getKey());
        setTtl(cache.getTtl());
        setContent(cache.getContent());
        setCurrentTtl(currentTtl);
    }
}
