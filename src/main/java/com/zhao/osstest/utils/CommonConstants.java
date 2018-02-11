package com.zhao.osstest.utils;

import java.math.BigDecimal;

public class CommonConstants {
	
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("conf/server.properties");
	
	//阿里云API的外网域名
	public static final String ENDPOINT=propertiesLoader.getProperty("endpoint");
	 //阿里云API的密钥Access Key ID  
    public static final String ACCESS_KEY_ID = propertiesLoader.getProperty("accesskeyid");  
    //阿里云API的密钥Access Key Secret  
    public static final String ACCESS_KEY_SECRET = propertiesLoader.getProperty("accesskeysecret");  
    //阿里云API的bucket名称  
    public static final String BACKET_NAME = propertiesLoader.getProperty("backetname");
    //阿里云url
    public static final String accessUrl = propertiesLoader.getProperty("accessUrl");
	/**
	 * 系统常量
	 */
    public static final String IMG_SERVER = propertiesLoader.getProperty("img.server");
    public static final String API_SERVER = propertiesLoader.getProperty("api.server");
    public static final String FILE_BASEPATH = propertiesLoader.getProperty("file.basepath");
    public static final String ADMIN_SERVER = propertiesLoader.getProperty("admin.server");
//    public static final String FRONT_SERVER = propertiesLoader.getProperty("front.server");
    public static final String SELLER_SERVER = propertiesLoader.getProperty("seller.server");
//    public static final String LUCENE_BASEPATH = propertiesLoader.getProperty("lucene.server");
//    public static final String REPORT_BASEPATH = propertiesLoader.getProperty("report.basepath");
//    public static final String MODEL_BASEPATH = propertiesLoader.getProperty("model.basepath");
    public static final String STATIC_PAGE_BASEPATH = propertiesLoader.getProperty("staticpage.basepath");
    public static final BigDecimal PROCEDURE_RATES = new BigDecimal(propertiesLoader.getProperty("procedure.rates"));
    /**
     * 我的钱包-惠豆兑换规则
     */
    public static final String BEANS_CASH_RULE = propertiesLoader.getProperty("beans.cash.rule");
    /**
     *   API  是否启用加密模式 1是 0否
     */
    public static final String STATIC_API_ENCRYPT_MODEL = propertiesLoader.getProperty("api.encrypt.model");
    public static final  String SMSSWITCH = new  String(propertiesLoader.getProperty("sms.smsswitch"));
    
    /**
     * 微信登录请求参数
     */
    private static PropertiesLoader weixinPropertiesLoader = new PropertiesLoader("conf/weixinconnect.properties");
    public static final String WEIXIN_APPID = weixinPropertiesLoader.getProperty("AppID");
    public static final String WEIXIN_APPSECRET = weixinPropertiesLoader.getProperty("AppSecret");
    public static final String WEIXIN_REDIRECT_URI = weixinPropertiesLoader.getProperty("redirect_uri");
    public static final String WEIXIN_SCOPE = weixinPropertiesLoader.getProperty("scope");
    public static final String WEIXIN_RESPONSE_TYPE = weixinPropertiesLoader.getProperty("response_type");
    public static final String WEIXIN_AUTHORIZATIONCODEURL = weixinPropertiesLoader.getProperty("authorizationCodeURL");
    public static final String WEIXIN_ACCESSTOKEN = weixinPropertiesLoader.getProperty("accessToken");
    public static final String WEIXIN_REFRESHTOKEN = weixinPropertiesLoader.getProperty("refreshToken");
    public static final String WEIXIN_CHECKACCESSTOKEN = weixinPropertiesLoader.getProperty("checkAccessToken");
    public static final String WEIXIN_USERINFO = weixinPropertiesLoader.getProperty("userInfo");
    
   /**
    * 交易时间
    */
    private static PropertiesLoader tradingTimePropertiesLoader = new PropertiesLoader("conf/timeCheck.properties");
 	// 大盘产品交易时间
    public static final String TRADING_DATE_TIME_STR = tradingTimePropertiesLoader .getProperty("trading.date.time");
 	// 节假日不开盘
    public static final String TRADING_HOLIDAY_TIME_STR = tradingTimePropertiesLoader.getProperty("trading.holiday.time");
 	// 特殊日期
    public static final String TRADING_SPECIALOPEN_TIME_STR = tradingTimePropertiesLoader.getProperty("trading.special.openTime");
 	// 交易日
    public static final String TRADING_WEEK_TIME_STR = tradingTimePropertiesLoader.getProperty("trading.week.time");
    
}

