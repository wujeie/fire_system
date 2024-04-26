package com.wzs.fire.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestPaperQuestionsDto {
    private Integer testpaperId;
    private String[] answers;
}
