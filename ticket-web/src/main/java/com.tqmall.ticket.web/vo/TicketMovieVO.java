package com.tqmall.ticket.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wurenzhi on 2017/02/27.
 */
@Getter
@Setter
public class TicketMovieVO {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private String movieName;

    private String movieDirector;

    /**主演*/
    private String movieActor;

    private String movieType;

    private String movieProduct;

    private String movieLanguage;

    private Integer movieLength;

    private String movieDesc;

    //电影状态：0:未上架或已下架 1:正在热映 2:即将热映
    private Integer movieStatus;

    //电影海报
    private String moviePicture;
}
