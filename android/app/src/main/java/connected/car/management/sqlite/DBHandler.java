package connected.car.management.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {
    DBHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    public DBHandler(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insert(String air_temp, String engine_time){
        ContentValues contentValues = new ContentValues();
        contentValues.put("air_temp", air_temp);
        contentValues.put("engine_time", engine_time);
        db.insert("airsetting", null, contentValues);
    }

    public Cursor select(){
        Cursor cursor = db.query("airsetting", new String[]{"air_temp", "engine_time"}, null,
                null, null, null, null);
        return cursor;
    }

    public void update(String air_temp, String engine_time){
        ContentValues contentValues = new ContentValues();
        contentValues.put("air_temp", air_temp);
        contentValues.put("engine_time", engine_time);
        db.update("airsetting", contentValues, "air_setting_no=?", new String[]{"1"});
    }
}
