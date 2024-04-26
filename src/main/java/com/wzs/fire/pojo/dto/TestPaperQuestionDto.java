package com.wzs.fire.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPaperQuestionDto {
    private Integer questionId;
    private List<Integer> testpaperIds;
}
