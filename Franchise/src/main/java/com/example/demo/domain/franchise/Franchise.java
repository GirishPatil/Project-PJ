package com.example.demo.domain.franchise;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="franchise")
public class Franchise implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2585956794138390018L;

	@Id
	@Column(name="franchiseId")
	private String franchiseId;
	
    @Column(name="fullname")
	private String fullname;
	
	@Column(name="email")
	private String email;
	
	
	@Column(name="phoneno")
	private String phoneno;
	
	@Column(name="password")
	private String password;
	
	@Column(name="isActive")
	private boolean isActive;
	
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getfullname() {
		return fullname;
	}

	public   void setfullname(String fullname) {
		this.fullname = fullname;
	}

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public String getphoneno() {
		return phoneno;
	}

	public  void setphoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getfranchiseId() {
		return franchiseId;
	}

	public   void setfranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}
	public Franchise(String franchiseId, String fullname, String email, String phoneno, String password,
			boolean isActive) {
		super();
		this.franchiseId = franchiseId;
		this.fullname = fullname;
		this.email = email;
		this.phoneno = phoneno;
		this.password = password;
		this.isActive = isActive;
	}
	public Franchise() {
		super();
	}
	 Franchise(String franchiseId, String fullname, String email, String phoneno, String password) {
			super();
			this.franchiseId = franchiseId;
			this.fullname = fullname;
			this.email = email;
			this.phoneno = phoneno;
			this.password = password;
		}

	


	
	

	

}
