package com.open.mscachemanager.adpters.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.open.mscachemanager.adpters.presentation.mapper.CacheMapper;
import com.open.mscachemanager.adpters.presentation.request.CacheCreateRequest;
import com.open.mscachemanager.adpters.presentation.request.CacheDeleteRequest;
import com.open.mscachemanager.adpters.presentation.request.CacheUpdateRequest;
import com.open.mscachemanager.adpters.presentation.validation.CacheValidation;
import com.open.mscachemanager.domain.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CacheCreateRequest request) throws JsonProcessingException, InterruptedException {
        CacheValidation.validate(request);

        if (request.getKey() != null ) {
            if (request.getIsAsync()) {
                service.createAsyncMode(CacheMapper.map(request));
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return ResponseEntity.ok().body(service.create(CacheMapper.map(request)));
        }

        return ResponseEntity.ok().body(service.createWithoutKey(CacheMapper.map(request)));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody CacheUpdateRequest request) throws IOException {
        CacheValidation.validate(request);

        if(request.getIsAsync()) {
            service.updateAsyncMode(CacheMapper.map(request));
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return ResponseEntity.ok().body(service.update(CacheMapper.map(request)));
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> findByKey(
            @PathVariable String key,
            @RequestParam(value = "response-model", required = false) List<String> responseModel) throws IOException {
        return ResponseEntity.ok().body(service.findByKey(key, responseModel));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody CacheDeleteRequest request) throws IOException {
        CacheValidation.validate(request);

        if(request.getIsAsync()) {
            service.deleteAsyncMode(request.getKey());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(service.delete(request.getKey()));
    }
}
