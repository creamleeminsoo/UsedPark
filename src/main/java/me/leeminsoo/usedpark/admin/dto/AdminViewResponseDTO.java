package me.leeminsoo.usedpark.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdminViewResponseDTO {
    private long boardCount;
    private long postCount;
    private long commentCount;
    private long userCount;
    private long itemCount;
}
