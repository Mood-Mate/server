package com.pado.socialdiary.api.common.attach.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attached {

    private Integer attachedId;
    private String refTable;
    private Integer refId;

    private String originalFilename;
    private String attachedFilename;
    private String attachedPath;
    private Long fileSize;

    @Builder
    public Attached(String refTable, Integer refId, String originalFilename, String attachedFilename, String attachedPath, Long fileSize) {
        this.refTable = refTable;
        this.refId = refId;
        this.originalFilename = originalFilename;
        this.attachedFilename = attachedFilename;
        this.attachedPath = attachedPath;
        this.fileSize = fileSize;
    }
}
