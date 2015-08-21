package you.in.spark.energy.cividroid;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class CiviProvider extends ContentProvider {

    private static DBHelper dbHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        CiviProvider.uriMatcher.addURI(CiviContract.PROVIDER_AUTHORITY, CiviContract.CONTACTS_FIELD_TABLE, 1);
        CiviProvider.uriMatcher.addURI(CiviContract.PROVIDER_AUTHORITY, CiviContract.ACTIVITY_TABLE, 2);
        CiviProvider.uriMatcher.addURI(CiviContract.PROVIDER_AUTHORITY, CiviContract.NOTES_TABLE, 3);
    }

    @Override
    public boolean onCreate() {
        if (CiviProvider.dbHelper == null) {
            CiviProvider.dbHelper = new DBHelper(this.getContext());
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = CiviProvider.dbHelper.getWritableDatabase();
        Cursor cursor;
        switch (CiviProvider.uriMatcher.match(uri)) {
            case 1:
                cursor = db.query(CiviContract.CONTACTS_FIELD_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case 2:
                cursor = db.query(CiviContract.ACTIVITY_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case 3:
                cursor = db.query(CiviContract.NOTES_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI", null);
        }
        cursor.setNotificationUri(this.getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = CiviProvider.dbHelper.getWritableDatabase();
        switch (CiviProvider.uriMatcher.match(uri)) {
            case 1:
                db.insert(CiviContract.CONTACTS_FIELD_TABLE, null, values);
                break;
            case 2:
                db.insert(CiviContract.ACTIVITY_TABLE, null, values);
                break;
            case 3:
                db.insert(CiviContract.NOTES_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Invalid URI", null);
        }
        this.getContext().getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = CiviProvider.dbHelper.getWritableDatabase();
        int del;
        switch (CiviProvider.uriMatcher.match(uri)) {
            case 1:
                del = db.delete(CiviContract.CONTACTS_FIELD_TABLE, selection, selectionArgs);
break;            case 2:
                del = db.delete(CiviContract.ACTIVITY_TABLE, selection, selectionArgs);
break;            case 3:
                del = db.delete(CiviContract.NOTES_TABLE, selection, selectionArgs);
break;            default:
                throw new IllegalArgumentException("Invalid URI", null);
        }
        this.getContext().getContentResolver().notifyChange(uri,null);
        return del;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = CiviProvider.dbHelper.getWritableDatabase();
        int update;
        switch (CiviProvider.uriMatcher.match(uri)) {
            case 1:
                update = db.update(CiviContract.CONTACTS_FIELD_TABLE, values, selection, selectionArgs);
break;            case 2:
                update = db.update(CiviContract.ACTIVITY_TABLE, values, selection, selectionArgs);
break;            default:
                throw new IllegalArgumentException("Invalid URI", null);
        }
        this.getContext().getContentResolver().notifyChange(uri,null);
        return update;
    }

    public int bulkInsert(Uri uri, ContentValues[] values) {
        long numInserted = 0;

        SQLiteDatabase sqlDB = CiviProvider.dbHelper.getWritableDatabase();
        sqlDB.beginTransaction();
        switch (CiviProvider.uriMatcher.match(uri)) {
            case 1:
                numInserted = this.startInserting(values, sqlDB, uri, CiviContract.CONTACTS_FIELD_TABLE);
                break;
            case 2:
                numInserted = this.startInserting(values, sqlDB, uri, CiviContract.ACTIVITY_TABLE);
                break;
            case 3:
                numInserted = this.startInserting(values, sqlDB, uri, CiviContract.NOTES_TABLE);
                break;
            default:
                throw new IllegalArgumentException("Invalid URI", null);
        }
        this.getContext().getContentResolver().notifyChange(uri,null);
        return (int) numInserted;
    }

    private long startInserting(ContentValues[] values, SQLiteDatabase sqlDB, Uri uri, String fieldTable) {
        long newID = 0;
        try {
            for (ContentValues cv : values) {
                newID = sqlDB.insertOrThrow(fieldTable, null, cv);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row !" + uri);
                }
            }
            sqlDB.setTransactionSuccessful();
            this.getContext().getContentResolver().notifyChange(uri, null);
        } finally {
            sqlDB.endTransaction();
        }
        return newID;
    }
}
