package se.erik.socialboard.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "category","topicCreator"})
public class Topic implements Comparable<LocalDateTime>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDateTime latestActivity;
	@Column(nullable = false)
	private String topicName;
	private String creatorAlias;
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "category_id")
	private Category category;	
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "user_id")
	private User topicCreator;
	
	public Topic(LocalDateTime latestActivity, String topicName) {
		this.latestActivity = latestActivity;
		this.topicName = topicName;
	}
	
	protected Topic() {}

	public LocalDateTime getLatestActivity() {
		return latestActivity;
	}

	public void setLatestActivity(LocalDateTime latestActivity) {
		this.latestActivity = latestActivity;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getCreatorAlias() {
		return creatorAlias;
	}

	public void setCreatorAlias(String creatorAlias) {
		this.creatorAlias = creatorAlias;
	}

	public long getId() {
		return id;
	}	

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getTopicCreator() {
		return topicCreator;
	}

	public void setTopicCreator(User topicCreator) {
		this.topicCreator = topicCreator;
		setCreatorAlias(topicCreator.getAlias());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((latestActivity == null) ? 0 : latestActivity.hashCode());
		result = prime * result + ((topicName == null) ? 0 : topicName.hashCode());
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
		Topic other = (Topic) obj;
		if (id != other.id)
			return false;
		if (latestActivity == null) {
			if (other.latestActivity != null)
				return false;
		} else if (!latestActivity.equals(other.latestActivity))
			return false;
		if (topicName == null) {
			if (other.topicName != null)
				return false;
		} else if (!topicName.equals(other.topicName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Topic [id=" + id + ", latestActivity=" + latestActivity + ", topicName=" + topicName + ", creatorAlias="
				+ creatorAlias + "]";
	}

	@Override
	public int compareTo(LocalDateTime otherDateTime) {
		return this.latestActivity.compareTo(otherDateTime);
	}
	
	
	
	
	
	

}
