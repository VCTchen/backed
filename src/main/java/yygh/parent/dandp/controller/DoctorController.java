package yygh.parent.dandp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yygh.parent.dandp.entity.User;
import yygh.parent.dandp.service.IUserService;
import yygh.parent.dandp.utils.MD5Util;
import yygh.parent.dandp.utils.QueryRequest;
import yygh.parent.dandp.utils.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestController
@RequestMapping("/adminApi/doctor")
public class DoctorController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public ResultVo list(QueryRequest queryRequest, User user) {
        return ResultVo.oK(userService.queryDoctorFuzz(queryRequest, user));
    }

    /**
     * 修改账户状态，禁用或者取消
     *
     * @param user
     * @param request
     * @return
     */
    @PutMapping("/changeStatus")
    public ResultVo changeStatus(@RequestBody User user, HttpServletRequest request) {
        userService.updateById(user);
        return ResultVo.oK();
    }

    /**
     * 根据ID查询用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResultVo getUserInfo(@PathVariable Integer userId) {
        User info = userService.getById(userId);
        return ResultVo.oK(info);
    }


    /**
     * 新增用户
     */
    @PostMapping
    public ResultVo add(@RequestBody User user) {

        // 查询用户名是否存在数据库中
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User entity = userService.getOne(lambdaQueryWrapper);
        if (entity == null) {
            user.setCreateTime(new Date());
            // 设置默认密码1234qwer
            user.setPassword(MD5Util.md5("1234qwer"));
            user.setRoleId(1);
            userService.save(user);
            return ResultVo.oK("用户:" + user.getUsername() + "新增成功，默认密码为：" + "1234qwer");
        } else {
            return ResultVo.failed(201, "用户新增失败，用户名：" + user.getUsername() + "已存在");
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    public ResultVo remove(@PathVariable Integer[] userIds) {
        for (Integer userId : userIds) {
            userService.removeById(userId);
            return ResultVo.oK();
        }
        return null;
    }

    /**
     * 修改用户
     *
     * @return
     */
    @PutMapping
    public ResultVo edit(@RequestBody User user) {
        user.setModifyTime(new Date());
        userService.updateById(user);
        return ResultVo.oK();
    }

    /**
     * 重置密码
     */
    @PutMapping("/resetPwd")
    public ResultVo reSetPwd(@RequestBody User user) {
        user.setPassword(MD5Util.md5(user.getPassword()));
        userService.updateById(user);
        return ResultVo.oK();
    }

}
