package you.in.spark.energy.cividroid;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class CiviProvider extends ContentProvider {

    private static DBHelper dbHelper = null;

    private final static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CiviContract.PROVIDER_AUTHORITY,CiviContract.CONTACTS_FIELD_TABLE,1);
        uriMatcher.addURI(CiviContract.PROVIDER_AUTHORITY,CiviContract.ACTIVITY_FIELD_TABLE,2);
        uriMatcher.addURI(CiviContract.PROVIDER_AUTHORITY,CiviContract.NOTES_TABLE,3);
    }

    @Override
    public boolean onCreate() {
        if(dbHelper==null) {
            dbHelper = new DBHelper(getContext());
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri))
        {
            case 1: cursor =  db.query(CiviContract.CONTACTS_FIELD_TABLE,projection,selection,selectionArgs,null,null,sortOrder);
                return cursor;
            case 2: cursor =  db.query(CiviContract.ACTIVITY_FIELD_TABLE,projection,selection,selectionArgs,null,null,sortOrder);
                return cursor;
            case 3: cursor =  db.query(CiviContract.NOTES_TABLE,projection,selection,selectionArgs,null,null,sortOrder);
                return cursor;
            default: throw new IllegalArgumentException("Invalid URI",null);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri))
        {
            case 1: db.insert(CiviContract.CONTACTS_FIELD_TABLE,null,values);
                return uri;
            case 2: db.insert(CiviContract.ACTIVITY_FIELD_TABLE,null,values);
                return uri;
            case 3: db.insert(CiviContract.NOTES_TABLE,null,values);
                return uri;
            default: throw new IllegalArgumentException("Invalid URI",null);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int del;
        switch (uriMatcher.match(uri))
        {
            case 1: del = db.delete(CiviContract.CONTACTS_FIELD_TABLE,selection,selectionArgs);
                return del;
            case 2: del = db.delete(CiviContract.ACTIVITY_FIELD_TABLE,selection,selectionArgs);
                return del;
            case 3: del = db.delete(CiviContract.NOTES_TABLE,selection,selectionArgs);
                return del;
            default: throw new IllegalArgumentException("Invalid URI",null);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int update;
        switch (uriMatcher.match(uri))
        {
            case 1: update = db.update(CiviContract.CONTACTS_FIELD_TABLE, values, selection, selectionArgs);
                return update;
            case 2: update = db.update(CiviContract.ACTIVITY_FIELD_TABLE, values, selection, selectionArgs);
                return update;
            default: throw new IllegalArgumentException("Invalid URI",null);
        }
    }

    public int bulkInsert(Uri uri, ContentValues[] values){
        int numInserted = 0;

        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        sqlDB.beginTransaction();
        switch (uriMatcher.match(uri))
        {
            case 1: startInserting(values, sqlDB, numInserted, uri, CiviContract.CONTACTS_FIELD_TABLE);
                break;
            case 2: startInserting(values, sqlDB, numInserted, uri, CiviContract.ACTIVITY_FIELD_TABLE);
                break;
            case 3: startInserting(values, sqlDB, numInserted, uri, CiviContract.NOTES_TABLE);
                break;
            default: throw new IllegalArgumentException("Invalid URI",null);
        }
        return numInserted;
    }

    private void startInserting(ContentValues[] values, SQLiteDatabase sqlDB, int numInserted, Uri uri, String fieldTable) {
        try {
            for (ContentValues cv : values) {
                long newID = sqlDB.insertOrThrow(fieldTable, null, cv);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row !" + uri);
                }
            }
            sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } finally {
            sqlDB.endTransaction();
        }
    }
}
