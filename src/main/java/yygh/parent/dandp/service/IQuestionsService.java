package yygh.parent.dandp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import yygh.parent.dandp.entity.Questions;
import com.baomidou.mybatisplus.extension.service.IService;
import yygh.parent.dandp.utils.QueryRequest;

/**
 * 问答表 服务类
 */
public interface IQuestionsService extends IService<Questions> {

    IPage<Questions> queryList(QueryRequest queryRequest, Questions questions);

    IPage<Questions> queryListByPatient(QueryRequest queryRequest, Questions questions, Integer userId);
}
