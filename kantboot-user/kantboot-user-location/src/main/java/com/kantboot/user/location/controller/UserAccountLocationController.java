package com.kantboot.user.location.controller;

import com.kantboot.user.location.domain.entity.UserAccountLocation;
import com.kantboot.user.location.service.IUserAccountLocationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-location-web/userAccountLocation")
public class UserAccountLocationController {

    @Resource
    private IUserAccountLocationService userAccountLocationService;

    @RequestMapping("/saveSelf")
    public UserAccountLocation saveSelf(UserAccountLocation entity) {
        return userAccountLocationService.saveSelf(entity);
    }

}
