package com.management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.management.documents.Admin;

public interface AdminRepo extends MongoRepository<Admin, String>{
public Admin findByUsername(String username);
}
