package com.pado.socialdiary.api.common;

import com.pado.socialdiary.api.constants.AttachPath;
import com.pado.socialdiary.api.utils.attach.AttachUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class CommonController {

    private final AttachUtil attachUtil;

    /**
     * 회원 프로필
     * @param filename
     * @return
     * @throws MalformedURLException
     */
    @ResponseBody
    @GetMapping("/resource/image/member/picture/{filename}")
    public Resource getMemberPicture(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file://" + attachUtil.getFullPath(AttachPath.MEMBER_PICTURE.getValue(), filename));
    }

    @ResponseBody
    @GetMapping("/resource/image/diary/picture/{filename}")
    public Resource getDiaryPicture(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file://" + attachUtil.getFullPath(AttachPath.DIARY_PICTURE.getValue(), filename));
    }
}
