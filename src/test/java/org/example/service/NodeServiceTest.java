package org.example.service;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeServiceTest {
    @Test
    public void testfind()
    {
        NodeService service=new NodeService();
        List<HashMap<String, Object>> allNode = service.findAllNode(11L);
        System.out.println(allNode.toString());
    }

}