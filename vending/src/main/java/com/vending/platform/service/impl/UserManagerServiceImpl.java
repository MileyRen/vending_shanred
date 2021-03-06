package com.vending.platform.service.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vending.platform.dao.IFrimAndGroupDAO;
import com.vending.platform.dao.IUserManagerDao;
import com.vending.platform.domain.AuthorityInfo;
import com.vending.platform.domain.FirmInfo;
import com.vending.platform.domain.RoleAuthInfo;
import com.vending.platform.domain.RoleInfo;
import com.vending.platform.domain.UserInfo;
import com.vending.platform.domain.UserRoleInfo;
import com.vending.platform.exception.SQLFormatException;
import com.vending.platform.service.IUserManagerService;

/** 用户管理Service */
@Service
public class UserManagerServiceImpl implements IUserManagerService, Serializable {
	private static final long serialVersionUID = -5147230002673392653L;
	private static Logger logger = Logger.getLogger(UserManagerServiceImpl.class);

	@Autowired
	private IUserManagerDao userManagerDao;
	@Autowired
	private IFrimAndGroupDAO firmDao;

	@Override
	public UserInfo login(UserInfo userInfo) {
		int i = userManagerDao.getAllUsers(userInfo).size();
		if (i > 1) {
			logger.debug("用户名密码多次匹配，用户错误");
			return null;
		} else if (i < 1) {
			logger.debug("登录失败");
			return null;
		} else {
			logger.debug("登录成功");
			return userManagerDao.getAllUsers(userInfo).get(0);
		}
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userManagerDao.updateUser(userInfo);
	}

	@Override
	public List<UserInfo> getAllUserInfos(UserInfo userInfo) {
		return userManagerDao.getAllUsers(userInfo);
	}

	@Override
	public void insertUserInfo(UserInfo userInfo) {
		try {
			userManagerDao.insertUser(userInfo);
		} catch (SQLFormatException e) {
			logger.debug("插入用户失败");
			e.printStackTrace();
		}
	}

	@Override
	public UserInfo getUserInfoById(Integer id) {
		return userManagerDao.getUserById(id);
	}

	@Override
	public void deleteUserInfo(Integer id) {
		userManagerDao.deleteUser(id);
	}

	@Override
	public void insertRole(RoleInfo roleInfo) {
		try {
			userManagerDao.insertRoleInfo(roleInfo);
		} catch (SQLFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateRole(RoleInfo roleInfo) {
		userManagerDao.updateRoleInfo(roleInfo);
	}

	@Override
	public RoleInfo getRoleById(Integer id) {
		return userManagerDao.getRoleById(id);
	}

	@Override
	public List<RoleInfo> getAllRoles(RoleInfo roleInfo) {
		return userManagerDao.getAllRoles(roleInfo);
	}

	@Override
	public void deleteRole(Integer Id) {
		userManagerDao.deleteRole(Id);
	}

	@Override
	public void insertAuthority(AuthorityInfo authorityInfo) {
		try {
			userManagerDao.insertAuthorityInfo(authorityInfo);
		} catch (SQLFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public AuthorityInfo getAuthorityById(Integer id) {
		return userManagerDao.getAuthorityInfoById(id);
	}

	@Override
	public List<AuthorityInfo> getAllAuthoritys(AuthorityInfo authorityInfo) {
		return userManagerDao.getAllAuthorityInfos(authorityInfo);
	}

	@Override
	public void updateAuthority(AuthorityInfo authorityInfo) {
		userManagerDao.updateAutorityInfo(authorityInfo);
	}

	@Override
	public void deleteAuthority(Integer id) {
		userManagerDao.deleteAuthorityInfo(id);
	}

	@Override
	public void insertRoleAuthInfo(RoleAuthInfo roleAuthInfo) {
		userManagerDao.insertRoleAuthInfo(roleAuthInfo);
	}

	@Override
	public List<RoleAuthInfo> getAllRoleAuthInfos(RoleAuthInfo roleAuthInfo) {
		return userManagerDao.getAllRoleAuthInfos(roleAuthInfo);
	}

	@Override
	public RoleAuthInfo getRoleAuthInfoById(Integer roleAuthId) {
		return userManagerDao.getRoleAuthInfoById(roleAuthId);
	}

	@Override
	public void deleteRoleAuthInfo(Integer roleAuthId) {
		userManagerDao.deleteRoleAuthInfo(roleAuthId);
	}

	@Override
	public void insertUserRoleInfo(UserRoleInfo userRoleInfo) {
		userManagerDao.insertUserRoleInfo(userRoleInfo);
	}

	@Override
	public UserRoleInfo getUserRoleInfoById(Integer userRoleId) {
		return userManagerDao.getUserRoleInfoById(userRoleId);
	}

	@Override
	public void deleteUserRoleInfo(Integer userRoleId) {
		userManagerDao.deleteUserRoleInfo(userRoleId);
	}

	@Override
	public List<UserRoleInfo> getAllUserRoleInfos(UserRoleInfo userRoleInfo) {
		return userManagerDao.getAllUserRoleInfos(userRoleInfo);
	}

	@Override
	public List<String> getAuthTop(Set<AuthorityInfo> authorityInfos) {
		List<String> allAuthCodes = new ArrayList<>();
		for (AuthorityInfo auth : authorityInfos) {
			allAuthCodes.add(auth.getAuthCode());
		}
		return allAuthCodes;
	}

	public List<UserInfo> getAllUsersByAuth(Set<AuthorityInfo> auths, UserInfo userInfo) {
		Integer firmType = userInfo.getFirmInfo().getFirmType();
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		List<String> codes = this.getAuthTop(auths);
		UserInfo userSelect = new UserInfo();
		if (firmType == 1) {
			// 运营商，根据不同的角色查看用户
			userSelect.setFirmId(userInfo.getFirmInfo().getFirmId());
			if (codes.contains("001")) {
				// 公司管理员,可查看公司所有UserInfo
			} else if (codes.contains("00101")) {
				// 运营商小组管理员,可查看组内成员,即parentUserId==当前userId的用户
				userSelect.setParentUserId(userInfo.getUserId());
			} else {
				// 普通管理员
				userSelect.setUserId(userInfo.getUserId());
			}

		} else if (firmType == 2) {
			// 查看厂商用户
			userSelect.setFirmId(userInfo.getFirmInfo().getFirmId());
		}
		userInfos = this.getAllUserInfos(userSelect);
		return userInfos;
	}

	@Override
	public Boolean alreadyUser(UserInfo userInfo) {
		UserInfo user = new UserInfo();
		user.setUserNo(userInfo.getUserNo());
		user.setFirmId(userInfo.getFirmId());
		List<UserInfo> userInfos = this.getAllUserInfos(user);
		user = new UserInfo();
		user.setFirmId(userInfo.getFirmId());
		user.setUserName(userInfo.getUserName());
		List<UserInfo> userRepqteName = this.getAllUserInfos(user);
		if (userInfos.size() > 0 || userRepqteName.size() > 0)
			return false;
		return true;
	}

	@Override
	public void deletUserRoleByUserId(Integer userId) {
		userManagerDao.deletUserRoleByUserId(userId);
	}

	@Override
	public List<UserInfo> getAllUserAuthByFirmId(Integer firmId) {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		Set<Integer> userIds = new HashSet<Integer>();

		RoleAuthInfo roleAuth = new RoleAuthInfo();
		UserRoleInfo userRole = new UserRoleInfo();
		UserInfo user = new UserInfo();
		user.setFirmId(firmId);
		List<UserInfo> users = userManagerDao.getAllUsers(user);

		for (UserInfo userInfo : users) {
			userRole.setUserId(userInfo.getUserId());
			Set<Integer> roleIds = new HashSet<Integer>();
			List<UserRoleInfo> userRoleInfos = userManagerDao.getAllUserRoleInfos(userRole);
			for (UserRoleInfo i : userRoleInfos) {
				if (i == null)
					continue;
				if (!roleIds.contains(i.getRoleInfo().getRoleId())) {
					roleIds.add(i.getRoleInfo().getRoleId());
					// 查询当前角色是否具有操作员权限
					roleAuth.setRoleId(i.getRoleInfo().getRoleId());
					for (RoleAuthInfo j : userManagerDao.getAllRoleAuthInfos(roleAuth)) {
						if (j == null)
							continue;
						if (j.getAuthorityInfo().getAuthCode().equals("0010101")) {
							if (!userIds.contains(userInfo.getUserId())) {
								userIds.add(userInfo.getUserId());
								userInfos.add(userInfo);
								continue;
							}
						}
					}
					continue;
				}
			}
		}

		return userInfos;
	}

	@Override
	public boolean checkFirmStatus(FirmInfo firmInfo) {
		boolean result = true;
		FirmInfo fInfo = firmDao.getAllFirmInfos(firmInfo).get(0);
		String endTime = fInfo.getEndTime();
		long now = System.currentTimeMillis();
		if (endTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
			try {
				long time = sdf.parse(endTime).getTime();
				if (time <= now && fInfo.getFirmStatus() == 1) {// 判定是否超时
					firmInfo.setFirmStatus(0);
					firmDao.updateFirm(firmInfo);
					result = false;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
