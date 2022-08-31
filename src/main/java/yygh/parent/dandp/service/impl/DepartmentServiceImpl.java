package yygh.parent.dandp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import yygh.parent.dandp.entity.Department;
import yygh.parent.dandp.mapper.DepartmentMapper;
import yygh.parent.dandp.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yygh.parent.dandp.utils.QueryRequest;

import javax.annotation.Resource;

/**
 * 医院科室表 服务实现类
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {


    @Resource
    private DepartmentMapper mapper;

    @Override
    public IPage<Department> queryList(Department entity, QueryRequest queryRequest) {
        IPage<Department> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(), true);
        LambdaQueryWrapper<Department> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(entity.getLetter())) {
            lambdaQueryWrapper.like(Department::getLetter, entity.getLetter());
        }
        if (StringUtils.isNotEmpty(entity.getDepName())) {
            lambdaQueryWrapper.like(Department::getDepName, entity.getDepName());
        }
        lambdaQueryWrapper.orderByDesc(Department::getCreateTime);
        return mapper.selectPage(page, lambdaQueryWrapper);
    }
}
