package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public List<User> listAll() {
		return userRepository.findAll();
	}
	
	public List<Role> listRoles() {
		return roleRepository.findAll();
	}

	public void save(User user) {
		encodePassword(user);
		userRepository.save(user);
		
	}
	
	public void encodePassword(User user) {
		String encoded = passwordEncoder.encode(user.getPassword());
		user.setPassword(encoded);
	}
	
	public boolean isEmailUnique(String email) {
		User userByEmail = userRepository.getUserByEmail(email);
		return userByEmail == null;
	}
	
	
}
