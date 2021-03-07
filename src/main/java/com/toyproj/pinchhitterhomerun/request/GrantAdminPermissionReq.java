package com.toyproj.pinchhitterhomerun.request;

import lombok.Getter;

@Getter
public class GrantAdminPermissionReq {
    private String loginId;
    private boolean grant;
}
