package dat.hcmus.expense.entity;

public class ErrorObject {
	private String message;
	private int statusCode;
	private long timestamp;

	public ErrorObject(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.timestamp = System.currentTimeMillis();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
