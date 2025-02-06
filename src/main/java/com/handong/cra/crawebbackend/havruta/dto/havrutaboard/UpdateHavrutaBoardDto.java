package com.handong.cra.crawebbackend.havruta.dto.havrutaboard;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.board.dto.UpdateBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.request.ReqUpdateHavrutaBoardDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateHavrutaBoardDto extends UpdateBoardDto {

    private Long havrutaId;
    private String className;
    private String professor;

    public UpdateHavrutaBoardDto(Long userId, Long boardId, ReqUpdateHavrutaBoardDto reqUpdateHavrutaBoardDto, List<MultipartFile> files) {
        super(userId, boardId, reqUpdateHavrutaBoardDto, files);
        this.havrutaId = reqUpdateHavrutaBoardDto.getHavrutaId();
    }

    public UpdateHavrutaBoardDto(Board board) {
        super(board);
        this.havrutaId = board.getHavruta().getId();
        this.className = board.getHavruta().getClassName();
        this.professor = board.getHavruta().getProfessor();
    }

    public static UpdateHavrutaBoardDto of(Long userId, Long boardId, ReqUpdateHavrutaBoardDto reqUpdateHavrutaBoardDto, List<MultipartFile> files) {
        return new UpdateHavrutaBoardDto(userId, boardId, reqUpdateHavrutaBoardDto, files);
    }

    public static UpdateHavrutaBoardDto from(Board board) {
        return new UpdateHavrutaBoardDto(board);
    }

}
