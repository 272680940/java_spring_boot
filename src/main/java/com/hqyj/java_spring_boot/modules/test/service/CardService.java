package com.hqyj.java_spring_boot.modules.test.service;

import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.test.entity.Card;

public interface CardService {
    //添加
    Result<Card> insertCard(Card card);
}
