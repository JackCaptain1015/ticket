package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketUserLoginCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketUserLoginCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketUserLoginCode record);

    int insertSelective(TicketUserLoginCode record);

    TicketUserLoginCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketUserLoginCode record);

    int updateByPrimaryKey(TicketUserLoginCode record);

    /**
     * 按mobile和输入验证码查询是否可用
     * @param userMobile
     * @param verifyCode
     * @return
     */
    List<TicketUserLoginCode> selectByMobileAndVerifyCode(@Param("userMobile") Long userMobile,
                                                          @Param("verifyCode") Integer verifyCode);

    /**
     * 根据Mobile取最近发送的一条验证码
     * @param userMobile
     * @return
     */
    TicketUserLoginCode selectByMobile(Long userMobile);

    /***
     * 更新验证码失败状态
     * @param idList
     * @return
     */
    int batchUpdateFailByIdList(@Param("list") List<TicketUserLoginCode> idList);

    /***
     * 更新验证码成功状态
     * @param idList
     * @return
     */
    int batchUpdateSuccessByIdList(@Param("list") List<TicketUserLoginCode> idList);

    int dealDeadVerifyCode();
}