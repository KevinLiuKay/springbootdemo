package com.kevin.service.pub;

import com.kevin.common.utils.JsonResult;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
    JsonResult uploadSinglePicture(MultipartFile file);
}
