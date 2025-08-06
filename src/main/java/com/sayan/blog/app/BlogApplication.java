package com.sayan.blog.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BlogApplication {
	
//	@Autowired
//	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		try {
//
//			Role role = new Role();
//			role.setRoleId(AppConstants.ADMIN_USER);
//			role.setName("ROLE_ADMIN");
//
//			Role role1 = new Role();
//			role1.setRoleId(AppConstants.NORMAL_USER);
//			role1.setName("ROLE_NORMAL");
//
//			List<Role> roles = List.of(role, role1);
//
//			List<Role> result = this.roleRepo.saveAll(roles);
//
//			result.forEach(r -> {
//				System.out.println(r.getName());
//			});
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//	}

}
