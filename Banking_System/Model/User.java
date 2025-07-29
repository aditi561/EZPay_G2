package com.ezpay.bank.model;
import java.util.List;
public class User {
	int userId;	
	String userName;
	String emailId;
	List<String> accounts;
	
	public User() {}
	
	public User(int userId, String userName, String emailId, List<String> accounts) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.emailId = emailId;
		this.accounts = accounts;
	}
	
	//getters
	public int getUserID() {return userId;}
	public String getUserName() {return userName;}
	public String getEmailId() {return emailId;}
	public List<String> getAccounts(){return accounts;}
	
	//setters
	public void setUserId(int userId) {this.userId=userId;}
	public void setUserName(String userName) {this.userName=userName;}
	public void setEmailId(String emailId) {this.emailId=emailId;}
	public void setAccounts(List<String> accounts) {this.accounts=accounts;}
		
	
}
