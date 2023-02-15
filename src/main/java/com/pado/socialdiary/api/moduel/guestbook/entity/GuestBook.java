package com.pado.socialdiary.api.moduel.guestbook.entity;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GuestBook {

    @Id
    private Integer guestBookId;
    private Integer hostMemberId;
    private Integer guestMemberId;
    private String contents;
    private Integer regId;
    private LocalDateTime regDt;
    private Integer updId;
    private LocalDateTime updDt;

    @Builder
    public GuestBook(Integer guestBookId, Integer hostMemberId, Integer guestMemberId, String contents) {

        this.guestBookId = guestBookId;
        this.hostMemberId = hostMemberId;
        this.guestMemberId = guestMemberId;
        this.contents = contents;

        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }
}
