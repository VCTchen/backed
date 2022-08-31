package yygh.parent.dandp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yygh.parent.dandp.entity.Case;
import yygh.parent.dandp.entity.Registration;
import yygh.parent.dandp.service.ICaseService;
import yygh.parent.dandp.service.IRegistrationService;
import yygh.parent.dandp.utils.QueryRequest;
import yygh.parent.dandp.utils.ResultVo;
import yygh.parent.dandp.utils.TokenUtil;
import yygh.parent.dandp.utils.UserManager;

import javax.servlet.http.HttpServletRequest;

/**
 * 病例表 前端控制器
 */
@RestController
@RequestMapping("/adminApi/case")
public class CaseController {

    @Autowired
    private ICaseService caseService;

    @Autowired
    private IRegistrationService registrationService;

    @Autowired
    private UserManager userManager;

    /**
     * 开处方
     */
    @PostMapping("/makeCase")
    public ResultVo makeCase(@RequestBody Case entity) {
        Integer id = entity.getId();
        caseService.save(entity);
        // 修改挂号状态
        Registration registration = new Registration();
        registration.setStatus(1);
        registration.setId(id);
        registrationService.updateById(registration);

        return ResultVo.oK();
    }


    /**
     * 管理员或患者查询病例列表
     */
    @GetMapping("/list")
    public ResultVo list(Case entity, QueryRequest queryRequest, HttpServletRequest request) {
        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));

        if (userManager.isAdminRole(userId)) {
            return ResultVo.oK(caseService.queryList(entity, queryRequest));
        } else if (userManager.isPatientsRole(userId)) {
            return ResultVo.oK(caseService.queryListByPatient(entity, queryRequest, userId));
        }
        return null;

    }

}
