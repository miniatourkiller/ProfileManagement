package com.management.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Admin {
	@Id
private String id;
private String username;
private String idnumber;
private String firstname;
private String secondname;
private String sirname;
private String deisignation;
private String contact;

public String getContact() {
	return contact;
}
public void setContact(String contact) {
	this.contact = contact;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getIdnumber() {
	return idnumber;
}
public void setIdnumber(String idnumber) {
	this.idnumber = idnumber;
}
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getSecondname() {
	return secondname;
}
public void setSecondname(String secondname) {
	this.secondname = secondname;
}
public String getSirname() {
	return sirname;
}
public void setSirname(String sirname) {
	this.sirname = sirname;
}
public String getDeisignation() {
	return deisignation;
}
public void setDeisignation(String deisignation) {
	this.deisignation = deisignation;
}

}
