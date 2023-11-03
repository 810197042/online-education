package com.cwx.serviceedu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 @author  *
 @date 2023/10/26$ 20:33$*
 @description: */
@Data
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();
}
