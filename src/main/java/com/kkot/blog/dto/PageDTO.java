package com.kkot.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageDTO {
    private int totalPage;
    private int pageNo;
    private int startNumber;
    private int endNumber;
    private boolean hasPrev;
    private boolean hasNext;
    private int prevIndex;
    private int nextIndex;
}
