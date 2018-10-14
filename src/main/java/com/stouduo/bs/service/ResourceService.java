package com.stouduo.bs.service;

import com.stouduo.bs.model.Resource;
import com.stouduo.bs.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public Resource insertResource(Resource resource) {
        return resourceRepository.save(resource);
    }
}
