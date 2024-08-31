package com.emazon.user.infrastructure.exceptionhandler;

import com.emazon.user.utils.Constants;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = Constants.RESPONSE_MESSAGE_KEY;
}
