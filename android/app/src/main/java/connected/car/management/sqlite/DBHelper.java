package connected.car.management.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;

    public DBHelper( Context context) {
        super(context, "airsetting.db", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("===","데이터베이스가 생성됨");

        String sql = "create table if not exists airsetting("
                + "airsettingno integer primary key autoincrement,"
                + "setime text,"
                + "carid text,"
                + "airtemp text,"
                 +"enginetime text)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("===","데이터베이스 스키마가 변경됨");

    }
}
