package com.leyou.controller;

import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("upload")
public class UploadController {

    private static final List<String> FILE_TYPE = Arrays.asList("jpg","png");

    @RequestMapping("image")
    public String uploadImage(@RequestParam("file") MultipartFile file){
        try {
            String filename = file.getOriginalFilename();
            //截取方法一
            //String type = filename.substring(filename.lastIndexOf(".") + 1);
            //截取方法二
            String filetype = StringUtils.substringAfterLast(filename, ".");
            //验证图片格式
            if(!FILE_TYPE.contains(filetype)){
                return null;
            }
            //校验图片内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage==null){
                return null;
            }
            String filePath = System.currentTimeMillis()+filename;
            //file.transferTo(new File("E:/tu"+filePath));

            //加载客户端配置文件，配置文件中指明了tracker服务器的地址
            ClientGlobal.init("fastdfs.conf");
            //验证配置文件是否加载成功
            System.out.println(ClientGlobal.configInfo());
            //创建TrackerClient对象，客户端对象
            TrackerClient trackerClient = new TrackerClient();
            //获取到调度对象，也就是与Tracker服务器取得联系
            TrackerServer trackerServer = trackerClient.getConnection();
            //创建存储客户端对象
            StorageClient storageClient = new StorageClient(trackerServer,null);

            //String[] upload_file = storageClient.upload_file("E:/tu/test.jpg", "jpg", params);

            String[] upload_file = storageClient.upload_appender_file(file.getBytes(), filetype, null);

            for (String string : upload_file) {
                System.out.println(string);
            }

            return "http://image.leyou.com/"+upload_file[0]+"/"+upload_file[1];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
