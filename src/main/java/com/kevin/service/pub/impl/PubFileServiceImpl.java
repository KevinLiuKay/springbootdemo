//package com.kevin.service.pub.impl;
//
//
//import com.kevin.common.GlobalConstant.GlobalConstant;
//import com.kevin.common.core.GeneralMethod;
//import com.kevin.common.utils.UUIDUtil;
//import com.kevin.dao.mapper.PubFileMapper;
//import com.kevin.model.PubFile;
//import com.kevin.service.pub.IPubFileService;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@Service
//@Transactional(rollbackFor=Exception.class)
//public class PubFileServiceImpl implements IPubFileService {
//
//    private static Logger logger = LoggerFactory.getLogger(PubFileServiceImpl.class);
//
//    @Resource
//    private PubFileMapper fileMapper;
//    @Resource
//    private FileServiceImpl fileService;
//
//
//    @Override
//    public int save(PubFile pubFile) {
//        if (pubFile.getFileId() == null) {
//        		pubFile.setFileId(UUIDUtil.getUUID());
//            GeneralMethod.setRecordInfo(pubFile, true);
//            return fileMapper.insertSelective(pubFile);
//        }else{
//            GeneralMethod.setRecordInfo(pubFile, false);
//            return fileMapper.updateByPrimaryKeySelective(pubFile);
//        }
//    }
//
//    @Override
//    public int logicallyDeleteById(String id) {
//        if(id == null){
//            return GlobalConstant.ZERO;
//        }
//        PubFile pubFile = new PubFile();
//        pubFile.setRecordState(GlobalConstant.ZERO);
//        pubFile.setFileId(id);
//        return save(pubFile);
//    }
//
//    @Override
//    public int deleteById(String id) {
//        return GlobalConstant.ZERO;
//    }
//
//    @Override
//    public List<PubFile> queryList(PubFile pubFile, String orderByClause) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public PubFile getById(String id) {
//        if (StringUtils.isNotBlank(id)) {
//            return fileMapper.selectByPrimaryKey(id);
//        }
//        return null;
//    }
//
//    @Override
//	public PubFile savePubFile(MultipartFile multipartFile, String folderName) {
//		if(multipartFile == null) {
//			return null;
//		}
//		PubFile pubFile = new PubFile();
//        String originalFileName = multipartFile.getOriginalFilename();
//        String suffix =originalFileName.substring(originalFileName.lastIndexOf(".")+1);
//        //默认的文件名
//        pubFile.setFileName(originalFileName);
//        //文件后缀名
//        pubFile.setFileSuffix(suffix);
//        //文件大小
//        pubFile.setFileSize(multipartFile.getSize());
//        String filePath = fileService.uploadFile(multipartFile, folderName, folderName);
//        //InputStream is = multipartFile.getInputStream();
//        pubFile.setFilePath(filePath);
//        save(pubFile);
//		return pubFile;
//	}
//
//    @Override
//    public int deletePubFile(String fileId) {
//    		if(fileId == null){
//            return GlobalConstant.ZERO;
//        }
//        PubFile pubFile = getById(fileId);
//        if(pubFile.getFileThumbnail() != null ){
//            //删除文件
//            fileService.deleteFile(pubFile.getFilePath());
//        }
//        return logicallyDeleteById(fileId);
//    }
//
//
//
//
//}
