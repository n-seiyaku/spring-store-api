package com.seikan.store.repositories;

import com.seikan.store.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Profile, Long> {
}
