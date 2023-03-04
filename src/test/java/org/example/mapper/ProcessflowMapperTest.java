package org.example.mapper;

import org.example.entity.ProcessFlow;
import org.example.utils.MyBatisUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProcessflowMapperTest {

    @Test
    void update() {
        MyBatisUtils.executeUpdate(sqlSession -> {
            ProcessflowMapper mapper = sqlSession.getMapper(ProcessflowMapper.class);
            List<ProcessFlow> list = mapper.selectByFormId(4L);
            ProcessFlow f=list.get(0);
            System.out.println(f);
            f.setReason("pppppppppppppp");
            mapper.update(f);
            System.out.println(f);
            return null;
        });
    }

    @Test
    void selectByFormId() {
         List<ProcessFlow> list=(List<ProcessFlow>)MyBatisUtils.executeQuery(sqlSession -> {
             ProcessflowMapper mapper = sqlSession.getMapper(ProcessflowMapper.class);
              return mapper.selectByFormId(1L);
         });
        System.out.println(list);
    }

    @Test
    void selectByParams() {

        List<Map<String, Object>> process= (ArrayList<Map<String, Object>>)MyBatisUtils.executeQuery(sqlSession -> {
            ProcessflowMapper mapper = sqlSession.getMapper(ProcessflowMapper.class);
            return (ArrayList<Map<String, Object>>) mapper.selectByParams("process", 3L);
        });
        System.out.println(process.size());
        LinkedHashMap<String,Object> info= (LinkedHashMap<String,Object>)process.get(0);
        for (String set:info.keySet())
        {
            System.out.println(info.get(set));
        }
    }
}