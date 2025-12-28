package com.handong.cra.crawebbackend.account.dto;

import com.handong.cra.crawebbackend.account.dto.req.ReqFindUsernameDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindUsernameDto {
    private String name;
    private String email;
    private String studentId;

    public static FindUsernameDto from(ReqFindUsernameDto reqFindUsernameDto) {
        return new FindUsernameDto(
                reqFindUsernameDto.getName(),
                reqFindUsernameDto.getEmail(),
                reqFindUsernameDto.getStudentId()
        );
    }
}
