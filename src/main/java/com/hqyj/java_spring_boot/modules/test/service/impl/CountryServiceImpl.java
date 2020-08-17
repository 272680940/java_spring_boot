package com.hqyj.java_spring_boot.modules.test.service.impl;

import com.hqyj.java_spring_boot.modules.test.dao.CountryDao;
import com.hqyj.java_spring_boot.modules.test.entity.Country;
import com.hqyj.java_spring_boot.modules.test.service.CountryService;
import com.hqyj.java_spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtils redisUtils;


    //通过countryId查询国家
    @Override
    public Country getCountryByCountryId(int countryId) {
        return countryDao.getCountryByCountryId(countryId);
    }

    //通过countryName查询国家
    @Override
    public Country getCountryByCountryName(String countryName) {
        return countryDao.getCountryByCountryName(countryName);
    }

    @Override
    public Country mograteCountryByRedis(int countryId) {
        Country country = countryDao.getCountryByCountryId(countryId);

        String countryKey = String.format("country%d",countryId);

        //redisTemplate.opsForValue().set(countryKey,country);

        //return (Country) redisTemplate.opsForValue().get(countryKey);

        redisUtils.set(countryKey,country);

        return (Country) redisUtils.get(countryKey);
    }
}
