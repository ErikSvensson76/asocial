package se.erik.socialboard.discarded;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Board {
	
	private long id;
	private String boardName;
	private LocalDate createdOn;
	
	private Set<BoardMember> members = new HashSet<>(); 
	
	public Board(String boardName,LocalDate creationDate) {
		this.boardName = boardName;
		this.createdOn = creationDate;
	}
	
	protected Board() {}

	public String getBoardName() {return boardName;}

	public void setBoardName(String boardName) {this.boardName = boardName;}

	public long getId() {return id;}

	public LocalDate getCreatedOn() {return createdOn;}
	
	public Set<BoardMember> getMembers() {
		return members;
	}

	public void setMembers(Set<BoardMember> members) {
		this.members = members;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardName == null) ? 0 : boardName.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Board other = (Board) obj;
		if (boardName == null) {
			if (other.boardName != null)
				return false;
		} else if (!boardName.equals(other.boardName))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", boardName=" + boardName + ", createdOn=" + createdOn + "]";
	}
	
	
	
	

}
