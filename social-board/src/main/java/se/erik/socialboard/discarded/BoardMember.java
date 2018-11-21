package se.erik.socialboard.discarded;

import java.time.LocalDate;

import se.erik.socialboard.entity.User;

public class BoardMember {
	
	private long id;
	private Board board;
	private User member;
	private int numberOfPosts;
	private LocalDate joinDate;
	
	public Board getBoard() {return board;}
	
	public void setBoard(Board board) {this.board = board;}
	
	public User getMember() {return member;}
	
	public void setMember(User member) {this.member = member;}
	
	public int getNumberOfPosts() {return numberOfPosts;}
	
	public void setNumberOfPosts(int numberOfPosts) {this.numberOfPosts = numberOfPosts;}
	
	public LocalDate getJoinDate() {return joinDate;}
	
	public void setJoinDate(LocalDate joinDate) {this.joinDate = joinDate;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((joinDate == null) ? 0 : joinDate.hashCode());
		result = prime * result + numberOfPosts;
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
		BoardMember other = (BoardMember) obj;
		if (id != other.id)
			return false;
		if (joinDate == null) {
			if (other.joinDate != null)
				return false;
		} else if (!joinDate.equals(other.joinDate))
			return false;
		if (numberOfPosts != other.numberOfPosts)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BoardMember [id=" + id + ", numberOfPosts=" + numberOfPosts + ", joinDate=" + joinDate + "]";
	}
	
		
	

}
