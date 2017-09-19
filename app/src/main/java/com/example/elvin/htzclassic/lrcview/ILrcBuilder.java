package com.example.elvin.htzclassic.lrcview;

import com.example.elvin.htzclassic.lrcview.impl.LrcRow;

import java.util.List;

/**
 * 解析歌词，得到LrcRow的集合
 */
public interface ILrcBuilder {
    List<LrcRow> getLrcRows(String rawLrc);
}
