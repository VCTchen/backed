package yygh.parent.dandp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yygh.parent.dandp.entity.User;
import yygh.parent.dandp.service.IUserService;

@Service
public class UserManager {

    @Autowired
    private IUserService userService;

    /**
     * 根据UserId 判断是否是管理员角色,与上面方法判断存在差异
     */
    public boolean isAdminRole(Integer userId) {
        User user = userService.getById(userId);
        if (user.getRoleId() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据UserId 判断是否是医生
     */
    public boolean isDoctorRole(Integer userId) {
        User user = userService.getById(userId);
        if (user.getRoleId() == 1) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据UserId 判断是否是学生
     */
    public boolean isPatientsRole(Integer userId) {
        User user = userService.getById(userId);
        if (user.getRoleId() == 2) {
            return true;
        } else {
            return false;
        }
    }
}