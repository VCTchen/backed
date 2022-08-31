package yygh.parent.dandp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yygh.parent.dandp.entity.Case;
import yygh.parent.dandp.mapper.CaseMapper;
import yygh.parent.dandp.service.ICaseService;
import yygh.parent.dandp.utils.QueryRequest;

import javax.annotation.Resource;

/**
 * 病例表 服务实现类
 */
@Service
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements ICaseService {

    @Resource
    private CaseMapper mapper;

    @Override
    public IPage<Case> queryList(Case entity, QueryRequest queryRequest) {
        IPage<Case> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(), true);
        return mapper.queryList(page, queryRequest, entity);
    }

    @Override
    public IPage<Case> queryListByPatient(Case entity, QueryRequest queryRequest, Integer userId) {
        IPage<Case> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(), true);
        return mapper.queryListByPatient(page, queryRequest, entity, userId);
    }

}
