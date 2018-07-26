//package com.kevin.service.pub;
//
//import com.kevin.model.PubFile;
//import com.kevin.service.ICommonService;
//import org.springframework.web.multipart.MultipartFile;
//
//
//
//public interface IPubFileService extends ICommonService<PubFile> {
//
//    /**
//    * 文件表记录并上传文件至服务器
//    * @param multipartFile 文件对象
//    * @param folderName 文件夹
//    * @return
//    * @throws Exception
//    * @throws
//     */
//    public PubFile savePubFile(MultipartFile multipartFile, String folderName);
//
//    /**
//    * 删除pubfile对象及相应图片
//    * @param fileId
//    * @return
//    * @throws
//     */
//    public int deletePubFile(String fileId);
//
//
//}
