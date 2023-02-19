package com.pado.socialdiary.api.utils.attach.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Attached {

    private Integer attachedId;
    private String refTable;
    private Integer refId;

    private String originalFilename;
    private String attachedFilename;
    private String attachedPath;
    private Long fileSize;

    private Integer regId;
    private LocalDateTime regDt;
    private Integer updId;
    private LocalDateTime updDt;

    @Builder
    public Attached(String refTable, Integer refId, String originalFilename, String attachedFilename, String attachedPath, Long fileSize, Integer regId, LocalDateTime regDt, Integer updId, LocalDateTime updDt) {
        this.refTable = refTable;
        this.refId = refId;
        this.originalFilename = originalFilename;
        this.attachedFilename = attachedFilename;
        this.attachedPath = attachedPath;
        this.fileSize = fileSize;

        this.regId = regId;
        this.regDt = regDt;
        this.updId = updId;
        this.updDt = updDt;
    }
}
