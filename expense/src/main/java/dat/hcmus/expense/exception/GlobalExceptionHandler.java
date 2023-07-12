package dat.hcmus.expense.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dat.hcmus.expense.entity.ErrorObject;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {

		ErrorObject errObj = new ErrorObject(ex.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorObject>(errObj, HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
//	public ResponseEntity<ErrorObject> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
//			WebRequest request) {
//		ErrorObject errObj = new ErrorObject(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
//		return new ResponseEntity<ErrorObject>(errObj, HttpStatus.BAD_REQUEST);
//	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> handleGeneralException(Exception ex, WebRequest request) {
		ErrorObject errObj = new ErrorObject(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<ErrorObject>(errObj, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, Object> body = new HashMap<String, Object>();

		body.put("timestamp", new Date());

		body.put("statusCode", HttpStatus.BAD_REQUEST.value());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("message", errors);

		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
}
