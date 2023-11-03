package com.cwx.serviceedu.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 @author  *
 @date 2023/10/25$ 21:23$*
 @description: */
@Data
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}
