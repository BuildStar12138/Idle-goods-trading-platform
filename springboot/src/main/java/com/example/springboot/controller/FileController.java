package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${server.port}")
    private String port;
    private static final  String ip = "http://localhost";

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String Filename = file.getOriginalFilename();
        //定义文件唯一标识
        String s = IdUtil.fastSimpleUUID();
        String path = System.getProperty("user.dir")+"/springboot/src/main/resources/files/"+s+"_"+Filename;//获取当前文件的根路径
       //获取输入
        //System.out.println(System.getProperty("user.dir"));
        byte[] bytes1 = file.getBytes();
        InputStream in = new ByteArrayInputStream(bytes1);
        //输出
        byte[] bytes = new byte[1024];
        FileOutputStream out = new FileOutputStream(path);
        int len;
        while( (len = in.read(bytes)) != -1){
            out.write(bytes,0,len);
        }
        //关闭
        out.close();
        in.close();
        return Result.success(ip+":"+port+"/file/"+s);//返回文件存储url
    }

    @GetMapping("/{flag}")
    public void download(@PathVariable String flag,HttpServletResponse response) throws IOException {
        OutputStream outputStream;//新建一个输出流
        String s = System.getProperty("user.dir") + "/springboot/src/main/resources/files/";//定义文件上传根路径
        List<String> listFileNames = FileUtil.listFileNames(s);//获取所有文件名
        String fileName = listFileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");//找到根参数一致的文件
        try {
            if(StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(s + fileName);//通过文件路径读取文件字节流
                outputStream = response.getOutputStream();//通过输出流返回文件
                outputStream.write(bytes);
                outputStream.flush();
                outputStream.close();
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("文件下载失败");
        }
    }

}
