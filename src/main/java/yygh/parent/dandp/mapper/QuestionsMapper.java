package yygh.parent.dandp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import yygh.parent.dandp.entity.Questions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yygh.parent.dandp.utils.QueryRequest;

/**
 * 问答表 Mapper 接口
 */
public interface QuestionsMapper extends BaseMapper<Questions> {

    IPage<Questions> queryList(@Param("page") IPage<Questions> page, @Param("queryRequest") QueryRequest queryRequest, @Param("questions") Questions questions);

    IPage<Questions> queryListByPatient(@Param("page") IPage<Questions> page, @Param("queryRequest") QueryRequest queryRequest, @Param("questions") Questions questions, @Param("userId") Integer userId);
}
