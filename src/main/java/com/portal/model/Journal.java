package com.portal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class Journal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@OneToOne
	@JoinColumn(name="CONTENT_ID")
	private JournalContent content;
	
	@ManyToOne
	@JoinColumn(name="PUBLISHER_ID")
	@NotNull
	private User publisher;

	@Version
	private Long version;

	public Journal() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JournalContent getContent() {
		return content;
	}

	public void setContent(JournalContent content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Journal [id=" + id + ", name=" + name + "]";
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}
}
