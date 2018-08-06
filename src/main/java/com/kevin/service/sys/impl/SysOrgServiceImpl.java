package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.SysOrgMapper;
import com.kevin.model.SysOrg;
import com.kevin.model.SysOrgExample;
import com.kevin.model.ext.sys.SysOrgExt;
import com.kevin.service.sys.ISysOrgService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author lzk
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysOrgServiceImpl implements ISysOrgService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SysOrgServiceImpl.class);

    @Resource
    private SysOrgMapper sysOrgMapper;

    @Override
    public int save(SysOrg sysOrg) {
        if (StringUtils.isBlank(sysOrg.getOrgId())) {
            //新增
            sysOrg.setOrgId(UUIDUtil.getUUID());
            GeneralMethod.setRecordInfo(sysOrg, true);
            return sysOrgMapper.insertSelective(sysOrg);
        } else {
            //修改
            GeneralMethod.setRecordInfo(sysOrg, false);
            return sysOrgMapper.updateByPrimaryKeySelective(sysOrg);
        }
    }

    @Override
    public int logicallyDeleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            //update
           SysOrg sysOrg = new SysOrg();
            //set
            sysOrg.setRecordState(GlobalConstant.N);
            //where
            sysOrg.setOrgId(id);
            return save(sysOrg);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public int deleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysOrgMapper.deleteByPrimaryKey(id);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public List<SysOrg> queryList(SysOrg sysOrg, String orderByClause) {
        SysOrgExample example = new SysOrgExample();
        SysOrgExample.Criteria criteria = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
        andCrieria(sysOrg, criteria);
        /* 排序字段 */
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        return sysOrgMapper.selectByExample(example);
    }

    @Override
    public SysOrg getById(String id) {
        return null;
    }

    private void andCrieria(SysOrg sysOrg, SysOrgExample.Criteria criteria) {
        if (StringUtils.isNotBlank(sysOrg.getOrgName())) {
            criteria.andOrgNameLike(GlobalConstant.PERCENT + sysOrg.getOrgName() + GlobalConstant.PERCENT);
        }
    }

    @Override
    public List<SysOrgExt> queryOrgTree(SysOrg sysOrg) {
        List<SysOrgExt> orgTree = null;
        List<SysOrg> orgList = queryList(sysOrg, null);
        if (null != orgList && !orgList.isEmpty()) {
            // 创建根节点
            SysOrgExt root = new SysOrgExt();
            // 组装Map数据
            Map<String, SysOrgExt> dataMap = new HashMap<String, SysOrgExt>();
            for (SysOrg org : orgList) {
                SysOrgExt SysOrgExt = new SysOrgExt();
                // 复制属性 A-B
                BeanUtils.copyProperties(org,SysOrgExt);
                dataMap.put(SysOrgExt.getOrgId(), SysOrgExt);
            }
            // 组装树形结构
            Set<Map.Entry<String, SysOrgExt>> entrySet = dataMap.entrySet();
            for (Map.Entry<String, SysOrgExt> entry : entrySet) {
                SysOrgExt org = entry.getValue();
                if (null == org.getOrgParentId() || ("0").equals(org.getOrgParentId())) {
                    root.getChildren().add(org);
                } else {
                    dataMap.get(org.getOrgParentId()).getChildren().add(org);
                }
            }
            // 对树形结构进行二叉树排序
            root.sortChildren();
            orgTree = root.getChildren();
        }
        return orgTree;
    }
}
