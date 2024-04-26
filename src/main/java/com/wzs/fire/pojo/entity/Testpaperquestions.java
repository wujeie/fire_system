package com.wzs.fire.pojo.entity;

import java.io.Serializable;

public class Testpaperquestions implements Serializable {
    private Integer testpaperquestionsId;

    private Integer testpaperId;

    private Integer questionId;

    private Integer questionOrder;

    private static final long serialVersionUID = 1L;

    public Integer getTestpaperquestionsId() {
        return testpaperquestionsId;
    }

    public void setTestpaperquestionsId(Integer testpaperquestionsId) {
        this.testpaperquestionsId = testpaperquestionsId;
    }

    public Integer getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(Integer testpaperId) {
        this.testpaperId = testpaperId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", testpaperquestionsId=").append(testpaperquestionsId);
        sb.append(", testpaperId=").append(testpaperId);
        sb.append(", questionId=").append(questionId);
        sb.append(", questionOrder=").append(questionOrder);
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
        Testpaperquestions other = (Testpaperquestions) that;
        return (this.getTestpaperquestionsId() == null ? other.getTestpaperquestionsId() == null : this.getTestpaperquestionsId().equals(other.getTestpaperquestionsId()))
            && (this.getTestpaperId() == null ? other.getTestpaperId() == null : this.getTestpaperId().equals(other.getTestpaperId()))
            && (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
            && (this.getQuestionOrder() == null ? other.getQuestionOrder() == null : this.getQuestionOrder().equals(other.getQuestionOrder()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTestpaperquestionsId() == null) ? 0 : getTestpaperquestionsId().hashCode());
        result = prime * result + ((getTestpaperId() == null) ? 0 : getTestpaperId().hashCode());
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getQuestionOrder() == null) ? 0 : getQuestionOrder().hashCode());
        return result;
    }
}