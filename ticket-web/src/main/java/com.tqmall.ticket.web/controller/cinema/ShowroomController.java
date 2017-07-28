package com.tqmall.ticket.web.controller.cinema;

import com.tqmall.ticket.biz.bo.TicketShowroomBO;
import com.tqmall.ticket.biz.movie.SpecificScheduleService;
import com.tqmall.ticket.biz.showroom.ShowroomService;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.web.vo.TicketShowroomVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by wurenzhi on 2017/03/09.
 */
@Controller
@RequestMapping("showroom")
public class ShowroomController {

    @Resource
    private ShowroomService showroomService;

    @RequestMapping(value = "detail", produces = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.GET)
    @ResponseBody
    public Result<TicketShowroomVO> getDetail(Integer showroomId){
        Assert.notNull(showroomId,"showroomId不能为空");
        TicketShowroomBO ticketShowroomBO = showroomService.selectByPrimaryKey(showroomId);
        TicketShowroomVO ticketShowroomVO = BdUtil.do2bo(ticketShowroomBO, TicketShowroomVO.class);
        return Result.wrapSuccessfulResult(ticketShowroomVO);
    }
}
