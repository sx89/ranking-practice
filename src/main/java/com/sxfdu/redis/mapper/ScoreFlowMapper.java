package com.sxfdu.redis.mapper;

import com.sxfdu.redis.domain.ScoreFlow;
import com.sxfdu.redis.domain.ScoreFlowExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface ScoreFlowMapper {
    @SelectProvider(type=ScoreFlowSqlProvider.class, method="countByExample")
    int countByExample(ScoreFlowExample example);

    @DeleteProvider(type=ScoreFlowSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScoreFlowExample example);

    @Delete({
        "delete from score_flow",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into score_flow (score, user_id, ",
        "user_name)",
        "values (#{score,jdbcType=BIGINT}, #{userId,jdbcType=INTEGER}, ",
        "#{userName,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(ScoreFlow record);

    @InsertProvider(type=ScoreFlowSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(ScoreFlow record);

    @SelectProvider(type=ScoreFlowSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="score", property="score", jdbcType= JdbcType.BIGINT),
        @Result(column="user_id", property="userId", jdbcType= JdbcType.INTEGER),
        @Result(column="user_name", property="userName", jdbcType= JdbcType.VARCHAR)
    })
    List<ScoreFlow> selectByExample(ScoreFlowExample example);

    @Select({
        "select",
        "id, score, user_id, user_name",
        "from score_flow",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="score", property="score", jdbcType= JdbcType.BIGINT),
        @Result(column="user_id", property="userId", jdbcType= JdbcType.INTEGER),
        @Result(column="user_name", property="userName", jdbcType= JdbcType.VARCHAR)
    })
    ScoreFlow selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ScoreFlowSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScoreFlow record, @Param("example") ScoreFlowExample example);

    @UpdateProvider(type=ScoreFlowSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScoreFlow record, @Param("example") ScoreFlowExample example);

    @UpdateProvider(type=ScoreFlowSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScoreFlow record);

    @Update({
        "update score_flow",
        "set score = #{score,jdbcType=BIGINT},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "user_name = #{userName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ScoreFlow record);
}