package com.open.mscachemanager.adpters.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.mscachemanager.adpters.presentation.enums.CacheAction;
import com.open.mscachemanager.adpters.presentation.response.CacheStatusResponse;
import com.open.mscachemanager.adpters.presentation.validation.CacheValidation;
import com.open.mscachemanager.domain.entities.Cache;
import com.open.mscachemanager.domain.exception.CException;
import com.open.mscachemanager.domain.service.CacheService;
import com.open.mscachemanager.jedis.JedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.*;

@Service
public class CacheServiceImpl implements CacheService {

    private ObjectMapper mapper;

    public CacheServiceImpl() {
        this.mapper = new ObjectMapper();
    }

    @Async
    @Override
    public void createAsyncMode(Cache cache) throws JsonProcessingException {
        try {

            String valueString = mapper.writeValueAsString(cache);

            JedisPool pool = JedisTemplate.getInstance();

            byte[] key = cache.getKey().getBytes();
            byte[] value = valueString.getBytes();

            try (Jedis jedis = pool.getResource()) {
                jedis.setex(key, cache.getTtl(), value);
                return;
            }
        } catch (JsonProcessingException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Async
    @Override
    public void deleteAsyncMode(String keyString) {
        try {
            byte[] key = keyString.getBytes();
            JedisPool pool = JedisTemplate.getInstance();
            try (Jedis jedis = pool.getResource()) {
                jedis.getDel(key);
                return;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Async
    @Override
    public void updateAsyncMode(Cache cache) throws IOException {
        try {

            JedisPool pool = JedisTemplate.getInstance();

            byte[] key = cache.getKey().getBytes();

            try (Jedis jedis = pool.getResource()) {
                Long ttl = cache.getTtl();

                if (ttl == null) {
                    ttl = jedis.ttl(key);
                    Map cacheFounded = findByKey(cache.getKey(), new ArrayList<>());
                    cache.setTtl((Long) cacheFounded.get("current_ttl"));
                }

                String valueString = mapper.writeValueAsString(cache);
                byte[] value = valueString.getBytes();

                jedis.setex(key, ttl, value);
                return;
            }
        } catch (JsonProcessingException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CacheStatusResponse create(Cache cache) throws JsonProcessingException {
        try {

            String valueString = mapper.writeValueAsString(cache);

            JedisPool pool = JedisTemplate.getInstance();

            byte[] key = cache.getKey().getBytes();
            byte[] value = valueString.getBytes();

            try (Jedis jedis = pool.getResource()) {
                jedis.setex(key, cache.getTtl(), value);
                return new CacheStatusResponse(CacheAction.CREATED, true);
            }

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Cache createWithoutKey(Cache cache) throws JsonProcessingException {
        try {

            String valueString = mapper.writeValueAsString(cache);

            JedisPool pool = JedisTemplate.getInstance();

            byte[] key = cache.getKey().getBytes();
            byte[] value = valueString.getBytes();

            try (Jedis jedis = pool.getResource()) {
                jedis.setex(key, cache.getTtl(), value);
                return cache;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CacheStatusResponse update(Cache cache) throws IOException {
        try {

            JedisPool pool = JedisTemplate.getInstance();

            byte[] key = cache.getKey().getBytes();

            try (Jedis jedis = pool.getResource()) {
                Long ttl = cache.getTtl();

                if (ttl == null) {
                    ttl = jedis.ttl(key);
                    Map cacheFounded = findByKey(cache.getKey(), new ArrayList<>());
                    cache.setTtl((Long) cacheFounded.get("current_ttl"));
                }

                String valueString = mapper.writeValueAsString(cache);
                byte[] value = valueString.getBytes();

                jedis.setex(key, ttl, value);
                return new CacheStatusResponse(CacheAction.UPDATED, true);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CacheStatusResponse delete(String keyString) throws IOException {
        try {
            byte[] key = keyString.getBytes();

            JedisPool pool = JedisTemplate.getInstance();
            try (Jedis jedis = pool.getResource()) {
                byte[] value = jedis.get(key);

                if (value == null)
                    throw new CException(String.format("value not found with key '%s'", keyString));

                jedis.getDel(key);
                return new CacheStatusResponse(CacheAction.DELETED, true);
            }
        } catch (CException e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> findByKey(String keyString, List<String> responseModel) throws IOException {
        try {
            JedisPool pool = JedisTemplate.getInstance();

            byte[] key = keyString.getBytes();

            try (Jedis jedis = pool.getResource()) {
                byte[] value = jedis.get(key);

                if (value == null)
                    throw new CException(String.format("value not found with key '%s'", keyString));

                Map<String, Object> cacheFounded = mapper.readValue(value, Map.class);

                long currentTtl = jedis.ttl(key);

                cacheFounded.put("current_ttl", currentTtl);

                if (responseModel != null && responseModel.size() > 0) {
                    Map<String, Object> response = new HashMap<>();

                    for (String field : responseModel) {
                        field = field.toLowerCase();
                        if (CacheValidation.isCacheField(field)) {
                            response.put(field.toLowerCase(), cacheFounded.get(field));
                        }
                    }

                    return response;
                }

                return cacheFounded;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Set<String> findAll() {
        try {
            JedisPool pool = JedisTemplate.getInstance();
            try (Jedis jedis = pool.getResource()) {
                Set<String> keys = jedis.keys("*");

                return keys;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
