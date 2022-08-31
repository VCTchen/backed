package yygh.parent.dandp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import yygh.parent.dandp.entity.Questions;
import yygh.parent.dandp.mapper.QuestionsMapper;
import yygh.parent.dandp.service.IQuestionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yygh.parent.dandp.utils.QueryRequest;

import javax.annotation.Resource;

/**
 * 问答表 服务实现类
 */
@Service
public class QuestionsServiceImpl extends ServiceImpl<QuestionsMapper, Questions> implements IQuestionsService {

    @Resource
    private QuestionsMapper mapper;

    @Override
    public IPage<Questions> queryList(QueryRequest queryRequest, Questions questions) {
        IPage<Questions> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(), true);
        return mapper.queryList(page, queryRequest, questions);
    }

    @Override
    public IPage<Questions> queryListByPatient(QueryRequest queryRequest, Questions questions, Integer userId) {
        IPage<Questions> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(), true);
        return mapper.queryListByPatient(page, queryRequest, questions,userId);
    }

}
