package entity;

public enum Status {
	FINISHED("Finished"), CANCELED("Canceled"), ONGOING("Ongoing"), OPENED(
			"Opened");

	public String status;

	Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
