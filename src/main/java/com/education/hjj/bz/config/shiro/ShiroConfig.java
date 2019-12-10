package com.education.hjj.bz.config.shiro;


import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.education.hjj.bz.config.shiro.cache.CustomCacheManager;
import com.education.hjj.bz.config.shiro.jwt.JwtFilter;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 * @author dolyw.com
 * @date 2018/8/30 15:49
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置使用自定义Realm，关闭Shiro自带的session
     * 详情见文档 http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
     * @param userRealm
     * @return org.apache.shiro.web.mgt.DefaultWebSecurityManager
     * @author dolyw.com
     * @date 2018/8/31 10:55
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 使用自定义Realm
        defaultWebSecurityManager.setRealm(userRealm);
        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        // 设置自定义Cache缓存
        defaultWebSecurityManager.setCacheManager(new CustomCacheManager());
        return defaultWebSecurityManager;
    }

    /**
     * 添加自己的过滤器，自定义url规则
     * Shiro自带拦截器配置规则
     * rest：比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,其中method为post，get，delete等
     * port：比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数
     * perms：比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法
     * roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty.blog.163.com/blog/static/83831226201302983358670/
     * anon：比如/admins/**=anon 没有参数，表示可以匿名使用
     * authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
     * authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
     * ssl：比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
     * user：比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
     * 详情见文档 http://shiro.apache.org/web.html#urls-
     * @param securityManager
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @author dolyw.com
     * @date 2018/8/31 10:57
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(16);
//        filterMap.put("jwt", new JwtFilter());
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 自定义url规则使用LinkedHashMap有序Map
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>(16);
        // Swagger接口文档
        // filterChainDefinitionMap.put("/v2/api-docs", "anon");
        // filterChainDefinitionMap.put("/webjars/**", "anon");
        // filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        // filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        // filterChainDefinitionMap.put("/doc.html", "anon");
        // 公开接口
        // filterChainDefinitionMap.put("/api/**", "anon");
        
        filterChainDefinitionMap.put("/manage/login","anon");
        
        // 配置swagger资源放行
        filterChainDefinitionMap.putAll(swaggerMap);

        // 登录放行
        filterChainDefinitionMap.putAll(anonMap);        
        
        // 所有请求通过我们自己的JWTFilter
        filterChainDefinitionMap.put("/**", "jwt");
        
     // 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        // 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        
        factoryBean.setLoginUrl("/manage/home"); // 登出
        // 设置无权限时跳转的URL
        factoryBean.setUnauthorizedUrl("/user/unauth");
        
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题，https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    
    private static final Map<String, String> swaggerMap =  new HashMap<String, String>(){{
        // 静态资源放行
        put("/static/**", "anon");
        put("/templates/**", "anon");
        // 配置swagger资源放行
        put("/", "anon");
        // springfox
        put("/csrf", "anon");
        put("/swagger-ui.html", "anon");
        put("/swagger-resources/**", "anon");
        put("/v2/**", "anon");
        put("/webjars/**", "anon");
    }};
    
 // 直接放行的接口
    private static final Map<String, String> anonMap =  new HashMap<String, String>(){{
        put("/user/getOpenId", "anon");

        put("/user/login", "anon");
        put("/user/getUserIdentity", "anon");
        put("/user/unauth", "anon");
        put("/user/error", "anon");
       
        // 获取菜单栏
        put("/user/getPermit", "anon");
        
        //教务系统登录
        put("/manage/login", "anon");
        //教务系统注册
        put("/manage/register", "anon");
        
        //手机验证码发送
        put("/smsVerifityCode/sendMessage", "anon");
        
        //获取OpenId
        put("/user/getOpenId", "anon");
        
      //teacher/list
        put("/teacher/list", "anon");
        
      //teacher/selectList
        put("/teacher/selectList", "anon");
        
      //userInfo/queryUserInfosDetail
        put("/userInfo/queryUserInfosDetail", "anon");
        
      //home/queryTeacherInfosByHome
        put("/home/queryTeacherInfosByHome", "anon");
        
      //home/queryTeacherInfosByHome
        put("/home/queryStudentDemandSignUpTeacher", "anon");
        
      //teacher/findAllSubject
        put("/teacher/findAllSubject", "anon"); 
        
        put("/parameter/queryParametersByType", "anon"); 
        
        put("/grade/queryAllGradelist", "anon"); 
        
        put("/grade/queryAllBranchlist", "anon"); 
        
        put("/home/queryAllStudentDemandList", "anon"); 
        
        put("/student/findStudent", "anon");

        // 微信支付回调函数
        put("/weixin/wxNotify", "anon");
        
        // 学员端首页
        put("/StudentDemand/homepageInfo", "anon");
        
        // 查询所有的大学
        put("/teacher/listUniversity", "anon");
        
        // 学员端查询所有的教员信息
        put("/userInfo/queryAllTeacherInfosByStudents", "anon");

    }};
}
