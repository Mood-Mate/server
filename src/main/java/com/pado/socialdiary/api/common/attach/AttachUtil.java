package com.pado.socialdiary.api.common.attach;

import com.pado.socialdiary.api.common.attach.dto.AttachDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class AttachUtil {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public String getFullPath(String attachPath, String fileName) {
        return fileDir + attachPath + fileName;
    }

    public AttachDto.UploadRequest attachedFile(String attachPath, MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String attachedFileName = createAttachedFilename(originalFileName);
        long filesize = multipartFile.getSize();

        if (StringUtils.hasText(attachPath)) {
            multipartFile.transferTo(new File(getFullPath(attachPath, attachedFileName)));
        } else {
            multipartFile.transferTo(new File(getFullPath(attachedFileName)));
        }

        return new AttachDto.UploadRequest(originalFileName, attachedFileName, attachPath, filesize);
    }

    private String createAttachedFilename(String originalFileName) {
        String ext = subStringExt(originalFileName);
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }

    private String subStringExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }
}
