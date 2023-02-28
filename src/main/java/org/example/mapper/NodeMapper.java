package org.example.mapper;

import org.example.entity.Node;
import org.example.utils.MyBatisUtils;

import java.util.List;

public class NodeMapper {

    public List<Node> findAllNodeByUserid(long id)
    {
        return  (List<Node>) MyBatisUtils.executeQuery(sqlSession -> sqlSession.selectList("nodemapper.selectbyuserid",id));
    }
}
