package com.cwx.serviceedu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/***
 @author  *
 @date 2023/10/25$ 15:46$*
 @description: */
@Data
public class EasyExcelSubject {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;

}
