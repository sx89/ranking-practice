package com.sxfdu.redis.domain;

/**
 * @author sunxu93@163.com
 * @date 19/7/5/005 17:06
 */

public class ScoreFlow {
    private Integer id;
    private Long score;
    private String userName;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ScoreFlow{" +
                "id=" + id +
                ", score=" + score +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }

    public ScoreFlow() {
    }

    public ScoreFlow( Long score, String userName, Integer userId) {
        this.score = score;
        this.userName = userName;
        this.userId = userId;
    }
}
