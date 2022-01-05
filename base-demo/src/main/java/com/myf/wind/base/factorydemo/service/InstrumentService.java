package com.myf.wind.base.factorydemo.service;
/**
 * @author : wind-myf
 * @desc : 乐器类 Service
 */
public interface InstrumentService {

    /**
     * 弹奏乐器
     * @param userName 用户名
     * @return String
     */
    String playInstrument(String userName);
}
