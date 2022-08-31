package yygh.parent.dandp.utils;

import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class FileUpload {

    public static String uploadAccessory(MultipartFile uploadFile) {
        String requestURL = null;
        // 计算文件大小
        BigDecimal size = new BigDecimal(uploadFile.getSize());
        BigDecimal mod = new BigDecimal(1024);
        //除一个1024，不保留小数，进行四舍五入
        String fileSize = size.divide(mod).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        // 生成的文件名
        String fileName = UploadFileUtils.CreateFileUrl(uploadFile.getOriginalFilename());
        // 生成目的文件夹名称（日期命名）
        String folder = UploadFileUtils.CreateNowDate();
        // 原文件名
        String oldName = uploadFile.getOriginalFilename();
        // 取出文件的后缀（类型）
        String fileType = oldName.substring(oldName.lastIndexOf(".") + 1);
        try {
            InputStream inputStream = uploadFile.getInputStream();
            BufferedImage bi = ImageIO.read(inputStream);
            String uploadPath = "";
            if (bi == null) {
                requestURL = "https://ftp.myxsmanagement.top/file/video/" + folder + "/" + fileName;
                uploadPath = "/usr/local/src/file/video/" + folder;
            } else {
                // 图片请求路径
                requestURL = "https://ftp.myxsmanagement.top/file/image/" + folder + "/" + fileName;
                uploadPath = "/usr/local/src/file/image/" + folder;
            }
            File file = new File(uploadPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            uploadFile.transferTo(new File(uploadPath + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestURL;
    }
}

