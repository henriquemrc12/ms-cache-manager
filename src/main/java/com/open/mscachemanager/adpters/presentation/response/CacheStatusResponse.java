package com.open.mscachemanager.adpters.presentation.response;

import com.open.mscachemanager.adpters.presentation.enums.CacheAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheStatusResponse {

    private CacheAction action;

    private Boolean success;
}
