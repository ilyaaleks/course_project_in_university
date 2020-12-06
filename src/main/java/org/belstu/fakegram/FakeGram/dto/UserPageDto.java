package org.belstu.fakegram.FakeGram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserPageDto {
    private List<UserDto> users;
    private int currentPage;
    private int totalPage;
}
