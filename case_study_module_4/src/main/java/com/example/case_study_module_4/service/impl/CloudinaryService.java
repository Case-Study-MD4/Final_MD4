package com.example.case_study_module_4.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dhlp7pnpn",
                "api_key", "411312418664949",
                "api_secret", "3YoWzAE9KWP-WfmTbXaqtugg6YU",
                "secure", true
        ));
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder", "foods"));
        return (String) uploadResult.get("secure_url"); // ✅ trả về URL Cloudinary
    }
}
