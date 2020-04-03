package com.education.hjj.bz.controller;

import static com.education.hjj.bz.service.WeChatService.getAccessToken;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.entity.StudentLogPo;
import com.education.hjj.bz.entity.vo.StudentVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.entity.vo.UserIndentityVo;
import com.education.hjj.bz.enums.ErrorEnum;
import com.education.hjj.bz.formBean.LoginForm;
import com.education.hjj.bz.formBean.LogoutForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.formBean.StudentForm;
import com.education.hjj.bz.formBean.UserForm;
import com.education.hjj.bz.mapper.StudentMapper;
import com.education.hjj.bz.mapper.TeacherMapper;
import com.education.hjj.bz.model.UserDto;
import com.education.hjj.bz.model.common.ResponseBean;
import com.education.hjj.bz.service.ISmsService;
import com.education.hjj.bz.service.IUserService;
import com.education.hjj.bz.service.LoginLogService;
import com.education.hjj.bz.service.PointsLogService;
import com.education.hjj.bz.service.StudentLogService;
import com.education.hjj.bz.service.StudentService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.AesCipherUtil;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.Constant;
import com.education.hjj.bz.util.DateUtil;
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
	private StudentService studentService;

	@Autowired
	private LoginLogService loginLogService;
	
	@Autowired
	private PointsLogService pointsLogService;
	
    private final UserUtil userUtil;

    private final IUserService userService;
    
    @Autowired
    private ISmsService smsService;

    @Autowired
	private StudentLogService studentLogService;

    @Autowired
	private StudentMapper studentMapper;

    @Autowired
	private TeacherMapper teacherMapper;
//
//    @Autowired
//	private RedisService redisService;

    @Autowired
	public LoginController(UserUtil userUtil, IUserService userService) {
		super();
		this.userUtil = userUtil;
		this.userService = userService;
	}


	/**
	 *
	 * 根据code获取用户信息
	 *
	 **/
	@ApiOperation("获取用户信息")
	@PostMapping("/getUserIdentity")
	public ApiResponse getUserInfo(@RequestBody StudentDemandForm demandForm, HttpServletResponse httpServletResponse) throws Exception {
		String code = demandForm.getCode();//获取微信服务器授权返回的code值
		String openId = getOpenId(code);

		UserIndentityVo vo = new UserIndentityVo();
		StudentVo studentVo = studentMapper.findByOpenId(openId);
		if (studentVo != null) {
			vo.setType(1);
			vo.setUserId(studentVo.getSid());
			vo.setPhone(studentVo.getParentPhoneNum());
			vo.setUserName(studentVo.getStudentName());

			String token = JwtUtil.sign(studentVo.getParentPhoneNum(), String.valueOf(System.currentTimeMillis()));
			httpServletResponse.setHeader(Constant.TOKEN, token);
			httpServletResponse.setHeader("Access-Control-Expose-Headers", Constant.TOKEN);

			// 更新学员的用户图像
			String accessToken = getAccessToken();
			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
			Map<String, String> urlData= new HashMap<String, String>();

			urlData.put("access_token",accessToken);//调用接口凭证
			urlData.put("openid",openId);//普通用户的标识，对当前公众号唯一
			urlData.put("lang","zh_CN");//返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
			String jsonStr = HttpClientUtils.doGet(url, urlData);
			String headImgUrl = JSONObject.parseObject(jsonStr).getString("headimgurl");
			StudentForm student = new StudentForm();
			student.setSid(studentVo.getSid());
			student.setPicture(headImgUrl);
			logger.info("caohuan******headImgUrl*******" + headImgUrl);
			if (StringUtils.isEmpty(headImgUrl)) {
			    logger.info("huanhuan****获取用户图像失败");
            } else {
                studentMapper.updateNotNull(student);
            }

			return ApiResponse.success(vo);
		}

		TeacherVo teacherVo = teacherMapper.findByOpenId(openId);
		if (teacherVo != null) {
			vo.setType(2);
			vo.setUserId(Long.valueOf(teacherVo.getTeacherId()));
			vo.setPhone(teacherVo.getTelephone());
			vo.setUserName(teacherVo.getName());

			String token = JwtUtil.sign(teacherVo.getTelephone(), String.valueOf(System.currentTimeMillis()));
			httpServletResponse.setHeader(Constant.TOKEN, token);
			httpServletResponse.setHeader("Access-Control-Expose-Headers", Constant.TOKEN);
			return ApiResponse.success(vo);
		}
		return ApiResponse.error("当前用户为注册");
	}

	@Transactional
	String getOpenId(String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + com.education.hjj.bz.util.weixinUtil.config.Constant.APP_ID + "&secret="
				+ com.education.hjj.bz.util.weixinUtil.config.Constant.APP_SECRET + "&code=" + code + "&grant_type=authorization_code";

		Map<String, String> urlData = new HashMap<String, String>();

		urlData.put("appid", com.education.hjj.bz.util.weixinUtil.config.Constant.APP_ID);// 小程序id
		urlData.put("secret", com.education.hjj.bz.util.weixinUtil.config.Constant.APP_SECRET);// 小程序key
		urlData.put("js_code", code);// 小程序传过来的code
		urlData.put("grant_type", "authorization_code");// 固定值这样写就行

		String openid;

		try {
			String jsonStr = HttpClientUtils.doGet(url, urlData);

			logger.info("jsonStr = {}", jsonStr);

			openid = JSONObject.parseObject(jsonStr).getString("openid");

			String unionid = JSONObject.parseObject(jsonStr).getString("unionid");
			logger.info("openid = {}, unionid = {}", openid, unionid);

			return openid;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
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
	public ApiResponse login(@RequestBody LoginForm LoginForm ,  HttpServletResponse httpServletResponse) throws Exception {

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
			return ApiResponse.error(codeMessage.get("msg").toString());
		}

		TeacherVo teacherVo = null;
		StudentVo studentVo = null;

		int j =0;
		
		Integer loginType = LoginForm.getLoginType();
		
		 Map<String , Object> map = new HashMap<String, Object>(1);

		if(loginType == Constant.TEACHER_CODE) {
			//插入teacher表
			teacherVo = userInfoService.queryTeacherInfosByTelephone(phoneNum);
			
			if (teacherVo == null) {//首次注册登录,加入用户详情表
				logger.info("该用户为首次注册用户");
				j = userInfoService.insertTeacherInfo(LoginForm);
				
				if(j<=0) {
					return ApiResponse.error("注册失败，请重新注册！");
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
		        	return ApiResponse.error("注册失败，请检查网络...");
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

	            map.put("teacherId", teacherVo.getTeacherId());
	            map.put("telephone", teacherVo.getTelephone());
	            map.put("registerDate", DateUtil.getStandardDay(teacherVo.getCreateTime()));
	            map.put("vacationStatus", teacherVo.getVacationStatus());
	            map.put("teacherLevel", teacherVo.getTeacherLevel());
	            map.put("auditStatus", teacherVo.getAuditStatus());
	            map.put("logonStatus", teacherVo.getLogonStatus());
	            map.put("employRate", teacherVo.getEmployRate());
	            map.put("resumptionRate", teacherVo.getResumptionRate());
	          
	            logger.info("telephone = {}" , map.get("telephone"));
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

            map.put("teacherName", teacherVo.getName());
            map.put("teacherId", teacherVo.getTeacherId());
            map.put("telephone", teacherVo.getTelephone());
            map.put("registerDate", DateUtil.getStandardDay(teacherVo.getCreateTime()));
            map.put("vacationStatus", teacherVo.getVacationStatus());
            map.put("teacherLevel", teacherVo.getTeacherLevel());
            map.put("auditStatus", teacherVo.getAuditStatus());
            map.put("logonStatus", teacherVo.getLogonStatus());
            map.put("employRate", teacherVo.getEmployRate());
            map.put("resumptionRate", teacherVo.getResumptionRate());
            
            logger.info("telephone = {} , teacherLevel = {}" , map.get("telephone") , map.get("teacherLevel"));
		// 学员端
		}else if (loginType.equals(Constant.STUDENT_CODE)) {

			// 检索是否用户已经注册过
			studentVo = studentService.findByPhone(phoneNum);

            if (studentVo == null) {//首次注册登录,加入用户详情表
                logger.info("该学员端用户为首次注册用户");

				j = studentService.add(LoginForm);

                if(j<=0) {
                    return ApiResponse.error("注册失败，请重新注册！");
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
                    return ApiResponse.error("注册失败，请检查网络...");
                }

                //记录学员的登陆日志，教员学员放一起
                loginLogService.addLoginLog(loginType, phoneNum , phoneNum);

                //将自动生成token信息，并将信息返回前端
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                JedisUtil.setObject(Constant.PREFIX_SHIRO_ACCESS_TOKEN + phoneNum, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
                JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + phoneNum, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
                // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
                String token = JwtUtil.sign(phoneNum, currentTimeMillis);
                httpServletResponse.setHeader(Constant.TOKEN, token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", Constant.TOKEN);

				studentVo = studentService.findByPhone(phoneNum);

				map.put("studentId", studentVo.getSid());
				map.put("telephone", studentVo.getParentPhoneNum());

				return ApiResponse.success("注册成功" , UtilTools.mapToJson(map));
			} else {
				logger.info("该用户为已注册用户");
				String openId = studentVo.getOpenId();

				if(openId ==null || StringUtils.isBlank(openId)) {

					studentService.updateOpenIdByStudentId(LoginForm.getOpenId() , studentVo.getSid());

				}else if(openId.equalsIgnoreCase(LoginForm.getOpenId())) {
					//记录学院端的登陆日志
					j = loginLogService.addLoginLog(loginType, phoneNum , phoneNum);
				}else {
					return ApiResponse.error("登录失败...");
				}
			}
			// 记录学员端客户信息
//			redisService.cacheValue(RedisContant.STUDENT_INFO + studentVo.getSid(), JSON.toJSONString(studentVo), 35 * 60);
			StudentLogPo logPo = new StudentLogPo();
            logPo.setStudentId(Integer.valueOf(studentVo.getSid().toString()));
            logPo.setLogType(1); // 登录
			logPo.setLogContent("最近登录了系统");
			logPo.setStudentName(studentVo.getStudentName());
			logPo.setStatus(1);
			logPo.setCreateTime(new Date());
			logPo.setCreateUser(studentVo.getSid().toString());
			logPo.setUpdateTime(new Date());
			logPo.setUpdateUser(studentVo.getSid().toString());
			studentLogService.addStudentLog(logPo);


			if (StringUtils.isNotEmpty(LoginForm.getHeadPicture())) {
				try{
					// 更新用户图像
					String openId = LoginForm.getOpenId();
					logger.info("huanhuan********openId***:"+ openId);


					StudentForm student = new StudentForm();
					student.setSid(studentVo.getSid());
					student.setPicture(LoginForm.getHeadPicture());
					studentMapper.updateNotNull(student);
				} catch (Exception e) {
					logger.info("huanhuan***********,更新用户图像出错");
				}

			}

			map.put("studentName", studentVo.getStudentName());
			map.put("studentId", studentVo.getSid());
			map.put("telephone", studentVo.getParentPhoneNum());
		}
		
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
    @ApiOperation("用户退出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @Transactional
    public ApiResponse logout(HttpServletRequest request , @RequestBody LogoutForm logoutForm) {

    	Subject subject = SecurityUtils.getSubject();
        String token = request.getHeader(Constant.TOKEN);

    	String telephone = "";
    	String userId = logoutForm.getUserId();
    	Integer type = logoutForm.getType();
    	String account = logoutForm.getAccount();

    	//教员登出
    	if(type == Constant.TEACHER_CODE) {
    		TeacherVo  teacherInfo = userInfoService.queryTeacherHomeInfos(userId);

    		 if(teacherInfo != null) {
    	        	telephone = teacherInfo.getTelephone();
    	     }
    	}

    	//学员登出
    	if(type == Constant.STUDENT_CODE) {

    	}
    	
    	//教务端登出
    	if(type == Constant.ADMIN_CODE || type == Constant.AUDIT_CODE) {
    		telephone = account;
    	}


        logger.info("teacherId = {} , telephone = {}" , userId , telephone);
        
        //删除Token
        if (StringUtils.isNotBlank(token)) {
            JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + telephone);
        }
        
        //删除授权
        if(JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + telephone)) {
        	JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + telephone);
        }

        subject.logout();
        return ApiResponse.success("登出成功！");
    }
    
    @GetMapping(value = "unauthorized")
    @ApiOperation("用户未授权")
    public ApiResponse unauthorized() {
        return new ApiResponse(ErrorEnum.USER_UNAUTHORIZED.getCode(), ErrorEnum.USER_UNAUTHORIZED.getMsg());
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
    
    @ApiOperation("教务端用户登录")
	@RequestMapping(value = "/educationalLogin", method = RequestMethod.POST)
	@Transactional
	public ApiResponse educationalLogin(@RequestBody UserForm userForm ,  HttpServletResponse httpServletResponse) throws Exception {
    	
    	String account = userForm.getAccount();
    	
    	String password = userForm.getPassword();
    	
    	//统一处理：如果是已经注册过的用户登录，教员和学生统一将认证信息和权限授权更新。
		UserDto userDtoTemp = new UserDto();
		
		//账号即是手机号
        userDtoTemp.setAccount(account);
        userDtoTemp = userService.selectOne(userDtoTemp);
        
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(userDtoTemp.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        
        if (key.equals(account + password)) {
        	
        	// 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(account, currentTimeMillis);
            httpServletResponse.setHeader(Constant.TOKEN, token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", Constant.TOKEN);

        	
            return ApiResponse.success("登录成功" , JSON.toJSON(userDtoTemp.getUsername()));
            
        } else {
        	
        	return ApiResponse.error("账号或密码错误,请重新输入...");
        }
    	
    }
    
    @ApiOperation("教务端用户注册")
	@RequestMapping(value = "/educationalRegister", method = RequestMethod.POST)
	@Transactional
	public ApiResponse educationalRegister(@RequestBody UserForm userForm ,  HttpServletResponse httpServletResponse) throws Exception {
    	
    	String account = userForm.getAccount();
    	
    	if(account == null || StringUtils.isBlank(account)) {
    		
    		return ApiResponse.error("输入的账号为空,请重新输入!");
    	}
    	
    	String password = userForm.getPassword();
    	
    	if(password == null || StringUtils.isBlank(password)) {
    		
    		return ApiResponse.error("输入的密码为空,请重新输入!");
    	}
    	
    	String userName = userForm.getUserName();
    	
    	if(userName == null || StringUtils.isBlank(userName)) {
    		
    		return ApiResponse.error("输入的用户名为空,请重新输入!");
    	}
    	
    	//统一处理：如果是已经注册过的用户登录，教员和学生统一将认证信息和权限授权更新。
		UserDto userDtoTemp = new UserDto();
		
		//账号即是手机号
        userDtoTemp.setAccount(account);
        userDtoTemp = userService.selectOne(userDtoTemp);
        
        if(userDtoTemp != null) {
            	
            return ApiResponse.error("账号已注册,请直接登陆...");
            
        }else {
        	
        	UserDto userDto = new UserDto();
        	userDto.setAccount(account);
        	// 密码以帐号+密码的形式进行AES加密
            String key = AesCipherUtil.enCrypto(account + password);
            userDto.setPassword(key);
            userDto.setUsername(userName);
            userDto.setRegTime(new Date());
            userDto.setStatus(1);
            
            //加入用户表user
            int count = userService.insertUser(userDto);
            if (count <= 0) {
                return ApiResponse.error("注册失败，请检查网络...");
            }
            
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(account, currentTimeMillis);
            httpServletResponse.setHeader(Constant.TOKEN, token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", Constant.TOKEN);
            
            return ApiResponse.success("注册成功！");
        }
        
    }
}
