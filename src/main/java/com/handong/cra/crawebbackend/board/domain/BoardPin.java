package com.handong.cra.crawebbackend.board.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class BoardPin extends BaseEntity {

    @Column(nullable = false)
    private Category category;

        @OneToOne
//    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", nullable = false, unique = false)
    private Board board;

    public static BoardPin of(Board board) {
        return builder()
                .board(board)
                .category(board.getCategory())
                .build();
    }
}
