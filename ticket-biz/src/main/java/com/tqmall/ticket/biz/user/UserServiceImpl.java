package com.tqmall.ticket.biz.user;

import com.tqmall.ticket.biz.bo.TicketUserBO;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.dal.entity.TicketUser;
import com.tqmall.ticket.dal.mapper.TicketUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wurenzhi on 2017/01/11.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private TicketUserMapper ticketUserMapper;


    @Override
    public TicketUserBO selectByMobile(Long userMobile) {
        TicketUser ticketUser = ticketUserMapper.selectByMobile(userMobile);
        TicketUserBO ticketUserBO = BdUtil.bo2do(ticketUser, TicketUserBO.class);
        ticketUserBO.setUserMobile(ticketUser.getUserMobile().toString());
        return ticketUserBO;
    }

    @Override
    public int isExistByMobileAndPassword(Long userMobile, String password) {
        int existByMobileAndPassword = ticketUserMapper.isExistByMobileAndPassword(userMobile, password);
        return existByMobileAndPassword;
    }

    @Override
    public int insertSelective(TicketUser record) {
        record.setGmtCreate(new Date());
        record.setGmtModified(new Date());
        int i = ticketUserMapper.insertSelective(record);
        return i;
    }

    @Override
    public TicketUserBO selectByPrimaryKey(Integer id) {
        TicketUser ticketUser = ticketUserMapper.selectByPrimaryKey(id);
        TicketUserBO ticketUserBO = BdUtil.bo2do(ticketUser, TicketUserBO.class);
        return ticketUserBO;
    }

    @Override
    public int isExistByMobile(Long userMobile) {
        int existByMobile = ticketUserMapper.isExistByMobile(userMobile);
        return existByMobile;
    }
}
