package entity;

/**
 * Created by andersongfs on 25/11/14.
 */
public enum EventStatus {
    PUBLIC(1), PRIVATE(2);
    private final int mValue;

    EventStatus(int value){
        mValue = value;
    }
    public int getValue(){
        return mValue;
    }
}
