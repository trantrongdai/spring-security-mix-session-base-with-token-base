package com.fishpro.securityjwt.domain;

import java.io.Serializable;
import java.util.List;

import com.fishpro.securityjwt.model.User;

public class UsersResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UsersResponse() {

	}

	public UsersResponse(List<User> users) {
		this.users = users;
	}

}