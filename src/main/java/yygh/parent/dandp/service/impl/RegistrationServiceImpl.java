package yygh.parent.dandp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import yygh.parent.dandp.entity.Registration;
import yygh.parent.dandp.mapper.RegistrationMapper;
import yygh.parent.dandp.service.IRegistrationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yygh.parent.dandp.utils.QueryRequest;

import javax.annotation.Resource;

/**
 * 挂号表 服务实现类
 */
@Service
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements IRegistrationService {

    @Resource
    private RegistrationMapper mapper;

    @Override
    public Integer selectByNow(Integer depId) {
        return mapper.selectByNow(depId);
    }

    @Override
    public IPage<Registration> queryList(Registration entity, QueryRequest queryRequest) {
        IPage<Registration> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(), true);
        return mapper.queryList(page, queryRequest, entity);
    }

    @Override
    public IPage<Registration> queryListByDoctor(Registration entity, QueryRequest queryRequest, Integer userId) {
        IPage<Registration> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(), true);
        return mapper.queryListByDoctor(page, queryRequest, entity, userId);
    }

    @Override
    public IPage<Registration> queryListByPatient(Registration entity, QueryRequest queryRequest, Integer userId) {
        IPage<Registration> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(), true);
        return mapper.queryListByPatient(page, queryRequest, entity, userId);
    }
}
