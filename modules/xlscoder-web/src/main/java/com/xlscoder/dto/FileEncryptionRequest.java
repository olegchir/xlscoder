package com.xlscoder.dto;

import com.xlscoder.model.Key;

public class FileEncryptionRequest {
    private Key key;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }
}
