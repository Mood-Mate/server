package com.pado.socialdiary.api.guest_book.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GuestBook {
    private int GuestBookId;
    private String MemberId;
    private String Cn; //내용
    private LocalDateTime regDt; //등록일

    private LocalDateTime updDt; //수정일


    @Builder
    public GuestBook(int GuestBookId, String Member_id, String Cn) {
        this.GuestBookId = GuestBookId;
        this.MemberId = MemberId;
        this.Cn = Cn;

        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }
}

