package me.leeminsoo.usedpark.admin.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddBoardRequestDTO {
    @Size(max = 10)
    private String name;
}
