package yygh.parent.dandp.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import yygh.parent.dandp.entity.User;
import yygh.parent.dandp.intercepter.TokenException;

import java.util.Date;

public class TokenUtil {

    // 过期时间，单位毫秒，正常30分钟
    private static final long EXPIRE_TIME = 1000 * 60 * 30;
    //密钥
    public static String SECRET = "HH_CC_FFF";

    public static String getAdminToken(User user) {
        // 生成过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = "";
        token = JWT.create().withAudience(String.valueOf(user.getUserId()))// 将 user id 保存到 token 里面
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET));// 以 SECRET 作为 token 的密钥;
        return token;
    }

    public static Integer getAdminUserId(String token) {
        int adminUserId = 0;
        try {
            adminUserId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
            return adminUserId;
        } catch (JWTDecodeException j) {
            throw new TokenException(403, "token不合法");
        }
    }
}

