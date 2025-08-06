package com.sayan.blog.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {

	@Id
	@Column(nullable = false)
	private String commentId;

	@Column(nullable = false, length = 1000)
	private String content;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

}
