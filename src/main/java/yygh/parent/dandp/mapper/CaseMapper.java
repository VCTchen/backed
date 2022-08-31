package yygh.parent.dandp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import yygh.parent.dandp.entity.Case;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yygh.parent.dandp.utils.QueryRequest;

/**
 * 病例表 Mapper 接口
 */
public interface CaseMapper extends BaseMapper<Case> {

    IPage<Case> queryList(@Param("page") IPage<Case> page, @Param("queryRequest") QueryRequest queryRequest, @Param("entity") Case entity);

    IPage<Case> queryListByPatient(@Param("page") IPage<Case> page, @Param("queryRequest") QueryRequest queryRequest, @Param("entity") Case entity, @Param("userId") Integer userId);
}
