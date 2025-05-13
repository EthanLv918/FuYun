package com.soft.service;

import com.soft.dto.DemoDto;
import com.soft.common.ServerResponse;

public interface DemoService {

    ServerResponse mungsDemo(DemoDto demosDto);
}
