package se.erik.socialboard.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TopicForm {
	
	@NotBlank
	@Size(min = 2, max = 255)
	private String topicName;
	@Positive
	private long categoryId;
	@Positive
	private long topicCreatorId;
	
	public TopicForm(String topicName, long categoryId, long topicCreatorId) {
		this.topicName = topicName;
		this.categoryId = categoryId;
		this.topicCreatorId = topicCreatorId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getTopicCreatorId() {
		return topicCreatorId;
	}

	public void setTopicCreatorId(long topicCreatorId) {
		this.topicCreatorId = topicCreatorId;
	}	

}
