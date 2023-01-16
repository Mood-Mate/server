package com.pado.socialdiary.api.diary;

import com.pado.socialdiary.api.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.diary.dto.DiarySearchRequest;
import com.pado.socialdiary.api.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.diary.entity.Diary;
import com.pado.socialdiary.api.diary.service.DiaryService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity create(@RequestBody DiaryCreateRequest diaryCreateRequest){

        diaryService.create(diaryCreateRequest);

        return ResponseEntity.ok()
            .build();
    }

    @GetMapping("")
    public ResponseEntity<List<Diary>> search(@RequestBody DiarySearchRequest diarySearchRequest){

        return ResponseEntity.ok(diaryService.search(diarySearchRequest));
    }

    @PutMapping("/edit")
    public ResponseEntity editDiary(@RequestBody DiaryUpdateRequest diaryUpdateRequest){

        diaryService.editDiary(diaryUpdateRequest);

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
