package com.materials.gpt.service.impl;

import com.alibaba.fastjson.JSON;

import cn.vlabs.umt.oauth.UserInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.materials.gpt.entity.Role;
import com.materials.gpt.entity.User;
import com.materials.gpt.entity.UserQueryConditions;
import com.materials.gpt.mapper.RoleMapper;
import com.materials.gpt.mapper.UserMapper;
import com.materials.gpt.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static java.util.Arrays.asList;

@Service
public class AuthServiceImpl implements IAuthService {





	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;



	@Override
	public User register(User userToAdd) {
		final String email = userToAdd.getEmail();
		if (userMapper.findByEmail(email) != null) {
			return null;
		}
		final String rawPassword = userToAdd.getPassword();
		userToAdd.setPassword(rawPassword);
		userToAdd.setLastPasswordResetDate(new Date());
		// 初始化默认default账号
		userToAdd.setRoles(asList(new Role(1L)));
		userMapper.insert(userToAdd);
		userMapper.insertUserRoles(userToAdd);
		return userToAdd;
	}


	@Override
	public String loginToken(String userId) {
		Algorithm algorithm = null;
		try {
			algorithm = Algorithm.HMAC256("secret");
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String token = JWT.create().withHeader(map).withClaim("userid", userId)
				.withExpiresAt(new Date(System.currentTimeMillis() + 360000)).sign(algorithm);
		return token;
	}

	public void verifyJwt(String token) {
		DecodedJWT decodedJWT = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier jwtVerifier = JWT.require(algorithm).build();
			decodedJWT = jwtVerifier.verify(token);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return;
	}



	@Override
	public User getUserInfo(HttpServletRequest request){
		String token = request.getHeader("authToken");
		if (StringUtils.isEmpty(token)) {
			token = request.getParameter("token");
		}
		Long userId =  null;
		if (!StringUtils.isEmpty(token)) {
			String user =(String)redisTemplate.opsForValue().get(token);
			UserInfo userInfo = JSON.parseObject(user, UserInfo.class);
			if (null ==userInfo) {
				return null;
			}
			userId = Long.valueOf(userInfo.getUmtId());
		}
		User user = null;
		//userId = 10881369L;
		if (!StringUtils.isEmpty(userId)) {
			user = userMapper.selectByPrimaryKey(new Long(userId));
			user.setRoles(userMapper.findRolesByUserId(user.getUserId()));
		}
		return user;
	}

	@Override
	public User selectByPrimaryKey(Long userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<User> getUserList(UserQueryConditions conditions) {
		int currentPage = conditions.getCurrentPage();
		int pageSize = conditions.getPageSize();
		int pstart = (currentPage - 1) * pageSize;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("username", conditions.getUsername());
		map.put("pstart", pstart);
		map.put("pageSize", pageSize);

		return userMapper.getUserList(map);
	}

	@Override
	public int countByUsername(String username) {
		return userMapper.countByUsername(username);
	}

	@Override
	public List<Role> getAllRole() {
		return roleMapper.getAllRole();
	}

	@Override
	public void updateUserRole(Long userId, List<Long> roleIds) {
		List<Long> oldRoleIds = userMapper.findRoleIdsByUserId(userId);
		List<Long> insertRoleIds = new ArrayList<>();
		List<Long> deleteRoleIds = new ArrayList<>();
		// 先做新增
		for (Long roleId : roleIds) {
			if (!oldRoleIds.contains(roleId)) {
				insertRoleIds.add(roleId);
			}
		}
		if (!CollectionUtils.isEmpty(insertRoleIds)) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("userId", userId);
			map.put("roleIds", insertRoleIds);
			userMapper.insertUserRoleByUserId(map);
		}

		for (Long roleId : oldRoleIds) {
			if (!roleIds.contains(roleId)) {
				deleteRoleIds.add(roleId);
			}
		}
		// 再做删除
		if (!CollectionUtils.isEmpty(deleteRoleIds)) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("userId", userId);
			map.put("roleIds", deleteRoleIds);
			userMapper.deleteUserRoleByUserId(map);
		}
	}

	@Override
	public User getUserById(Long userId) {
		return userMapper.getUserById(userId);
	}

	@Override
	public int getAdminRole(String userId) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userId", userId);
		return userMapper.getAdminRole(map);
	}

	@Override
	public void updateByPrimaryKey(User user) {
		userMapper.updateByPrimaryKey(user);
	}

}

