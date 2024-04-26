package com.wzs.fire.pojo.vo;

import com.wzs.fire.pojo.entity.Questions;
import com.wzs.fire.pojo.entity.Testpapers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPaperQuestionsVo {
    private Integer testpaperId;
    private String testpaperName;
    private String totalscore;
    private List<Questions> questionList = new ArrayList<>();
}
