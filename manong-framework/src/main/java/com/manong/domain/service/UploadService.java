package com.manong.domain.service;

import com.manong.domain.ResponseResult;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    ResponseResult uploadImg(MultipartFile file);
}
