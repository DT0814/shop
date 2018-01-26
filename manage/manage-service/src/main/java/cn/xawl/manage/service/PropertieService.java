package cn.xawl.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertieService {
    @Value("${REPORSITORY_PATH}")
    public String REPORSITORY_PATH;

    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}
