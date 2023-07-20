package com.management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.management.documents.Tenant;

public interface TenantsRepo extends MongoRepository<Tenant, String>{
public Tenant findByUsername(String username);
}
