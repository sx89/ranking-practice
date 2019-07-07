package com.sxfdu.redis.domain;

/**
 * @author sunxu93@163.com
 * @date 19/7/5/005 17:08
 */
public class UserScore {
    private Integer id;

    private Integer userId;

    private Long userScore;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getUserScore() {
        return userScore;
    }

    public void setUserScore(Long userScore) {
        this.userScore = userScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", userScore=").append(userScore);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }


    public UserScore(Integer userId, Long userScore, String name) {
        this.userId = userId;
        this.userScore = userScore;
        this.name = name;
    }

    public UserScore(){

    }
}
