package com.management.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.management.documents.Admin;
import com.management.documents.Landlord;
import com.management.documents.Tenant;
import com.management.repo.AdminRepo;
import com.management.repo.LandlordRepo;
import com.management.repo.TenantsRepo;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ProfileServices implements Actions{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	TenantsRepo tenantRepo;
	@Autowired
	LandlordRepo ownerRepo;
	@Autowired
	AdminRepo adminRepo;
	JsonMapper mapper = new JsonMapper();
	Map< String, String> map;
	public void notFound(String message,HttpServletResponse response)
	{
		response.setContentType("application/json");
		map = new HashMap<>();
		map.put("result", "failed");
		map.put("error", message+" not found");
		map.put("message", "an object was no found in the database");
		try {
			mapper.writeValue(response.getOutputStream(), map);
		}catch(Exception e) {
			logger.warn(e.getMessage());
		}
	}
	public void exists(String message,HttpServletResponse response)
	{
		response.setContentType("application/json");
		map = new HashMap<>();
		map.put("result", "failed");
		map.put("error", "already exists");
		map.put("message", message+" with the details already exists");
		try {
			mapper.writeValue(response.getOutputStream(), map);
		}catch(Exception e) {
			logger.warn(e.getMessage());
		}
	}
	public void success(HttpServletResponse response) {
		response.setContentType("application/json");
		map = new HashMap<>();
		map.put("result", "success");
		map.put("message", "action completed successfully");
		try {
			mapper.writeValue(response.getOutputStream(), map);
		}catch(Exception e) {
			logger.warn(e.getMessage());
		}
	}
	
	//Class to send userDetails to the userDetails endPoint
	Map<String, String> obj;
	@Autowired
	RestExchanger exchanger;
	@Autowired
	Mapper myMapper;
	@Autowired
	FileUploader fileUploader;
	@Override
	public void createTenant(String data, MultipartFile file, HttpServletResponse response) {
		Tenant tenant = myMapper.mapData(data, Tenant.class);
		Tenant tenant1 = tenantRepo.findByUsername(tenant.getUsername());
		if(tenant1 != null) {
			this.exists("Tenant", response);
			
		}else {
			tenant.setProfilePic(fileUploader.uploadFile(file));
			obj = new HashMap<>();
			obj.put("username", tenant.getUsername());
			String url = "http://localhost:8085/adddetails";
			exchanger.postData(url, obj);
			tenantRepo.save(tenant);
			this.success(response);
		}
	}

	@Override
	public void createLandLord(String data, MultipartFile file, HttpServletResponse response) {
		Landlord landlord = myMapper.mapData(data, Landlord.class);
		if( ownerRepo.findByUsername(landlord.getUsername()) != null) {
			this.exists("Tenant", response);
			
		}else {
			obj = new HashMap<>();
			if(file != null) {
				landlord.setProfilePic(fileUploader.uploadFile(file));
			}
			obj.put("username", landlord.getUsername());
//			String url = "http://localhost:8085/adddetails";
//			exchanger.postData(url, obj);
			ownerRepo.save(landlord);
			this.success(response);
		}
	}

	@Override
	public void createAdmin(Admin admin, HttpServletResponse response) {
		Admin admin1 = adminRepo.findByUsername(admin.getUsername());
		if(admin1 != null) {
			this.exists("Admin", response);
			
		}else {
			adminRepo.save(admin);
			this.success(response);
		}
	}


	@Override
	public void updateTenant(String username, Tenant tenant, HttpServletResponse response) {
		Tenant tenant1 = tenantRepo.findByUsername(username);
		if(tenant1 == null) {
			this.notFound("Tenant", response);
		}else {
			tenant.setId(tenant1.getId());
			tenant.setUsername(username);
			tenant.setUnitName(tenant1.getUnitName());
			tenantRepo.save(tenant);
			this.success(response);
		}
	}

	@Override
	public void updateOwner(String username, Landlord owner, HttpServletResponse response) {
			Landlord landlord = ownerRepo.findByUsername(username);
			if(landlord == null) {
				this.notFound("landlord", response);
			}else {
				owner.setId(landlord.getId());
				owner.setProfilePic(landlord.getProfilePic());
				ownerRepo.save(owner);
				this.success(response);
			}
	}

	@Override
	public void updateAdmin(String username, Admin admin, HttpServletResponse response) {
		Admin admin1 = adminRepo.findByUsername(username);
		if(admin1 == null) {
			this.notFound("landlord", response);
		}else {
			admin.setId(admin1.getId());
			adminRepo.save(admin);
			this.success(response);
		}
	}

	//adding tenant to a unit
	@Override
	public void addUnit(String username, String propertyName, String unitName, HttpServletResponse response) {
		Tenant tenant = tenantRepo.findByUsername(username);
		if(tenant == null) {
			this.notFound(username, response);
		}else {
			String url = "http://localhost:8081/unitExists/"+unitName+"/"+propertyName;
			if(exchanger.getData(url).getBody().equals("true")) {
				tenant.setUnitName(unitName);
				tenant.setPropertyName(propertyName);
				String url2 = "http://localhost:8081/addTenant/"+propertyName;
				exchanger.postData(url2, null);
				tenantRepo.save(tenant);
				this.success(response);
				
			}else {
				this.notFound("unit", response);
			}
		}
	}

	@Override
	public void removeUnit(String username, HttpServletResponse response) {
		Tenant tenant = tenantRepo.findByUsername(username);
		if(tenant == null) {
			this.notFound("tenant", response);
		}else {
			String url2 = "http://localhost:8081/removeTenant/"+tenant.getPropertyName();
			exchanger.postData(url2, null);
			tenant.setUnitName(null);
			tenant.setPropertyName(null);
			tenantRepo.save(tenant);
		}
		
	}
	@Autowired
	ResourceLoader resourceLoader;
	@Override
	public void getProfilePic(HttpServletResponse response, String username, String who) {

	if(who.equals("tenant")) {
		Tenant tenant = tenantRepo.findByUsername(username);
		if(tenant == null) {
			this.notFound(who, response);
		}else {
			if(!fileUploader.getExtension(tenant.getProfilePic()).equals("00.err")){
Resource resource = resourceLoader.getResource("classpath:profilePics/"+tenant.getProfilePic());
				try {

					StreamUtils.copy(resource.getInputStream(), response.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				this.notFound("file", response);
			}

		}
		
	}else if(who.equals("landlord")) {
		Landlord landlord = ownerRepo.findByUsername(username);
		if(landlord == null) {
			this.notFound(who, response);
		}else {
			String ext = fileUploader.getExtension(landlord.getProfilePic());
		if(!ext.equals("00.err")) {
		Resource resource = resourceLoader.getResource("classpath:profilePics/"+landlord.getProfilePic());
			try {
				StreamUtils.copy(resource.getInputStream(), response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			this.notFound("file", response);
		}
		}
	}
	
	
	}
	@Override
	public void updateProfilePic(String who,String username, MultipartFile file, HttpServletResponse response) {
		if(who.equals("tenant")) {
			Tenant tenant = tenantRepo.findByUsername(username);
			if(tenant == null) {
				this.notFound(who, response);
			}else {
				if(tenant.getProfilePic() != null) {
					if(!fileUploader.getExtension(tenant.getProfilePic()).equals("00.err")) {
						fileUploader.deleteFile(tenant.getProfilePic());
					}
				}
					
					tenant.setProfilePic(fileUploader.uploadFile(file));
					tenantRepo.save(tenant);
					this.success(response);
			}
		}else if(who.equals("landlord")) {
			Landlord landlord = ownerRepo.findByUsername(username);
			if(landlord == null) {
				this.notFound(who, response);
			}else {
				if(landlord.getProfilePic() != null) {
					if(!fileUploader.getExtension(landlord.getProfilePic()).equals("00.err")) {
						fileUploader.deleteFile(landlord.getProfilePic());
					}
				}
					landlord.setProfilePic(fileUploader.uploadFile(file));
					ownerRepo.save(landlord);
					this.success(response);
			}
		}
	}
	@Override
	public boolean isProfileSet( String username, String who) {
		if(who.equals("tenant")) {
			Tenant tenant = tenantRepo.findByUsername(username);
		if(tenant == null) {
			return false;
		}else {
			if(tenant.getIdnumber() == null) {
				return false;
			}
			return true;
		}
		}else if(who.equals("landlord")) {
			Landlord landlord = ownerRepo.findByUsername(username);
			if(landlord == null) {
				return false;
			}else {
				if(landlord.getIdnumber() == null) {
					return false;
				}
				return true;
			}
		}else if(who.equals("admin")) {
			Admin admin =adminRepo.findByUsername(username);
			if(admin == null ) {
				return false;
			}else {
				if(admin.getIdnumber() == null) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	@Override
	public Tenant getTenant(String username,  HttpServletResponse response) {
		Tenant tenant = tenantRepo.findByUsername(username);
		if(tenant == null) {
			this.notFound(username, response);
			return null;
		}else {
			return tenant;
		}
	}
	@Override
	public Landlord getLandlord(String username,  HttpServletResponse response) {
		Landlord landlord = ownerRepo.findByUsername(username);
		if(landlord == null) {
			this.notFound(username, response);
			return null;
		}else {
			return landlord;
		}
	}
	@Override
	public Admin getAdmin(String username,  HttpServletResponse response) {
		Admin admin = adminRepo.findByUsername(username);
		if(admin == null) {
			this.notFound(username, response);
			return null;
		}else {
			return admin;
		}
	}

	
}
