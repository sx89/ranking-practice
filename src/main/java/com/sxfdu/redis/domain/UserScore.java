package com.sxfdu.redis.domain;

/**
 * @author sunxu93@163.com
 * @date 19/7/5/005 17:08
 */
public class UserScore {
    private Integer id;
    private String userName;
    private Integer userId;
    private Long score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
    public UserScore() {
    }

    @Override
    public String toString() {
        return "UserScore{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                ", score=" + score +
                '}';
    }

    public UserScore(Integer id, String userName, Integer userId, Long score) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.score = score;
    }
}
