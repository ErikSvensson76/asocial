package se.erik.socialboard.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Content {
	
	@Column(length = 2048)
	private String textContent;	
	

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	@Override
	public String toString() {
		return "Content [textContent=" + textContent + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((textContent == null) ? 0 : textContent.hashCode());
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
		Content other = (Content) obj;
		if (textContent == null) {
			if (other.textContent != null)
				return false;
		} else if (!textContent.equals(other.textContent))
			return false;
		return true;
	}
	
	

}
