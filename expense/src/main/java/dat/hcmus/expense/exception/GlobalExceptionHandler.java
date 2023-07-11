package dat.hcmus.expense.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import dat.hcmus.expense.entity.ErrorObject;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {

		ErrorObject errObj = new ErrorObject(ex.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorObject>(errObj, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorObject> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		ErrorObject errObj = new ErrorObject(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorObject>(errObj, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> handleException(Exception ex, WebRequest request) {
		ErrorObject errObj = new ErrorObject(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<ErrorObject>(errObj, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
