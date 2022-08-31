package yygh.parent.dandp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yygh.parent.dandp.entity.Questions;
import yygh.parent.dandp.service.IQuestionsService;
import yygh.parent.dandp.utils.QueryRequest;
import yygh.parent.dandp.utils.ResultVo;
import yygh.parent.dandp.utils.TokenUtil;
import yygh.parent.dandp.utils.UserManager;

import javax.servlet.http.HttpServletRequest;

/**
 * 问答表 前端控制器
 */
@RestController
@RequestMapping("/adminApi/questions")
public class QuestionsController {


    @Autowired
    private IQuestionsService questionsService;

    @Autowired
    private UserManager userManager;

    // 患者提问
    @PostMapping("/ask")
    public ResultVo ask(@RequestBody Questions questions, HttpServletRequest request) {
        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));
        questions.setPatientId(userId);
        questionsService.save(questions);
        return ResultVo.oK();
    }


    @GetMapping("/list")
    public ResultVo list(QueryRequest queryRequest, Questions questions, HttpServletRequest request) {
        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));
        if (userManager.isAdminRole(userId)) {
            return ResultVo.oK(questionsService.queryList(queryRequest, questions));
        } else if (userManager.isDoctorRole(userId)) {
            return ResultVo.oK(questionsService.queryList(queryRequest, questions));
        } else if (userManager.isPatientsRole(userId)) {
            return ResultVo.oK(questionsService.queryListByPatient(queryRequest, questions, userId));
        }
        return null;
    }

    @DeleteMapping("/{ids}")
    public ResultVo del(@PathVariable Integer[] ids) {
        for (Integer id : ids) {
            questionsService.removeById(id);
            LambdaQueryWrapper<Questions> lambdaQueryWrapper = new LambdaQueryWrapper<>();

            lambdaQueryWrapper.eq(Questions::getParentId, id);
            questionsService.remove(lambdaQueryWrapper);
        }
        return ResultVo.oK();
    }

    @PutMapping("/reply")
    public ResultVo reply(@RequestBody Questions questions, HttpServletRequest request) {
        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));
        Questions entity = new Questions();
        entity.setTitle(questions.getReply());
        entity.setParentId(questions.getId());
        entity.setPatientId(questions.getPatientId());
        entity.setDoctorId(userId);
        questionsService.save(entity);

        return ResultVo.oK();
    }
}
