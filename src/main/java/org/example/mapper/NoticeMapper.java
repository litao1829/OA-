package org.example.mapper;

import org.example.entity.Notice;

import java.util.List;

public interface NoticeMapper {
    void insert(Notice notice);
    List<Notice> selectByreceiveid(long reciveid);
}
