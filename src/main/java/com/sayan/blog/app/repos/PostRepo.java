package com.sayan.blog.app.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sayan.blog.app.entities.Category;
import com.sayan.blog.app.entities.Post;
import com.sayan.blog.app.entities.User;

public interface PostRepo extends JpaRepository<Post, String> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);

}
