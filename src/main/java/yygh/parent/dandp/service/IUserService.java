package yygh.parent.dandp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import yygh.parent.dandp.entity.User;
import yygh.parent.dandp.utils.QueryRequest;

/**
 * 系统用户 服务类
 */
public interface IUserService extends IService<User> {

    User selectByUsername(String username);

    IPage<User> queryFuzz(QueryRequest queryRequest, User user);

    IPage<User> queryDoctorFuzz(QueryRequest queryRequest, User user);

    IPage<User> queryPatiensFuzz(QueryRequest queryRequest, User user);

    User getDoctorInfoByUsername(String username);
}
