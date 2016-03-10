package pl.wasat.smarthma.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import pl.wasat.smarthma.model.entry.Entry;

public class EoDbAdapter {

    private static final String KEY_ROWID = BaseColumns._ID;
    private static final String KEY_GUID = "guid";
    private static final String KEY_READ = "read";
    private static final String KEY_OFFLINE = "offline";

    private static final String DATABASE_NAME = "blogposts";
    private static final String DATABASE_TABLE = "blogpostlist";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_LIST_TABLE = "create table " + DATABASE_TABLE + " (" +
            KEY_ROWID + " integer primary key autoincrement, " +
            KEY_GUID + " text not null, " +
            KEY_READ + " boolean not null, " +
            KEY_OFFLINE + " boolean not null);";
    private final Context context;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    public EoDbAdapter(Context c) {
        context = c;
    }

    public void openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
    }

    public void openToWrite() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteHelper.close();
    }

    public void insertBlogListing(String guid) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_GUID, guid);
        initialValues.put(KEY_READ, false);
        initialValues.put(KEY_OFFLINE, false);
        sqLiteDatabase.insert(DATABASE_TABLE, null, initialValues);
    }

    public Entry getBlogListing(String guid) throws SQLException {
        Cursor mCursor =
                sqLiteDatabase.query(true, DATABASE_TABLE, new String[]{
                                KEY_ROWID,
                                KEY_GUID,
                                KEY_READ,
                                KEY_OFFLINE
                        },
                        KEY_GUID + "= '" + guid + "'",
                        null,
                        null,
                        null,
                        null,
                        null);

        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            Entry a = new Entry();
            a.setGuid(mCursor.getString(mCursor.getColumnIndex(KEY_GUID)));
            a.setRead(mCursor.getInt(mCursor.getColumnIndex(KEY_READ)) > 0);
            a.setDbId(mCursor.getLong(mCursor.getColumnIndex(KEY_ROWID)));
            a.setOffline(mCursor.getInt(mCursor.getColumnIndex(KEY_OFFLINE)) > 0);
            sqLiteDatabase.close();
            return a;
        }
        return null;
    }

    public boolean markAsUnread(String guid) {
        ContentValues args = new ContentValues();
        args.put(KEY_READ, false);
        return sqLiteDatabase.update(DATABASE_TABLE, args, KEY_GUID + "='" + guid + "'", null) > 0;
    }

    public boolean markAsRead(String guid) {
        ContentValues args = new ContentValues();
        args.put(KEY_READ, true);
        return sqLiteDatabase.update(DATABASE_TABLE, args, KEY_GUID + "='" + guid + "'", null) > 0;
    }

    public boolean saveForOffline(String guid) {
        ContentValues args = new ContentValues();
        args.put(KEY_OFFLINE, true);
        return sqLiteDatabase.update(DATABASE_TABLE, args, KEY_GUID + "='" + guid + "'", null) > 0;
    }

    public class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_LIST_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}