package com.pado.socialdiary.api.guest_book.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GuestBookComment {
    private int Guest_Book_Comment_Id;
    private String Guest_Book;
    private String Member_id;
    private String Cn; //내용
    private int Del_AT; //삭제여부?

    @Builder
    public GuestBookComment(int Guest_Book_Comment_Id, String Guesst_Book, String Member_id, String Cn, int Del_At) {
        this.Guest_Book_Comment_Id = Guest_Book_Comment_Id;
        this.Guest_Book = Guest_Book;
        this.Member_id = Member_id;
        this.Cn = Cn;
        this.Del_AT = Del_At;

    }

}
