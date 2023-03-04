package org.example.mapper;

import org.example.entity.Notice;
import org.example.utils.MyBatisUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoticeMapperTest {

    @Test
    void selectByreceiveid() {
         List<Notice> list=(List<Notice>)MyBatisUtils.executeQuery(sqlSession -> {
            NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
             return mapper.selectByreceiveid(3L);

         });
        System.out.println(list);
    }

    @Test
    void insertest()
    {
        MyBatisUtils.executeUpdate(sqlSession -> {
            NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
            Notice notice=Notice.builder().receiverId(15)
                    .content("aaaaaaaaaaaaaaaaa")
                    .createTime(new Date())
                    .build();
                  mapper.insert(notice);
                return null;
        });
    }
}