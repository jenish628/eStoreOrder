package estore.order.exception;

import estore.order.entity.OrderLine;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OperationUnsucessfullExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({OperationUnsucessfullException.class})
    public ResponseEntity<Object> exceptionHandler(Exception e, WebRequest request){
        return handleExceptionInternal(e,"Operation failed, Please Check it out.",new HttpHeaders(),
                HttpStatus.NOT_FOUND,request);
    }


    @ExceptionHandler({OrderLineNotFoundException.class})
    public ResponseEntity<Object> orderLineNotFoundException(OrderLineNotFoundException e, WebRequest request){
        return handleExceptionInternal(e,e.getMessage(),new HttpHeaders(),
                HttpStatus.NOT_FOUND,request);
    }


    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<Object> oderNotFoundException(OrderNotFoundException e, WebRequest request){
        return handleExceptionInternal(e,e.getMessage(),new HttpHeaders(),
                HttpStatus.NOT_FOUND,request);
    }

    @ExceptionHandler({AddressNotFoundException.class})
    public ResponseEntity<Object> addressNotFound(OrderNotFoundException e, WebRequest request){
        return handleExceptionInternal(e,e.getMessage(),new HttpHeaders(),
                HttpStatus.NOT_FOUND,request);
    }

}
