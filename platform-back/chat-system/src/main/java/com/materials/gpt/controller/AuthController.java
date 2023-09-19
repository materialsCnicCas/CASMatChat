package com.materials.gpt.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.materials.gpt.entity.ConnectConstant;
import com.materials.gpt.entity.User;
import com.materials.gpt.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;

import cn.vlabs.umt.oauth.AccessToken;
import cn.vlabs.umt.oauth.Oauth;
import cn.vlabs.umt.oauth.UMTOauthConnectException;
import cn.vlabs.umt.oauth.UserInfo;
import cn.vlabs.umt.oauth.common.exception.OAuthProblemException;

@RestController
@RequestMapping("/auth")
public class AuthController {


	@Autowired
	private IAuthService authService;


	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;


/**
	 * 首页地址
	 */

	public final static String HOME_URL = ConnectConstant.HOME_PAGE;

/**
	 * 退出后地址
	 */

	public final static String LOGIN_HOME_URL = ConnectConstant.QUIT_HOME_PAGE;


/**
	 * 登录失败状态
	 */

	public final static String LOGIN_ERROR = "501";


/**
	 * 登录配置文件
	 */

	public final static String LOGIN_CONFIG = "umtoauthconfig.properties";

	private final Logger logger = LoggerFactory.getLogger(AuthController.class);



    /**
	 * 退出回调函数
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 */

	@RequestMapping(value = "/hideLogout", method = RequestMethod.GET)
	public ModelAndView hideLogout(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");
		if (!StringUtils.isEmpty(token)) {
			redisTemplate.delete(token);
		}
		return new ModelAndView(new RedirectView(LOGIN_HOME_URL));

	}


/**
	 * 单点登录跳转到中科院登录界面
	 *
	 * @param request
	 * @param response
	 * @throws
	 */

	@RequestMapping(value = "/hideLogin", method = RequestMethod.GET)
	public void hideLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			File file = ResourceUtils.getFile(LOGIN_CONFIG);
			Oauth oauth = new Oauth(file.toString());
			response.sendRedirect(oauth.getAuthorizeURL(request));
		} catch (IOException | UMTOauthConnectException e) {
			logger.error(e.getMessage());
		}
	}



	/**
	 * 单点登录回调函数
	 *
	 * @param req
	 * @param resp
	 * @return
	 * @throws
	 * @throws FileNotFoundException
	 */

	@RequestMapping(value = "/gettoken", method = RequestMethod.GET)
	public ModelAndView gettoken(HttpServletRequest req, HttpServletResponse resp)
			throws  FileNotFoundException {
		InputStream in = null;
		in = this.getClass().getClassLoader().getResourceAsStream(LOGIN_CONFIG);
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e1) {
			logger.error("ready Properties error:" + e1.getMessage());
		}
		Oauth oauth = new Oauth(p);
		AccessToken token = null;
		try {
			token = oauth.getAccessTokenByRequest(req);
		} catch (UMTOauthConnectException e) {
			// 网络错误
			logger.error(e.getMessage());
		} catch (OAuthProblemException e) {
			// 系统oauth2连接错误
			logger.error(e.getMessage());
		}
		if (null == token.getUserInfo()) {
			logger.info("UserInfo is null!");
			hideLogin(req, resp);
		}
		// 获取用户登录对象
		UserInfo userInfo = token.getUserInfo();
		// 获取返回token
		String tokens = token.getAccessToken();
		// 获取用户Id
		Long userId = new Long(userInfo.getUmtId());

		String uuid = UUID.randomUUID().toString();
		tokens = uuid + ":" + tokens;
		// 把用户信息写入redis
		stringRedisTemplate.opsForValue().set(tokens, JSON.toJSONString(userInfo), 7,TimeUnit.DAYS);
		// 查询用户umtId是否存在
		User user = authService.selectByPrimaryKey(userId);
		if (null == user) {
			// 不存在添加到数据库
			User addedUser = new User();
			addedUser.setUserId(userId);
			addedUser.setUsername(userInfo.getTrueName());
			addedUser.setPassword(userInfo.getCstnetId());
			addedUser.setEmail(userInfo.getCstnetId());
			User register = authService.register(addedUser);
			if (null == register) {
				logger.info("Insert the failure!");
			}
		}
		String url_to = null;
		if (!StringUtils.isEmpty(token.getAccessToken())) {
			url_to = HOME_URL + "?pid=" + tokens;
		} else {
			hideLogin(req, resp);
		}
		return new ModelAndView(new RedirectView(url_to));
	}

}

