package ru.academits.phonebook;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.academits.model.ErrorInfo;

import java.util.logging.Logger;

@ControllerAdvice
public class ErrorController {
    private static Logger log = Logger.getLogger(String.valueOf(ErrorController.class));

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo processException(Exception e) {
        log.info("Hello this is an info message");
        return new ErrorInfo(e.getMessage());
    }
}
