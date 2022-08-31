package yygh.parent.dandp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import yygh.parent.dandp.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import yygh.parent.dandp.utils.QueryRequest;

/**
 * 医院科室表 服务类
 */
public interface IDepartmentService extends IService<Department> {

    IPage<Department> queryList(Department entity, QueryRequest queryRequest);

}
