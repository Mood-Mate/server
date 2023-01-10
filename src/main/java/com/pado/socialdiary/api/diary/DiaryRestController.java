package com.pado.socialdiary.api.diary;

import com.pado.socialdiary.api.diary.dto.DiaryRequest;
import com.pado.socialdiary.api.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryRestController {

    private final DiaryService diaryService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody DiaryRequest diaryRequest){

        diaryService.create(diaryRequest);

        return ResponseEntity.ok()
            .build();
    }

    @PutMapping("/edit")
    public ResponseEntity editDiary(@RequestBody DiaryRequest diaryRequest){

        diaryService.editDiary(diaryRequest);

        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteDiary(){

        diaryService.deleteDiary();

        return ResponseEntity.ok()
            .build();
    }
}
