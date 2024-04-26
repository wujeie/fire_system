package com.wzs.fire.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private String content;
    private Map<String, String> options = new HashMap<>();
    private String correctAnswer;
}

