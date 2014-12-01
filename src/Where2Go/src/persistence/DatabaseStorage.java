package persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import entity.event.Event;

public class DatabaseStorage implements StorageSystem {
	public static final String EVENT_TABLE_NAME = "EVENTS";
    public static final String EVENT_ID = "_id";
    public static final String EVENT_NAME = "NOME";    
    public static final String EVENT_STATUS = "STATUS";
    public static final String EVENT_DESCRIPTION = "DESCRIPTION";    
    public static final String EVENT_PHOTO = "PHOTO";
    public static final String EVENT_INFO = "INFO";
    public static final String EVENT_INITIAL_DATE = "INITIAL_DATE";
    public static final String EVENT_FINAL_DATE = "FINAL_DATE";
    public static final String EVENT_PRICE = "PRICE";
    public static final String EVENT_OUTFIT = "OUTFIT";
    public static final String EVENT_CAPACITY = "CAPACITY";
    public static final String EVENT_TIMESTAMP = "TIMESTAMP";


    private static final String EVENT_CREATE_TABLE = "CREATE TABLE "
            + EVENT_TABLE_NAME + "  (" + EVENT_ID
            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + EVENT_NAME
            + " TEXT NOT NULL," + EVENT_STATUS + " TEXT NOT NULL,"
            + EVENT_DESCRIPTION + " TEXT NOT NULL," + EVENT_PHOTO
            + " TEXT NOT NULL," + EVENT_INFO
            + " TEXT NOT NULL," + EVENT_INITIAL_DATE + " TEXT NOT NULL,"
            + EVENT_FINAL_DATE + " TEXT NOT NULL," + EVENT_PRICE
            + " TEXT NOT NULL," + EVENT_OUTFIT + " TEXT NOT NULL,"
            + EVENT_CAPACITY + " INTEGER NOT NULL," + EVENT_TIMESTAMP
            + " TEXT NOT NULL" + ");";
    
    /**
     * Create database
     */
    @SuppressWarnings("unused")
    private static final String TAG = "DataBaseStorage";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DB_NAME = "DBP";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    
    public static class DatabaseHelper extends SQLiteOpenHelper {
        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(EVENT_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
            onCreate(db);
        }
    }
    
    public DatabaseStorage(Context ctx) {
        mCtx = ctx;
        open();
    }
    
    public void create() {
        mDb.execSQL(EVENT_CREATE_TABLE);
    }

    public void recreate() {
        mDb.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
    }

    public DatabaseStorage open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
        mDb.close();
    }

    public DatabaseHelper getMDBHelper() {
        return mDbHelper;
    }

    public SQLiteDatabase getMdb() {
        return mDb;
    }

    
	@Override
	public Integer add(Event event) {
		ContentValues values = new ContentValues();
        values = eventContentValues(event);
        return (int) mDb.insert(EVENT_TABLE_NAME, null, values);
	}

	@Override
	public void delete(Integer id) {
        mDb.delete(EVENT_TABLE_NAME, EVENT_ID + "=" + id, null);
	}

	@Override
	public void edit(Event event) {
        ContentValues values = eventContentValues(event);
        mDb.update(EVENT_TABLE_NAME, values,
                EVENT_ID + "=" + event.getId(), null);
	}

	@Override
	public Event getEvent(int key) {
		Cursor mCursor = null;
		mCursor = mDb.query(true, EVENT_TABLE_NAME, new String[]{EVENT_ID, EVENT_NAME, 
				EVENT_STATUS, EVENT_DESCRIPTION, EVENT_PHOTO, EVENT_INFO, EVENT_INITIAL_DATE,
				EVENT_FINAL_DATE,EVENT_PRICE,EVENT_OUTFIT,EVENT_CAPACITY,EVENT_TIMESTAMP},
				EVENT_ID + "=?", 
				new String[] {
	                    String.valueOf(key)
	                },
	                null, null, null, null, null);
		
		 	if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
		 	
		 	Integer id = mCursor.getInt(mCursor.getColumnIndex(EVENT_ID));
		 	String name = mCursor.getString(mCursor.getColumnIndex(EVENT_NAME));
		 	String status = mCursor.getString(mCursor.getColumnIndex(EVENT_STATUS));
		 	String description = mCursor.getString(mCursor.getColumnIndex(EVENT_DESCRIPTION));
		 	String photo = mCursor.getString(mCursor.getColumnIndex(EVENT_PHOTO));
		 	String info = mCursor.getString(mCursor.getColumnIndex(EVENT_INFO));
		 	String initial_date = mCursor.getString(mCursor.getColumnIndex(EVENT_INITIAL_DATE));
		 	String final_date = mCursor.getString(mCursor.getColumnIndex(EVENT_FINAL_DATE));
		 	String price = mCursor.getString(mCursor.getColumnIndex(EVENT_PRICE));
		 	String outfit = mCursor.getString(mCursor.getColumnIndex(EVENT_OUTFIT));
		 	Integer capacity = mCursor.getInt(mCursor.getColumnIndex(EVENT_CAPACITY));
		 	String timestamp = mCursor.getString(mCursor.getColumnIndex(EVENT_TIMESTAMP));
		 	
		 	Event event = new Event(id, name, status, description, photo, info, initial_date, final_date, price, outfit, capacity, timestamp);
		 	event.setId(id);
	        mCursor.close();
	        return event;
	}

	@Override
	public List<Event> getEvents() {
		List<Event> events = new ArrayList<Event>();

        try {
            String query = "SELECT * FROM " + EVENT_TABLE_NAME;
            Cursor mCursor = mDb.rawQuery(query, null);

            if (mCursor != null) {
                mCursor.moveToFirst();
                while (mCursor.isAfterLast() == false) {
                	events.add(getEvent(mCursor.getInt(mCursor
                            .getColumnIndex(EVENT_ID))));
                    mCursor.moveToNext();
                }
            }
            if (mCursor != null) {
                mCursor.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return events;	}
	
    private ContentValues eventContentValues(Event event) {
        ContentValues values = new ContentValues();
        values.put(EVENT_NAME, event.getName());
        values.put(EVENT_STATUS, event.getStatus());
        values.put(EVENT_DESCRIPTION, event.getDescription());
        values.put(EVENT_PHOTO, event.getPhoto());
        values.put(EVENT_INFO, event.getInfo());
        values.put(EVENT_INITIAL_DATE, event.getInitialDate());
        values.put(EVENT_FINAL_DATE, event.getFinalDate());
        values.put(EVENT_PRICE, event.getPrice());
        values.put(EVENT_OUTFIT, event.getOutfit());
        values.put(EVENT_CAPACITY, event.getCapacity());
        values.put(EVENT_TIMESTAMP, event.getTimestamp());
        return values;
    }

}
