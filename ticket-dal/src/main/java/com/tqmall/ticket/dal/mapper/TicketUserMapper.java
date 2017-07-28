package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketUser;
import org.apache.ibatis.annotations.Param;

public interface TicketUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketUser record);

    int insertSelective(TicketUser record);

    TicketUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketUser record);

    int updateByPrimaryKey(TicketUser record);

    /**
     * 根据手机号查找账号
     * @param userMobile
     * @return
     */
    TicketUser selectByMobile(@Param("userMobile") Long userMobile);

    /**
     * 根据手机号和密码判断是否正确
     * @param userMobile
     * @param password
     * @return
     */
    int isExistByMobileAndPassword(@Param("userMobile") Long userMobile,
                                   @Param("password") String password);

    /**
     * 根据mobile来查询这个账号是否存在
     * @param userMobile
     * @return
     */
    int isExistByMobile(Long userMobile);
}