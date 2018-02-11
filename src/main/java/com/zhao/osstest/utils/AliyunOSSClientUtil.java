package com.zhao.osstest.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 
* @ClassName: AliyunOSSClinentUtil  
* @Description: java使用阿里云OSS存储对象上传图片  
* @author zyf 
* @date 2018年2月8日  
* @version 1.0
 */
public class AliyunOSSClientUtil {
	//log日志
	private static Logger logger = Logger.getLogger(AliyunOSSClientUtil.class);
	//阿里云API的内或外网域名
	private static String ENDPOINT;
	//阿里云API的密钥Access Key ID  
    private static String ACCESS_KEY_ID;  
    //阿里云API的密钥Access Key Secret  
    private static String ACCESS_KEY_SECRET;  
    //阿里云API的bucket名称  
    private static String BACKET_NAME;  
    //阿里云url
    private static String ACCESS_URL;  
    private static OSSClient ossClient;
    //初始化属性  
    static{
    	ENDPOINT =CommonConstants.ENDPOINT;
    	ACCESS_KEY_ID = CommonConstants.ACCESS_KEY_ID;
    	ACCESS_KEY_SECRET = CommonConstants.ACCESS_KEY_SECRET;
    	BACKET_NAME = CommonConstants.BACKET_NAME;
    	ACCESS_URL = CommonConstants.accessUrl;
    	
    	System.out.println(ENDPOINT+"/t"+ACCESS_KEY_ID+"/t"+ACCESS_KEY_SECRET+"/t"+BACKET_NAME);
    }
    /**
     *
    * @Title: getOSSClient  
    * @Description: 获取阿里云oss客户端对象
    * @param @return    参数  
    * @return OSSClient    返回类型  
    * @throws
     */
	public static OSSClient getOSSClient(){
		
		
		 return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}
	
	/** 
     * 创建存储空间 
     * @param ossClient      OSS连接 
     * @param bucketName 存储空间 
     * @return 
     */  
    public  static String createBucketName(OSSClient ossClient,String bucketName){  
        //存储空间  
        final String bucketNames=bucketName;  
        if(!ossClient.doesBucketExist(bucketName)){  
            //创建存储空间  
            Bucket bucket=ossClient.createBucket(bucketName);  
           logger.info("创建存储空间成功");  
            return bucket.getName();  
        }  
        return bucketNames;  
    }  
    /** 
     * 删除存储空间buckName 
     * @param ossClient  oss对象 
     * @param bucketName  存储空间 
     */  
    public static  void deleteBucket(OSSClient ossClient, String bucketName){    
        ossClient.deleteBucket(bucketName);     
        logger.info("删除" + bucketName + "Bucket成功");    
    } 
    /** 
     * 创建模拟文件夹 
     * @param ossClient oss连接 
     * @param bucketName 存储空间 
     * @param folder   模拟文件夹名如"qj_nanjing/" 
     * @return  文件夹名 
     */  
    public  static String createFolder(OSSClient ossClient,String bucketName,String folder){  
        //文件夹名   
        final String keySuffixWithSlash =folder;  
        //判断文件夹是否存在，不存在则创建  
        if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){  
            //创建文件夹  
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));  
            logger.info("创建文件夹成功");  
            //得到文件夹名  
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);  
            String fileDir=object.getKey();  
            return fileDir;  
        }  
        return keySuffixWithSlash;  
    }  
      
     /**   
        * 根据key删除OSS服务器上的文件   
        * @param ossClient  oss连接 
        * @param bucketName  存储空间  
        * @param folder  模拟文件夹名 如"qj_nanjing/" 
        * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg" 
        */      
       public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){      
            ossClient.deleteObject(bucketName, folder + key);     
            logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");    
       }   
      
    /** 
     * 上传图片至OSS 
     * @param ossClient  oss连接 
     * @param fileContent 上传文件（文件全路径如：D:\\image\\cake.jpg） 
     * @param bucketName  存储空间 
     * @param remotePath 模拟文件夹名 如"qj_nanjing/" 
     * @return String 返回的唯一MD5数字签名 
     * @throws IOException 
     * */  
    public static  String uploadObject2OSS(InputStream fileContent, String remotePath, String fileName ) throws IOException {  
        String remoteFilePath = null;
    	try {  
    	ossClient =  getOSSClient();
          //随机名处理  
            fileName =  new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));  
            //定义二级目录  
             remoteFilePath = remotePath.substring(0, remotePath.length()).replaceAll("\\\\", "/") + "/";  
            //创建二级目录
             createFolder(ossClient,BACKET_NAME,remoteFilePath);
            //创建上传Object的Metadata    
            ObjectMetadata metadata = new ObjectMetadata();  
            //上传的文件的长度  
            metadata.setContentLength(fileContent.available()); 
            //指定该Object被下载时的内容编码格式  
            metadata.setContentEncoding("utf-8");
            //指定该Object被下载时的网页的缓存行为  
            metadata.setCacheControl("no-cache");   
            //指定该Object下设置Header  
            metadata.setHeader("Pragma", "no-cache");    
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，  
            //如果没有扩展名则填默认值application/octet-stream  
            metadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));    
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）  
            metadata.setContentDisposition("inline;filename=" + fileName);    
            //上传文件   (上传文件流的形式)
            ossClient.putObject(BACKET_NAME, remoteFilePath + fileName, fileContent,metadata);    
            // 关闭OSSClient  
            ossClient.shutdown();  
            // 关闭io流  
            fileContent.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);    
        }  
        return ACCESS_URL+ "/" + remoteFilePath + fileName;  
    }  
  
    /** 
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType 
     * @param fileName 文件名 
     * @return 文件的contentType 
     */  
    public static  String getContentType(String fileName){  
        //文件的后缀名  
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));  
        if(".bmp".equalsIgnoreCase(fileExtension)) {  
            return "image/bmp";  
        }  
        if(".gif".equalsIgnoreCase(fileExtension)) {  
            return "image/gif";  
        }  
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {  
            return "image/jpeg";  
        }  
        if(".html".equalsIgnoreCase(fileExtension)) {  
            return "text/html";  
        }  
        if(".txt".equalsIgnoreCase(fileExtension)) {  
            return "text/plain";  
        }  
        if(".vsd".equalsIgnoreCase(fileExtension)) {  
            return "application/vnd.visio";  
        }  
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {  
            return "application/vnd.ms-powerpoint";  
        }  
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {  
            return "application/msword";  
        }  
        if(".xml".equalsIgnoreCase(fileExtension)) {  
            return "text/xml";  
        }  
        //默认返回类型  
        return "image/jpeg";  
    }  
    
    /** 
     * 获得url链接 
     * 
     * @param key 
     * @return 
     */  
    public static String getUrl(String key) {  
        // 设置URL过期时间为10年 3600l* 1000*24*365*10  
    	
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);  
        // 生成URL  
        URL url = ossClient.generatePresignedUrl(BACKET_NAME, key, expiration);  
        if (url != null) {  
            return url.toString();  
        }  
        return null;  
    }  
}
