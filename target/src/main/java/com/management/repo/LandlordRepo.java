package com.management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.management.documents.Landlord;

public interface LandlordRepo extends MongoRepository<Landlord, String>{
public Landlord findByUsername(String username);
}
