package com.wf.crowd.mapper;

import com.wf.crowd.entity.po.ProjectPO;
import com.wf.crowd.entity.po.ProjectPOExample;
import com.wf.crowd.entity.vo.DetailProjectVO;
import com.wf.crowd.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    void insertTypeRelationship(@Param("typeIdList") List<Integer> typeIdList,@Param("projectPOId") Integer projectPOId);

    void insertTagRelationship(@Param("tagIdList") List<Integer> tagIdList, @Param("projectPOId") Integer projectPOId);

    List<PortalTypeVO> selectPortalTypeVOList();

    DetailProjectVO selectDetailProjectVO(Integer projectId);
}