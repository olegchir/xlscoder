package com.xlscoder.dto;

import com.xlscoder.model.Key;
import org.springframework.web.multipart.MultipartFile;

public class FileEncryptionRequest {
    private Key key;
    private MultipartFile file;
    private String filter;
    private Boolean decrypt;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Boolean getDecrypt() {
        return decrypt;
    }

    public void setDecrypt(Boolean decrypt) {
        this.decrypt = decrypt;
    }
}
