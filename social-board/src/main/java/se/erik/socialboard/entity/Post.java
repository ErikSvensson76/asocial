package se.erik.socialboard.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "topic","author"})
public class Post implements Comparable<LocalDateTime>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String authorAlias;
	private LocalDateTime timeStamp;
	
	@Embedded
	private Content content = new Content();
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "topic_id")
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "user_id")
	private User author;
	
	
	public Post(String authorAlias) {
		this();
		this.authorAlias = authorAlias;
	}

	public Post() {
		this.timeStamp = LocalDateTime.now();
	}

	@JsonIgnore
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public long getId() {
		return id;
	}

	public String getAuthorAlias() {
		return authorAlias;
	}	

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}	

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}	

	public void setTextContent(String textContent) {
		content.setTextContent(textContent);
	}

	public String getTextContent() {
		return content.getTextContent();
	}

	public void setLatestActivity() {
		topic.setLatestActivity(this.timeStamp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorAlias == null) ? 0 : authorAlias.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (authorAlias == null) {
			if (other.authorAlias != null)
				return false;
		} else if (!authorAlias.equals(other.authorAlias))
			return false;
		if (id != other.id)
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}

	@Override
	public int compareTo(LocalDateTime otherDateTime) {
		return getTimeStamp().compareTo(otherDateTime);
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", authorAlias=" + authorAlias + ", timeStamp=" + timeStamp + ", content=" + content
				+ "]";
	}
	
	
	
	

}
