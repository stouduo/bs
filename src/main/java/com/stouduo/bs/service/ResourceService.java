package com.stouduo.bs.service;

import com.stouduo.bs.model.Resource;
import com.stouduo.bs.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public void insertResource(Resource resource) {
        resourceRepository.save(resource);
    }
}
