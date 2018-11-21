package se.erik.socialboard.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CategoryForm {
	
	@NotBlank
	@Size(min = 2, max = 255)
	private String categoryName;
	@NotBlank
	@Size(min = 2, max = 255)
	private String description;
	@Positive
	private long creatorId;	
	
	public CategoryForm(String categoryName, String description, long creatorId) {
		this.categoryName = categoryName;
		this.description = description;
		this.creatorId = creatorId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}	

}
