package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userDuong = new User("duong@gmail.com", "123456", "Duong", "Nguyen");
		userDuong.addRole(roleAdmin);
		User savedUser = userRepository.save(userDuong);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userMusk = new User("musk@gmail.com", "123456", "Elon", "Musk");
		Role roleEditor = new Role(3);
		Role roleAssitant = new Role(5);
		
		userMusk.addRole(roleEditor);
		userMusk.addRole(roleAssitant);
		
		User savedUser = userRepository.save(userMusk);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void listAllUsers() {
		List<User> users = userRepository.findAll();
		
		users.forEach(user -> System.out.println(user));
	}
	
	
	@Test
	public void testGetUserById() {
		User user1 = userRepository.findById(1).get();
		System.out.println(user1);
		assertThat(user1).isNotNull();
		
		
	}
	@Test
	public void testUpdateUserDetails() {
		User user1 = userRepository.findById(1).get();
		user1.setEnabled(true);
		user1.setEmail("duongtest@gmail.com");
		
		userRepository.save(user1);
	}
	
	@Test
	public void testUpdateRole() {
		User user1 = userRepository.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSales = new Role(2);
		
		user1.getRoles().remove(roleEditor);
		user1.addRole(roleSales);
		
		userRepository.save(user1);
	}
	
	@Test
	public void deleteUser() {
		User user1 = userRepository.findById(2).get();
		userRepository.delete(user1);
	}
	
	
	@Test
	public void testGetUserByEmail() {
		String email = "code@gmail.com";
		User user = userRepository.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
