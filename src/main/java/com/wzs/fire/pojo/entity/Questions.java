package com.wzs.fire.pojo.entity;

import java.io.Serializable;

public class Questions implements Serializable {
    private Integer questionId;

    private String content;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String correctAnswer;

    private static final long serialVersionUID = 1L;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", questionId=").append(questionId);
        sb.append(", content=").append(content);
        sb.append(", optionA=").append(optionA);
        sb.append(", optionB=").append(optionB);
        sb.append(", optionC=").append(optionC);
        sb.append(", optionD=").append(optionD);
        sb.append(", correctAnswer=").append(correctAnswer);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Questions other = (Questions) that;
        return (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getOptionA() == null ? other.getOptionA() == null : this.getOptionA().equals(other.getOptionA()))
            && (this.getOptionB() == null ? other.getOptionB() == null : this.getOptionB().equals(other.getOptionB()))
            && (this.getOptionC() == null ? other.getOptionC() == null : this.getOptionC().equals(other.getOptionC()))
            && (this.getOptionD() == null ? other.getOptionD() == null : this.getOptionD().equals(other.getOptionD()))
            && (this.getCorrectAnswer() == null ? other.getCorrectAnswer() == null : this.getCorrectAnswer().equals(other.getCorrectAnswer()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getOptionA() == null) ? 0 : getOptionA().hashCode());
        result = prime * result + ((getOptionB() == null) ? 0 : getOptionB().hashCode());
        result = prime * result + ((getOptionC() == null) ? 0 : getOptionC().hashCode());
        result = prime * result + ((getOptionD() == null) ? 0 : getOptionD().hashCode());
        result = prime * result + ((getCorrectAnswer() == null) ? 0 : getCorrectAnswer().hashCode());
        return result;
    }
}