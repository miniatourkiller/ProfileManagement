package com.management.services;

import org.springframework.web.multipart.MultipartFile;

import com.management.documents.Admin;
import com.management.documents.Landlord;
import com.management.documents.Tenant;

import jakarta.servlet.http.HttpServletResponse;

public interface Actions {
	public void createTenant(String data, MultipartFile file, HttpServletResponse response);
	public void createLandLord(String data, MultipartFile file, HttpServletResponse response);
	public void createAdmin(Admin admin,  HttpServletResponse response);
	public void updateTenant(String username, Tenant tenant,  HttpServletResponse response);
	public void updateOwner(String username, Landlord owner,  HttpServletResponse response);
	public void updateAdmin(String username, Admin admin,  HttpServletResponse response);
	public void addUnit(String username, String propertyName,String unitName, HttpServletResponse response);
	public void removeUnit(String username,  HttpServletResponse response);
	public void getProfilePic(HttpServletResponse response, String username, String who);
	public void updateProfilePic(String who,String username, MultipartFile file, HttpServletResponse response);
	public boolean isProfileSet( String username, String who);
	public Tenant getTenant(String username,  HttpServletResponse response);
	public Landlord getLandlord(String username,  HttpServletResponse response);
	public Admin getAdmin(String username,  HttpServletResponse response);
}
