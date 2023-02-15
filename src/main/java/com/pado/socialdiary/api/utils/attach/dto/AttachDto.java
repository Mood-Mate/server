package com.pado.socialdiary.api.utils.attach.dto;

import lombok.Getter;

public class AttachDto {

    @Getter
    public static class UploadRequest {
        private String originalFileName;
        private String attachedFileName;
        private String attachedPath;
        private Long fileSize;

        public UploadRequest(String originalFileName, String attachedFileName, String attachedPath, Long fileSize) {
            this.originalFileName = originalFileName;
            this.attachedFileName = attachedFileName;
            this.attachedPath = attachedPath;
            this.fileSize = fileSize;
        }
    }

}
