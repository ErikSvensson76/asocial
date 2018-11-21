package se.erik.socialboard.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false)
	private String alias;
	private LocalDate signUpDate;
	private boolean active;
	private LocalDate deactivationDate;	
		
	public User(String email, String firstName, String lastName, String alias, LocalDate signupDate) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.alias = alias;
		this.signUpDate = signupDate;
		this.active = true;
	}
	
	protected User() {}

	public String getEmail() {return email;}

	public void setEmail(String email) {this.email = email;}

	public String getFirstName() {return firstName;}

	public void setFirstName(String firstName) {this.firstName = firstName;}

	public String getLastName() {return lastName;}

	public void setLastName(String lastName) {this.lastName = lastName;}

	public String getAlias() {return alias;}

	public void setAlias(String alias) {this.alias = alias;}

	public long getId() {return id;}
	
	public LocalDate getSignUpDate() {return signUpDate;}
	
	public String getFullName() {return getFirstName() + " " + getLastName();}
	
	public boolean isActive() {return active;}

	public void setActive(boolean active) {
		if(!active) {
			deactivationDate = LocalDate.now();
		}else {
			deactivationDate = null;
		}
		this.active = active;
	}	
	
	public LocalDate getDeactivationDate() {
		return deactivationDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", alias=" + alias + ", signUpDate=" + signUpDate
				+ ", name= " + getFullName() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
