package com.open.mscachemanager.adpters.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CacheDeleteRequest {

    private String key;

    private Boolean isAsync = Boolean.FALSE;
}
