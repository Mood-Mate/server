package com.pado.socialdiary.api.guest_book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GusetBookCommentRequest {
    private int GuestBookId;
    private String Member_Id;
    private String Cn; //내용
    private LocalDateTime regDt; //등록일


}
