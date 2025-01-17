package com.handong.cra.crawebbackend.havruta.dto.havrutaboard.response;

import com.handong.cra.crawebbackend.board.domain.Category;
import com.handong.cra.crawebbackend.board.dto.response.ResCreateBoardDto;
import com.handong.cra.crawebbackend.havruta.dto.havrutaboard.CreateHavrutaBoardDto;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResCreateHavrutaBoardDto extends ResCreateBoardDto {

    private Long havrutaId;
    private String className;
    private String professor;

    public ResCreateHavrutaBoardDto(CreateHavrutaBoardDto createHavrutaBoardDto) {
        super(createHavrutaBoardDto);
        this.havrutaId = createHavrutaBoardDto.getHavrutaId();
        this.className = createHavrutaBoardDto.getClassName();
        this.professor = createHavrutaBoardDto.getProfessor();
    }

    public static ResCreateHavrutaBoardDto from(CreateHavrutaBoardDto createHavrutaBoardDto){
        return new ResCreateHavrutaBoardDto(createHavrutaBoardDto);
    }
}
