package com.pado.socialdiary.api.emoji;

import com.pado.socialdiary.api.emoji.dto.EmojiSympathyRequest;
import com.pado.socialdiary.api.emoji.mapper.EmojiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emoji")
public class EmojiRestController {

    private final EmojiMapper emojiMapper;

    @PostMapping
    public ResponseEntity createSympathy(EmojiSympathyRequest request) {

        emojiMapper.createSympathy(request);

        return ResponseEntity.ok()
                .build();
    }
}
