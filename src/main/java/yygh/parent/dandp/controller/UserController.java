package yygh.parent.dandp.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yygh.parent.dandp.entity.User;
import yygh.parent.dandp.service.IUserService;
import yygh.parent.dandp.utils.*;
import yygh.parent.dandp.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 系统用户 前端控制器
 */
@RestController
@RequestMapping("/adminApi/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public ResultVo list(QueryRequest queryRequest, User user) {
        return ResultVo.oK(userService.queryFuzz(queryRequest, user));
    }

    /**
     * 修改账户状态，禁用或者取消
     *
     * @param user
     * @return
     */
    @PutMapping("/changeStatus")
    public ResultVo changeStatus(@RequestBody User user) {
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
    public ResultVo add(@RequestBody User entity) {
        // 查询用户名是否存在数据库中
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, entity.getUsername());
        User user = userService.getOne(lambdaQueryWrapper);
        if (user == null) {
            entity.setCreateTime(new Date());
            // 设置默认密码123456
            entity.setPassword(MD5Util.md5("123456"));
            entity.setRoleId(0);
            userService.save(entity);
            // 插入返回ID
            return ResultVo.oK("用户:" + entity.getUsername() + "新增成功，默认密码为：123456");
        } else {
            return ResultVo.failed(201, "用户新增失败，用户名：" + entity.getUsername() + "已存在");
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    public ResultVo remove(@PathVariable Integer[] userIds) {
        boolean flag = false;
        for (Integer userId : userIds) {
            User entity = userService.getById(userId);
            if (!entity.getUsername().equals("admin")) {
                userService.removeById(userId);
            } else {
                flag = true;
            }
        }
        if (flag) {
            return ResultVo.failed(201, "admin用户不能删除");
        } else {
            return ResultVo.oK();
        }
    }

    /**
     * 修改用户
     */
    @PutMapping
    public ResultVo edit(@RequestBody User user) {
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


    /**
     * 个人中心配置
     */
    @GetMapping("/profile")
    public ResultVo profile(HttpServletRequest request) {
        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));
        return ResultVo.oK(userService.getById(userId));
    }

    /**
     * 个人中心配置
     */
    @PutMapping("/profile")
    public ResultVo updateProfile(@RequestBody User user) {
        userService.updateById(user);
        return ResultVo.oK();
    }

    @PutMapping("/profile/updatePwd")
    public ResultVo profileUpdatePwd(QueryRequest queryRequest, HttpServletRequest request) {
        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if (user.getPassword().equalsIgnoreCase(MD5Util.md5(queryRequest.getOldPassword()))) {
            User u = new User();
            u.setUserId(userId);
            u.setPassword(MD5Util.md5(queryRequest.getNewPassword()));
            userService.updateById(u);
            return ResultVo.oK();
        } else {
            return ResultVo.failed(201, "原密码不正确");
        }
    }

    @PostMapping("/profile/avatar")
    public ResultVo avatarUpload(@RequestParam("avatarfile") MultipartFile file, HttpServletRequest request) {
        Integer userId = TokenUtil.getAdminUserId(request.getHeader("Authorization"));
        JSONObject result = UploadFileUtils.upload(file);
        if (result.getInteger("status") == 200) {
            if (result.getBoolean("isImage")) {
                User user = new User();
                user.setAvatar(result.getString("requestUrl"));
                user.setUserId(userId);
                userService.updateById(user);
                // 缓存
                return ResultVo.oK(result.getString("requestUrl"));
            } else {
                return ResultVo.failed(201, "该图片不是图片文件");
            }
        } else {
            return ResultVo.failed(201, "文件上传失败!");
        }

    }
}
