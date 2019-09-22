package com.education.hjj.bz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.enums.ErrorEnum;
import com.education.hjj.bz.formBean.LoginForm;
import com.education.hjj.bz.formBean.LogoutForm;
import com.education.hjj.bz.model.UserDto;
import com.education.hjj.bz.model.common.ResponseBean;
import com.education.hjj.bz.service.ISmsService;
import com.education.hjj.bz.service.IUserService;
import com.education.hjj.bz.service.LoginLogService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.service.UserPictureInfoService;
import com.education.hjj.bz.util.AesCipherUtil;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.Constant;
import com.education.hjj.bz.util.HttpClientUtils;
import com.education.hjj.bz.util.JedisUtil;
import com.education.hjj.bz.util.JwtUtil;
import com.education.hjj.bz.util.RegUtils;
import com.education.hjj.bz.util.StrUtils;
import com.education.hjj.bz.util.UserUtil;
import com.education.hjj.bz.util.UtilTools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "用户登录" })
@RestController
@RequestMapping(value = "/user")
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	//默认密码
	@Value("${RESET_PASSWORD}")
	private String RESET_PASSWORD;
	
	//appId
	@Value("${wx.appId}")
	private String APPID;
	
	//默认密码
	@Value("${wx.secret}")
	private String SECRET;

	/**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private LoginLogService loginLogService;
	
	@Autowired
	private UserPictureInfoService pictureInfoService;
	
    private final UserUtil userUtil;

    private final IUserService userService;
    
    @Autowired
    private ISmsService smsService;
	
    @Autowired
	public LoginController(UserUtil userUtil, IUserService userService) {
		super();
		this.userUtil = userUtil;
		this.userService = userService;
	}
    
    
    /**
     * 获取当前登录用户信息
     * @param
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2019/3/15 11:51
     */
    @GetMapping("/info")
    @RequiresAuthentication
    public ResponseBean info() {
        // 获取当前登录用户
        UserDto userDto = userUtil.getUser();
        // 获取当前登录用户Id
        Integer id = userUtil.getUserId();
        // 获取当前登录用户Token
        String token = userUtil.getToken();
        // 获取当前登录用户Account
        String account = userUtil.getAccount();
        return new ResponseBean(HttpStatus.OK.value(), "您已经登录了(You are already logged in)", userDto);
    }
    

	@ApiOperation("用户登录/注册")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Transactional
	public ApiResponse login(@RequestBody LoginForm LoginForm ,  HttpServletResponse httpServletResponse) {

		String phoneNum = LoginForm.getLoginPhone();
		
		if (StrUtils.isEmpty(phoneNum) || !RegUtils.isMoblie(phoneNum)) {
			return ApiResponse.error("手机号码格式输入不正确，请重新输入！");
		}
		
		String identifyCode = LoginForm.getIdentifyCode();
		logger.info("该用户登录手机号是： {},验证码是: {}" , phoneNum , identifyCode);
		
		Map<String, Object> codeMessage = smsService.checkIsCorrectCode(phoneNum, identifyCode);
		
		if(codeMessage.size() ==0) {
			return ApiResponse.error("手机验证码输入不正确，请重新输入！");
		}
		int verifyResult = (int) codeMessage.get("code");
		
		if(Constant.FAILED == verifyResult) {
			return ApiResponse.error("手机验证码输入不正确，请重新输入！");
		}

		
		
		TeacherVo teacherVo = null;

		int j =0;
		
		Integer loginType = LoginForm.getLoginType();
		
		if(loginType == Constant.TEACHER_CODE) {
			//插入teacher表
			teacherVo = userInfoService.queryTeacherInfosByTelephone(phoneNum);
			
			if (teacherVo == null) {//首次注册登录,加入用户详情表
				logger.info("该用户为首次注册用户");
				j = userInfoService.insertTeacherInfo(LoginForm);
				
				if(j<=0) {
					return ApiResponse.success("注册失败，请重新注册！");
				}
				
				UserDto userDto = new UserDto();
				userDto.setRegTime(new Date());
				userDto.setUsername(phoneNum);
		        // 密码以帐号+密码的形式进行AES加密
		        String key = AesCipherUtil.enCrypto(phoneNum + RESET_PASSWORD);
		        userDto.setPassword(key);
		        userDto.setStatus(1);
		        //加入用户表user
		        int count = userService.insertUser(userDto);
		        if (count <= 0) {
		        	return ApiResponse.success("注册失败，请检查网络...");
		        }
		        
		        //记录教员的登陆日志
		        loginLogService.addLoginLog(loginType, phoneNum , phoneNum);
		        
		        //将自动生成token信息，并将信息返回前端
	            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
	            JedisUtil.setObject(Constant.PREFIX_SHIRO_ACCESS_TOKEN + phoneNum, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
	            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + phoneNum, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
	            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
	            String token = JwtUtil.sign(phoneNum, currentTimeMillis);
	            httpServletResponse.setHeader(Constant.TOKEN, token);
	            httpServletResponse.setHeader("Access-Control-Expose-Headers", Constant.TOKEN);

	            teacherVo = userInfoService.queryTeacherInfosByTelephone(phoneNum);
	          
	            Map<String , Object> map = new HashMap<String, Object>(1);
	            map.put("teacherId", teacherVo.getTeacherId());
	            map.put("telephone", teacherVo.getTelephone());
	          
		        return ApiResponse.success("注册成功" , UtilTools.mapToJson(map));
			}else {
				logger.info("该用户为已注册用户");
				String openId = teacherVo.getOpenId();
				
				if(openId ==null || StringUtils.isBlank(openId)) {
					
					userInfoService.updateOpenId(LoginForm.getOpenId() , teacherVo.getTeacherId());
					
				}else if(openId.equalsIgnoreCase(LoginForm.getOpenId())) {
					//记录教员的登陆日志
					j = loginLogService.addLoginLog(loginType, phoneNum , phoneNum);
				}else {
					return ApiResponse.error("登录失败...");
				}
				
			}
			
		}
		
		String teacherName = teacherVo.getName();

		//统一处理：如果是已经注册过的用户登录，教员和学生统一将认证信息和权限授权更新。
		UserDto userDtoTemp = new UserDto();
		
		//账号即是手机号
        userDtoTemp.setAccount(phoneNum);
        userDtoTemp = userService.selectOne(userDtoTemp);
        
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(userDtoTemp.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        
        if (key.equals(phoneNum + RESET_PASSWORD)) {//
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + phoneNum)) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + phoneNum);
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + phoneNum, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(phoneNum, currentTimeMillis);
            httpServletResponse.setHeader(Constant.TOKEN, token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", Constant.TOKEN);
            
            Map<String , Object> map = new HashMap<String, Object>(1);
            map.put("teacherName", teacherName);
            map.put("teacherId", teacherVo.getTeacherId());
            map.put("telephone", teacherVo.getTelephone());
            
            return ApiResponse.success("登录成功" , UtilTools.mapToJson(map));
        } else {
        	return ApiResponse.error("登录失败，请检查网络...");
        }
		
		
	}
	
	/**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @GetMapping(value = "/unauth")
    //@ApiOperation("用户未登录或已退出")
    public ApiResponse unauth() {
        return new ApiResponse(ErrorEnum.NOT_LOGIN.getCode(), ErrorEnum.NOT_LOGIN.getMsg());
    }

    /**
     * 登出 
     * @return
     */
    @GetMapping(value = "/logout")
    @ApiOperation("用户退出")
    @Transactional
    public ApiResponse logout(HttpServletRequest servletRequest , @RequestBody LogoutForm logoutForm) {
        Subject subject = SecurityUtils.getSubject();
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        
        //删除Token
        if (StringUtils.isNotBlank(token)) {
            JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + logoutForm.getLoginPhone());
        }
        
        //删除授权
        if(JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + logoutForm.getLoginPhone())) {
        	JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + logoutForm.getLoginPhone());
        }

        subject.logout();
        return new ApiResponse(ErrorEnum.USER_LOGOUT.getCode(), ErrorEnum.USER_LOGOUT.getMsg());
    }
    
    @GetMapping(value = "unauthorized")
    @ApiOperation("用户未授权")
    public ApiResponse unauthorized() {
        return new ApiResponse(ErrorEnum.USER_Unauthorized.getCode(), ErrorEnum.USER_Unauthorized.getMsg());
    }
    
    /**
     * 获取openId
     * @return
     */
    @RequestMapping(value = "/getOpenId" , method = RequestMethod.POST)
    @ApiOperation("获取openId")
    public ApiResponse getOAuth(@RequestBody LoginForm loginForm){       
        String code = loginForm.getCode();//获取微信服务器授权返回的code值
        
        logger.info("code = {}" , code);
            /**
             * 构造请求链接
             * https://api.weixin.qq.com/sns/jscode2session?
             * appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
             */
        String url = Constant.ACCESS_TOKEN_URL+"?appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";  
        
        logger.info("url = {}" , url);
        
        Map<String, String> urlData= new HashMap<String, String>();
        
        urlData.put("appid",APPID);//小程序id
        urlData.put("secret",SECRET);//小程序key
        urlData.put("js_code",code);//小程序传过来的code
        urlData.put("grant_type","authorization_code");//固定值这样写就行
        
        String openid;
        
		try {
			String jsonStr = HttpClientUtils.doGet(url, urlData);
			
			logger.info("jsonStr = {}" , jsonStr);
			
			openid = JSONObject.parseObject(jsonStr).getString("openid");
			
			String unionid = JSONObject.parseObject(jsonStr).getString("unionid");
			logger.info("openid = {}, unionid = {}" , openid , unionid);
			
			Map<String , Object> map = new HashMap<String, Object>(1);
            map.put("openid", openid);
			return ApiResponse.success("获取OpenId成功" , UtilTools.mapToJson(map));
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return ApiResponse.error("获取OpenId失败！");
    }
}
