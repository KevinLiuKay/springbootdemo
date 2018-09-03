package com.kevin.service.pub.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.PubFileMapper;
import com.kevin.model.PubFile;
import com.kevin.model.PubFileExample;
import com.kevin.model.PubFileExample.Criteria;
import com.kevin.service.pub.IPubFileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor=Exception.class)
public class PubFileServiceImpl implements IPubFileService {

    private static Logger logger = LoggerFactory.getLogger(PubFileServiceImpl.class);

    @Resource
    private PubFileMapper fileMapper;

    @Override
    public int save(PubFile pubFile) {
        if (StringUtils.isBlank(pubFile.getFileId())) {
            pubFile.setFileId(UUIDUtil.getUUID());
            GeneralMethod.setRecordInfo(pubFile, true);
            return fileMapper.insertSelective(pubFile);
        }else{
            GeneralMethod.setRecordInfo(pubFile, false);
            return fileMapper.updateByPrimaryKeySelective(pubFile);
        }
    }

    @Override
    public int logicallyDeleteById(String id) {
        if(StringUtils.isNotBlank(id)){
            PubFile pubFile = new PubFile();
            pubFile.setRecordState(GlobalConstant.Y);
            pubFile.setFileId(id);
            return save(pubFile);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public int deleteById(String id) {
        if(StringUtils.isNotBlank(id)) {
            return fileMapper.deleteByPrimaryKey(id);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public List<PubFile> queryList(PubFile pubFile, String orderByClause) {
        PubFileExample example = new PubFileExample();
        Criteria criteria = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
        if(StringUtils.isNotBlank(pubFile.getFileName())){
            criteria.andFileNameLike(GlobalConstant.PERCENT+pubFile.getFileName()+GlobalConstant.PERCENT);
        }
        example.setOrderByClause(orderByClause);
        return fileMapper.selectByExample(example);
    }

    @Override
    public PubFile getById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return fileMapper.selectByPrimaryKey(id);
        }
        return null;
    }

}
