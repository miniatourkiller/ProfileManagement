package com.management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.management.documents.Admin;
import com.management.documents.Landlord;
import com.management.documents.Tenant;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MyController {
	@Autowired
	Actions profileServices;
@RequestMapping(value = "addTenant", method = RequestMethod.POST)
public void addTenant(@RequestPart("data") String data,@RequestPart(name= "file", required = false) MultipartFile file, HttpServletResponse response){
	profileServices.createTenant(data, file, response);
}
@RequestMapping(value = "addLandlord", method = RequestMethod.POST)
public void addLandlord(@RequestPart("data")String data,@RequestPart(name= "file", required=false) MultipartFile file, HttpServletResponse response){
	profileServices.createLandLord(data, file, response);
}
@RequestMapping(value = "isProfileSet/{username}/{role}")
public boolean isProfileSet(@PathVariable("username")String username,@PathVariable("role") String role) {
	return profileServices.isProfileSet(username, role);
}
@RequestMapping(value = "updateTenant/{username}", method = RequestMethod.POST)
public void updateTenant(@PathVariable("username")String username,@RequestBody Tenant tenant, HttpServletResponse response) {
	profileServices.updateTenant(username, tenant, response);
}
@RequestMapping(value = "updateLandlord/{username}", method = RequestMethod.POST)
public void updateLandlord(@PathVariable("username")String username,@RequestBody Landlord landlord, HttpServletResponse response) {
	profileServices.updateOwner(username, landlord, response);
}
@RequestMapping(value = "updateAdmin/{username}", method = RequestMethod.POST)
public void updateAdmin(@PathVariable("username")String username,@RequestBody Admin admin, HttpServletResponse response) {
	profileServices.updateAdmin(username, admin, response);
}
@RequestMapping(value = "getTenant/{username}", method = RequestMethod.GET)
public Tenant getTenant(@PathVariable("username")String username,  HttpServletResponse response) {
	return profileServices.getTenant(username, response);
}
@RequestMapping(value = "getAdmin/{username}", method = RequestMethod.GET)
public Admin getAdmin(@PathVariable("username")String username,  HttpServletResponse response) {
	return profileServices.getAdmin(username, response);
}
@RequestMapping(value = "getLandlord/{username}", method = RequestMethod.GET)
public Landlord getLandLord(@PathVariable("username")String username,  HttpServletResponse response) {
	return profileServices.getLandlord(username, response);
}
@RequestMapping(value = "updateProfilePic/{username}/{who}", method = RequestMethod.POST)
public void updateProfilePic(@PathVariable("username")String username, @PathVariable("who") String who,@RequestPart("file") MultipartFile file, HttpServletResponse response) {
	profileServices.updateProfilePic(who, username, file, response);
}

@RequestMapping(value = "getProfilePic/{username}/{who}", method = RequestMethod.GET)
public void getProfilePic(@PathVariable("username")String username, @PathVariable("who") String who, HttpServletResponse response) {
	profileServices.getProfilePic(response, username, who);
}
}