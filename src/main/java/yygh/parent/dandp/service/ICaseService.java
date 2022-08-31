package yygh.parent.dandp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import yygh.parent.dandp.entity.Case;
import com.baomidou.mybatisplus.extension.service.IService;
import yygh.parent.dandp.utils.QueryRequest;

/**
 * 病例表 服务类
 */
public interface ICaseService extends IService<Case> {

    IPage<Case> queryList(Case entity, QueryRequest queryRequest);

    IPage<Case> queryListByPatient(Case entity, QueryRequest queryRequest, Integer userId);
}
