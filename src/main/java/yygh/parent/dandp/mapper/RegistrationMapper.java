package yygh.parent.dandp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import yygh.parent.dandp.entity.Registration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yygh.parent.dandp.utils.QueryRequest;

/**
 * 挂号表 Mapper 接口
 */
public interface RegistrationMapper extends BaseMapper<Registration> {

    Integer selectByNow(@Param("depId") Integer depId);

    IPage<Registration> queryList(@Param("page") IPage<Registration> page, @Param("queryRequest") QueryRequest queryRequest, @Param("entity") Registration entity);

    IPage<Registration> queryListByDoctor(@Param("page") IPage<Registration> page, @Param("queryRequest") QueryRequest queryRequest, @Param("entity") Registration entity, @Param("userId") Integer userId);

    IPage<Registration> queryListByPatient(@Param("page") IPage<Registration> page, @Param("queryRequest") QueryRequest queryRequest, @Param("entity") Registration entity, @Param("userId") Integer userId);


}
