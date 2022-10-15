package com.manong.domain.service;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    ResponseBody uploadImg(MultipartFile file);
}
