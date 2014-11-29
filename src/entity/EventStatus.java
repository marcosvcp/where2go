package entity;

public enum EventStatus {
    FINISHED("Finished"), CANCELED("Canceled"), ONGOING("Ongoing"), OPENED(
            "Opened");

    public String status;

    EventStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
