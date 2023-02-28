package org.example.service;

import org.example.entity.Node;
import org.example.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NodeService {
    private NodeMapper nodeMapper=new NodeMapper();
    //将模块和功能封装树形
    public List<HashMap<String,Object>> findAllNode(long id)
    {
        List<Node> nodeList = nodeMapper.findAllNodeByUserid(id);
        HashMap<String,Object> node=null;
        List<HashMap<String,Object>> tree=new ArrayList<HashMap<String,Object>>();
        for (Node d:nodeList)
        {
           if(d.getNodeType()==1){
                node=new HashMap<String,Object>();
                node.put("node",d);
                node.put("child",new ArrayList<Node>());
                tree.add(node);
           }
           else if(d.getNodeType()==2)
           {
             List<Node> children= (List<Node>)node.get("child");
             children.add(d);
           }
        }
        return tree;
    }
}
