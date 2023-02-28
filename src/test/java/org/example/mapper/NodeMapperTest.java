package org.example.mapper;

import org.example.entity.Node;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeMapperTest {
    @Test void findAllnode()
    {
        NodeMapper nodeMapper=new NodeMapper();
        List<Node> node = nodeMapper.findAllNodeByUserid(11L);
        Iterator<Node> iterator = node.iterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
    }
}