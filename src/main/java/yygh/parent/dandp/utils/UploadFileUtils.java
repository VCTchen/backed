package yygh.parent.dandp.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import yygh.parent.dandp.config.properties.PenintProperties;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
public class UploadFileUtils {

    public static PenintProperties properties = SpringContextUtil.getBean(PenintProperties.class);

    /**
     * 默认上传的本地文件夹
     */
    private static String DEFAULT_UPLOAD_AVATAR_DIR = properties.getResourcePath().getUploadAvatar();

    /**
     * 默认存储URL前缀
     */
    private static String DEFAULT_DB_AVATAR_URL = properties.getResourcePath().getAvatarUrl();

    /**
     * 生成文件名+后缀
     *
     * @return
     */
    public static String CreateFileUrl(String path) {
//        //获取.的位置
//        int index = path.lastIndexOf(".");
//        //获取后缀
//        String extions = path.substring(index);
//        //返回随机生成+后缀
        return UUID.randomUUID().toString();
    }

    /**
     * 生成当前日期字符串
     *
     * @return
     */
    public static String CreateNowDate() {
        Calendar now = Calendar.getInstance();
        String nowDate = "";
        nowDate += now.get(Calendar.YEAR);
        nowDate += (now.get(Calendar.MONTH) + 1);
        nowDate += now.get(Calendar.DAY_OF_MONTH);
        return nowDate;
    }

    /**
     * 判断是否是图片
     *
     * @param file
     * @return
     */
    public static boolean isImage(File file) {
        if (!file.exists()) {
            return false;
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
            if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 头像上传（仅限头像上传）
     *
     * @param UploadFile
     * @return fileSize 文件大小
     * generateFileName 生成的文件名
     * generateFolder 生成的目的文件夹
     * oldName 原文件名
     * fileType 文件后缀
     * isImage 是否是图片
     * requestUrl 文件请求路径
     * status 是否上传成功
     */
    public static JSONObject upload(MultipartFile UploadFile) {

        JSONObject data = new JSONObject();
        // 计算文件大小
        BigDecimal size = new BigDecimal(UploadFile.getSize());
        BigDecimal mod = new BigDecimal(1024);

        //除一个1024，不保留小数，进行四舍五入
        String fileSize = size.divide(mod).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        log.info("上传文件的大小：" + fileSize + "kb");
        data.put("fileSize", fileSize);


        // 生成目的文件夹名称（日期命名）
        String generateFolder = UploadFileUtils.CreateNowDate();
        data.put("generateFolder", generateFolder);
        // 原文件名
        String oldName = UploadFile.getOriginalFilename();
        log.info("原文件名：" + oldName);
        data.put("oldName", oldName);

        // 生成的文件名
        String generateFileName = UploadFileUtils.CreateFileUrl(oldName);
        data.put("generateFileName", generateFileName);


        // 取出文件的后缀（类型）
        String fileType = oldName.substring(oldName.lastIndexOf(".") + 1);
        log.info("文件名后缀（类型）：" + fileType);
        data.put("fileType", fileType);

        InputStream inputStream = null;
        String uploadPath = "";
        String requestURL = "";
        try {
            inputStream = UploadFile.getInputStream();
            BufferedImage bi = ImageIO.read(inputStream);
            if (bi == null) {
                log.info("此文件不是图片文件");
                data.put("isImage", false);
            } else {
                log.info("此文件是图片文件");
                data.put("isImage", true);
                requestURL = DEFAULT_DB_AVATAR_URL + "/" + generateFolder + "/" + generateFileName;
                data.put("requestUrl", requestURL);
                log.info("图片请求路径：" + requestURL);
                uploadPath = DEFAULT_UPLOAD_AVATAR_DIR + "/" + generateFolder;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            UploadFile.transferTo(new File(uploadPath + File.separator + generateFileName));
            data.put("status", 200);
            data.put("requestUrl", requestURL);
            log.info("文件或图片上传成功");

        } catch (IOException e) {
            data.put("status", 201);
            e.printStackTrace();
            log.info("图片上传失败");
        }
        return data;
    }
}
