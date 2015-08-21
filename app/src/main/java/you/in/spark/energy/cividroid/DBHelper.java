package you.in.spark.energy.cividroid;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CiviDatabase";
    private static final int DB_VER = 1;

    public DBHelper(Context context) {
        super(context, DBHelper.DB_NAME, null, DBHelper.DB_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String cols = "";

        for(String title : CiviContract.CONTACT_TABLE_COLUMNS) {
            cols += ", "+title+" VARCHAR";
        }

        String CREATE_CONTACTS_FIELD_TABLE = "CREATE TABLE " + CiviContract.CONTACTS_FIELD_TABLE + " (" + CiviContract.ID_COLUMN +
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+CiviContract.CONTACT_ID_FIELD+" VARCHAR"+cols+");";

        cols = "";

        for(String title : CiviContract.ACTIVITY_TABLE_COLUMNS) {
            cols += ", "+title+" VARCHAR";
        }

        String CREATE_ACTIVITY_TABLE  = "CREATE TABLE " + CiviContract.ACTIVITY_TABLE + " (" + CiviContract.ID_COLUMN +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + CiviContract.TARGET_CONTACT_ID_COLUMN + " VARCHAR"+cols+");";



        db.execSQL(CREATE_CONTACTS_FIELD_TABLE);
        db.execSQL(CREATE_ACTIVITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CiviContract.CONTACTS_FIELD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CiviContract.CONTACT_CONV_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CiviContract.ACTIVITY_TABLE);
    }
}
