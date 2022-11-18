package com.itrjp.im.controller;

import com.itrjp.common.result.Result;
import com.itrjp.im.uid.UidGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/11/17 22:49
 */

@RestController
@RequestMapping("uid")
public class UIdController {

    private final UidGenerator uidGenerator;

    public UIdController(UidGenerator uidGenerator) {
        this.uidGenerator = uidGenerator;
    }

    @GetMapping
    public Result<Long> getUid() {
        long uid = uidGenerator.getUID();
        return Result.success(uid);
    }
}
