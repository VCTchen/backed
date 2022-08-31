package yygh.parent.dandp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import yygh.parent.dandp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yygh.parent.dandp.utils.QueryRequest;

/**
 * 系统用户 Mapper 接口
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> queryDoctorFuzz(IPage<User> userIPage, @Param("queryRequest") QueryRequest queryRequest, @Param("user") User user);

    User getDoctorInfoByUsername(@Param("username") String username);

}
