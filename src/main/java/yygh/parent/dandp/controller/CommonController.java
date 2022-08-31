package yygh.parent.dandp.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yygh.parent.dandp.annotation.AdminLoginToken;
import yygh.parent.dandp.entity.Department;
import yygh.parent.dandp.entity.User;
import yygh.parent.dandp.service.IDepartmentService;
import yygh.parent.dandp.service.IUserService;
import yygh.parent.dandp.utils.FileUpload;
import yygh.parent.dandp.utils.MD5Util;
import yygh.parent.dandp.utils.ResultVo;
import yygh.parent.dandp.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/adminApi/common")
public class CommonController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IDepartmentService departmentService;

    @PostMapping("/login")
    public Object adminLogin(String username, String password) {
        User user = userService.selectByUsername(username);
        if (user == null) {
            return ResultVo.failed(201, "账号不存在,请检查账号是否正确或联系管理员");
        } else {
            if (!MD5Util.md5(password).equals(user.getPassword())) {
                return ResultVo.failed(201, "密码错误");
            } else {
                JSONObject obj = new JSONObject();
                obj.put("userInfo", user);
                obj.put("token", TokenUtil.getAdminToken(user));
                return ResultVo.oK(obj);
            }
        }
    }

    @ApiOperation("根据Token获取用户信息")
    @GetMapping("/getUserInfo")
    @AdminLoginToken
    public Object getUserInfo(HttpServletRequest request) {
        User user = userService.getById(TokenUtil.getAdminUserId(request.getHeader("Authorization")));
        if (user == null) {
            return ResultVo.failed(201, "账号不存在,请检查账号是否正确或联系管理员");
        } else {
            JSONObject obj = new JSONObject();
            obj.put("userInfo", user);
            obj.put("token", TokenUtil.getAdminToken(user));
            return ResultVo.oK(obj);
        }
    }

    @ApiOperation("退出登录")
    @GetMapping("/outLogin")
    public Object outLogin() {
        return ResultVo.oK();
    }


    @ApiOperation("文件上传")
    @PostMapping("/file")
    public ResultVo uploadFile(@RequestParam("file") MultipartFile file) {
        String uploadUrl = FileUpload.uploadAccessory(file);
        if (uploadUrl != null && uploadUrl != "") {
            return ResultVo.oK(uploadUrl);
        }
        return ResultVo.failed(500, "失败");
    }


    @PostMapping("/getMeInfo")
    @AdminLoginToken
    public ResultVo getMeInfo(HttpServletRequest request) {
        User user = userService.getById(TokenUtil.getAdminUserId(request.getHeader("Authorization")));
        return ResultVo.oK(user);
    }


    /**
     * 判断库中用户名 有没有被使用
     *
     * @param username
     * @return
     */
    @GetMapping("/checkUsername/{username}")
    public ResultVo checkUsername(@PathVariable String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        if (userService.getOne(lambdaQueryWrapper) != null) {
            return ResultVo.oK(true);
        } else {
            return ResultVo.oK(false);
        }
    }

    /**
     * 判断请求方法的该用户原密码是否正确
     *
     * @param password
     * @return
     */
    @GetMapping("/checkPassword/{password}")
    public ResultVo checkPassword(@PathVariable String password, HttpServletRequest request) {
        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));
        String encrypt = MD5Util.md5(password);// 用户输入的密码加密字符串

        if (userService.getById(userId).getPassword().equalsIgnoreCase(encrypt)) {
            return ResultVo.oK(true);
        } else {
            return ResultVo.oK(false);
        }
    }


    /**
     * 获取科室下拉框
     */
    @GetMapping("/getDepartment")
    public ResultVo getDepartment() {
        LambdaQueryWrapper<Department> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Department::getId, Department::getDepName);
        return ResultVo.oK(departmentService.list(lambdaQueryWrapper));
    }


    /**
     * 通过科室id查询医生
     */
    @GetMapping("/getDoctor/{id}")
    public ResultVo getDoctor(@PathVariable Integer id) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(User::getNickName, User::getUserId);
        lambdaQueryWrapper.eq(User::getDepId, id);
        return ResultVo.oK(userService.list(lambdaQueryWrapper));
    }


    @PostMapping("/registerDoctor")
    public ResultVo registerStudent(User user) {
        user.setPassword(MD5Util.md5(user.getPassword()));
        user.setCreateTime(new Date());
        user.setRoleId(1);
        userService.save(user);

        return ResultVo.oK();
    }

    @PostMapping("/registerPatients")
    public ResultVo registerTeacher(User user) {
        user.setPassword(MD5Util.md5(user.getPassword()));
        user.setCreateTime(new Date());
        user.setRoleId(2);
        userService.save(user);
        return ResultVo.oK();
    }

    /**
     * 忘记密码
     */
    @PutMapping("/forgotPassword")
    public ResultVo forgotPassword(User sysUser) {

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(sysUser.getUsername()), User::getUsername, sysUser.getUsername());
        // 先判断用户名在不在库中
        User user = userService.getOne(lambdaQueryWrapper);
        if (user != null) {
            sysUser.setPassword(MD5Util.md5(sysUser.getPassword()));
            sysUser.setUserId(user.getUserId());
            userService.updateById(sysUser);
            return ResultVo.oK();
        } else {
            return ResultVo.failed(201, "请输入正确的用户名");
        }
    }

    /**
     * 通过用户名查询医生详情，科室
     */
    @GetMapping("/getDoctorInfoByUsername/{username}")
    public ResultVo getDoctorInfoByUsername(@PathVariable String username){
        return ResultVo.oK(userService.getDoctorInfoByUsername(username));
    }

}
