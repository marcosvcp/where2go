package entity;

/**
 * Created by andersongfs on 25/11/14.
 */
public enum EventVisibility {
    FINISHED(1), CANCELED(2), ONGOING(3), OPENED(4);
    private final int mValue;

    EventVisibility(int value){
        mValue = value;
    }
    public int getValue(){
        return mValue;
    }

}
