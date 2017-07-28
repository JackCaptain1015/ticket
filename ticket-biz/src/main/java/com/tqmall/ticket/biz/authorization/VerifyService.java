package com.tqmall.ticket.biz.authorization;

/**
 * Created by wurenzhi on 2017/01/12.
 */
public interface VerifyService {
    /**
     * 根据手机号，校验验证码
     * @param mobile
     * @param code
     * @return
     */
    boolean verifying(Long mobile, Integer code);

    /**
     * 插入生成的验证码
     * @param mobile
     * @param code
     * @return
     */
    boolean addVerifyRecord(String mobile, String code);

    int dealDeadVerifyCode();

}
