package com.tqmall.ticket.biz.authorization.shiro;

import freemarker.template.SimpleHash;
import org.apache.shiro.web.tags.*;

/**
 * Created by wurenzhi on 2016/12/26.
 */
public class CustomShiroTags extends SimpleHash {
    public CustomShiroTags() {
        put("authenticated", new AuthenticatedTag());
        put("guest", new GuestTag());
        put("hasAnyRoles", new HasAnyRolesTag());
        put("hasPermission", new HasPermissionTag());
        put("hasRole", new HasRoleTag());
        put("lacksPermission", new LacksPermissionTag());
        put("lacksRole", new LacksRoleTag());
        put("notAuthenticated", new NotAuthenticatedTag());
        put("principal", new PrincipalTag());
        put("user", new UserTag());
    }
}
