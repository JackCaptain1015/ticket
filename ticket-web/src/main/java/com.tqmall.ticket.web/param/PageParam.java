package com.tqmall.ticket.web.param;

import com.tqmall.ticket.common.constants.TicketConstants;
import lombok.Data;

/**
 * Created by wurenzhi on 2017/02/27.
 */
@Data
public class PageParam {
    private Integer curNo;      //当前页码

    private Integer pageSize;   //每页条数

    public Integer getPageSize() {
        if (pageSize == null || pageSize == 0) return TicketConstants.DEFAULT_PAGE_SIZE;
        return pageSize;
    }
}
