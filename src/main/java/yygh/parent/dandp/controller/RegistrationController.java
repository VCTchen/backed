package yygh.parent.dandp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yygh.parent.dandp.entity.Registration;
import yygh.parent.dandp.service.IDepartmentService;
import yygh.parent.dandp.service.IRegistrationService;
import yygh.parent.dandp.utils.*;
import yygh.parent.dandp.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 挂号表 前端控制器
 */
@RestController
@RequestMapping("/adminApi/registration")
public class RegistrationController {

    @Autowired
    private IRegistrationService registrationService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private UserManager userManager;


    @GetMapping("/list")
    public ResultVo list(Registration entity, QueryRequest queryRequest, HttpServletRequest request) {
        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));
        if (userManager.isAdminRole(userId)) {
            return ResultVo.oK(registrationService.queryList(entity, queryRequest));
        } else if (userManager.isDoctorRole(userId)) {
            return ResultVo.oK(registrationService.queryListByDoctor(entity, queryRequest, userId));
        } else if (userManager.isPatientsRole(userId)) {
            return ResultVo.oK(registrationService.queryListByPatient(entity, queryRequest, userId));
        }

        return null;

    }

    /**
     * 挂号接口
     */
    @PostMapping
    public ResultVo registration(@RequestBody Registration registration, HttpServletRequest request) {


        String s = DateUtil.parseTimeFormattoDayDate(new Date());
        s = s.replace("-", "");
        s = s + departmentService.getById(registration.getDepId()).getLetter();

        Integer count = registrationService.selectByNow(registration.getDepId());
        int num = count + 1;
        s = s + num;

        registration.setNumber(s);

        // 随机金额
        int min = 2, max = 10;
        BigDecimal v = BigDecimal.valueOf((Math.random() * (max - min)) + min);
        registration.setAmountPayable(v.setScale(2, BigDecimal.ROUND_HALF_UP));

        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));

        registration.setPatientsId(userId);
        registrationService.save(registration);
        return ResultVo.oK("已支付" + v.setScale(2, BigDecimal.ROUND_HALF_UP) + "元");

    }


    /**
     * 采纳
     */
    @GetMapping("/accept/{id}")
    public ResultVo accept(@PathVariable Integer id) {
        Registration registration = new Registration();
        registration.setId(id);
        registration.setAccept(1);
        registrationService.updateById(registration);
        return ResultVo.oK();
    }

    /**
     * 不采纳
     */
    @GetMapping("/notAccept/{id}")
    public ResultVo notAccept(@PathVariable Integer id) {
        Registration registration = new Registration();
        registration.setId(id);
        registration.setAccept(2);
        registration.setStatus(0);
        registrationService.updateById(registration);
        return ResultVo.oK();
    }

    /**
     * 评价
     */
    @PutMapping("/evaluate")
    public ResultVo evaluate(@RequestBody Registration registration) {
        registrationService.updateById(registration);
        return ResultVo.oK();
    }


    /**
     * 开发票
     */
    @PutMapping("/{id}")
    public ResultVo openInvoice(@PathVariable Integer id) {
        Registration registration = new Registration();

        // 生成发票代码，12位数，随即生成
        registration.setInvoiceCode(String.valueOf(OrderSnUtil.getRandom(12)));

        // 生成发票号，9位数，随机生成
        registration.setInvoiceNumber(String.valueOf(OrderSnUtil.getRandom(9)));

        // 生成发票日期
        registration.setInvoiceDate(LocalDate.now());

        //　改变状态
        registration.setIsInvoice(true);
        registration.setId(id);

        registrationService.updateById(registration);

        return ResultVo.oK();
    }

    /**
     * 通过挂号id获取详情
     */
    @GetMapping("/{id}")
    public ResultVo getInfoById(@PathVariable Integer id) {
        return ResultVo.oK(registrationService.getById(id));
    }
}


