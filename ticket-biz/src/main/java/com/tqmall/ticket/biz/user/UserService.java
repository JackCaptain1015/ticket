package com.tqmall.ticket.biz.user;

import com.tqmall.ticket.biz.bo.TicketUserBO;
import com.tqmall.ticket.dal.entity.TicketUser;

/**
 * Created by wurenzhi on 2017/01/11.
 */
public interface UserService {
    TicketUserBO selectByMobile(Long userMobile);

    int isExistByMobileAndPassword( Long userMobile,
                                    String password);

    int insertSelective(TicketUser record);

    TicketUserBO selectByPrimaryKey(Integer id);

    /**
     * 根据mobile来查询这个账号是否存在
     * @param userMobile
     * @return
     */
    int isExistByMobile(Long userMobile);
}
