package com.vending.platform.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * T_Authority_Info映射表：用户权限映射表
 * 
 * @author Miley_Ren
 */
public class AuthorityInfo implements Serializable {
	/** 序列号 */
	private static final long serialVersionUID = -1610238727257309621L;
	/** 权限ID */
	private Integer authId;
	/** 权限名 */
	private String authName;
	/** 权限编码 */
	private String authCode;
	private String authDesc;
	private Integer authType;
	/** 所属公司 */
	private Integer firmId;
	/** 操作者ID */
	private Integer operateId;
	/** 操作时间 */
	private Date operateDate;

	private List<RoleInfo> roleInfos;

	public AuthorityInfo() {
		super();
	}

	public Integer getAuthId() {
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getAuthDesc() {
		return authDesc;
	}

	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public Integer getOperateId() {
		return operateId;
	}

	public void setOperateId(Integer operateId) {
		this.operateId = operateId;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public List<RoleInfo> getRoleInfos() {
		return roleInfos;
	}

	public void setRoleInfos(List<RoleInfo> roleInfos) {
		this.roleInfos = roleInfos;
	}

	@Override
	public String toString() {
		return "AuthorityInfo [authId=" + authId + ", authName=" + authName + ", authCode=" + authCode + ", authDesc="
				+ authDesc + ", authType=" + authType + ", firmId=" + firmId + ", operateId=" + operateId
				+ ", operateDate=" + operateDate + ", roleInfos=" + roleInfos + "]";
	}

}
