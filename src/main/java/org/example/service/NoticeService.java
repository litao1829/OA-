package org.example.service;

import org.example.entity.Notice;
import org.example.mapper.NoticeMapper;
import org.example.utils.MyBatisUtils;

import java.util.List;

public class NoticeService {
    public List<Notice> getNoticeList(Long receiverId){
        return  (List<Notice>)MyBatisUtils.executeQuery(sqlSession -> {
            NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
            return mapper.selectByreceiveid(receiverId);
        });
    }
}
