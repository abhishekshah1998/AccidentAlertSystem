package xyz.abhishekshah.accidentalertsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contactList.db";
    public static final String TABLE_NAME = "contacts";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "CONTACT_NUMBER";


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CONTACT_NUMBER TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addData(String NAME,String phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,NAME);
        contentValues.put(COL3,phone);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        return res;


    }

    public boolean updateData(String id,String NAME,String phone){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,NAME);
        contentValues.put(COL3,phone);

        db.update(TABLE_NAME, contentValues,"ID = ?",new String[]{id});
        return true;

    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id} );


    }
}
