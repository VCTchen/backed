package yygh.parent.dandp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import yygh.parent.dandp.entity.Registration;
import com.baomidou.mybatisplus.extension.service.IService;
import yygh.parent.dandp.utils.QueryRequest;

/**
 * 挂号表 服务类
 */
public interface IRegistrationService extends IService<Registration> {

    Integer selectByNow(Integer depId);

    IPage<Registration> queryList(Registration entity, QueryRequest queryRequest);

    IPage<Registration> queryListByDoctor(Registration entity, QueryRequest queryRequest, Integer userId);

    IPage<Registration> queryListByPatient(Registration entity, QueryRequest queryRequest, Integer userId);

}
