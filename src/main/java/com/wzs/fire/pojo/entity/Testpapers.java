package com.wzs.fire.pojo.entity;

import java.io.Serializable;
import java.util.Date;

public class Testpapers implements Serializable {
    private Integer testpaperId;

    private String testpaperName;

    private Date createtime;

    private String totalscore;

    private static final long serialVersionUID = 1L;

    public Integer getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(Integer testpaperId) {
        this.testpaperId = testpaperId;
    }

    public String getTestpaperName() {
        return testpaperName;
    }

    public void setTestpaperName(String testpaperName) {
        this.testpaperName = testpaperName;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(String totalscore) {
        this.totalscore = totalscore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", testpaperId=").append(testpaperId);
        sb.append(", testpaperName=").append(testpaperName);
        sb.append(", createtime=").append(createtime);
        sb.append(", totalscore=").append(totalscore);
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
        Testpapers other = (Testpapers) that;
        return (this.getTestpaperId() == null ? other.getTestpaperId() == null : this.getTestpaperId().equals(other.getTestpaperId()))
            && (this.getTestpaperName() == null ? other.getTestpaperName() == null : this.getTestpaperName().equals(other.getTestpaperName()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getTotalscore() == null ? other.getTotalscore() == null : this.getTotalscore().equals(other.getTotalscore()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTestpaperId() == null) ? 0 : getTestpaperId().hashCode());
        result = prime * result + ((getTestpaperName() == null) ? 0 : getTestpaperName().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getTotalscore() == null) ? 0 : getTotalscore().hashCode());
        return result;
    }
}