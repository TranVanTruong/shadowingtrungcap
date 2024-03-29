package shadowing.systemtrust.shadowing2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "shadowing_app_db_2019.db3";
    private static String DB_NAME_ASSET = "shadowing_app_db_2019.db3";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabase mDatabase;
    private final Context mContext;
    private final File mDatabasePath;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        mContext = context;
        mDatabasePath = mContext.getDatabasePath(DB_NAME);
    }

    /**
     * asset に格納したデータベースをコピーするための空のデータベースを作成する 
     */
    public void createEmptyDataBase() throws IOException {
        boolean dbExist = checkDataBaseExists();

        if (dbExist) {
            // すでにデータベースは作成されている  
        } else {
            // このメソッドを呼ぶことで、空のデータベースがアプリのデフォルトシステムパスに作られる  
            getReadableDatabase();

            try {
                // asset に格納したデータベースをコピーする  
                copyDataBaseFromAsset();

                String dbPath = mDatabasePath.getAbsolutePath();
                SQLiteDatabase checkDb = null;
                try {
                    checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
                } catch (SQLiteException e) {
                }

                if (checkDb != null) {
                    checkDb.setVersion(DATABASE_VERSION);
                    checkDb.close();
                }

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * 再コピーを防止するために、すでにデータベースがあるかどうか判定する 
     *
     * @return 存在している場合 {@code true} 
     */
    private boolean checkDataBaseExists() {
        String dbPath = mDatabasePath.getAbsolutePath();

        SQLiteDatabase checkDb = null;
        try {
            checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // データベースはまだ存在していない  
        }

        if (checkDb == null) {
            // データベースはまだ存在していない  
            return false;
        }

        int oldVersion = checkDb.getVersion();
        int newVersion = DATABASE_VERSION;

        if (oldVersion == newVersion) {
            // データベースは存在していて最新  
            checkDb.close();
            return true;
        }

        // データベースが存在していて最新ではないので削除  
        File f = new File(dbPath);
        f.delete();
        return false;
    }

    /**
     * asset に格納したデーだベースをデフォルトのデータベースパスに作成したからのデータベースにコピーする 
     */
    private void copyDataBaseFromAsset() throws IOException{

        // asset 内のデータベースファイルにアクセス  
        InputStream mInput = mContext.getAssets().open(DB_NAME_ASSET);

        // デフォルトのデータベースパスに作成した空のDB  
        OutputStream mOutput = new FileOutputStream(mDatabasePath);

        // コピー  
        byte[] buffer = new byte[1024];
        int size;
        while ((size = mInput.read(buffer)) > 0) {
            mOutput.write(buffer, 0, size);
        }

        // Close the streams  
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        return getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public synchronized void close() {
        if(this.mDatabase != null)
            this.mDatabase.close();

        super.close();
    }
    public List<Product> getEmployees() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(mDatabasePath.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM Unit_tbl";
        Cursor cursor = db.rawQuery(query, null);
        List<Product> list = new ArrayList<Product>();
        while(cursor.moveToNext()) {
            Product employee = new Product();
            employee.setId(cursor.getInt(0));
            employee.setName(cursor.getString(1));
            employee.setQuantity(cursor.getInt(2));
            list.add(employee);
        }
        db.close();
        return list;
    }
    public List<Product> getFraccionInfo(int id) {
        List<Product> list = new ArrayList<Product>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM Section_tbl WHERE UnitID ='" + id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Product employee = new Product();
                employee.setId(cursor.getInt(0));
                employee.setName(cursor.getString(1));
                employee.setQuantity(cursor.getInt(2));
                list.add(employee);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public Product getProduct(String id) {
        Product list = new Product();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM Section_Content WHERE AudioFile ='" + id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            list.setId(cursor.getInt(0));
            list.setName(cursor.getString(2));
            list.setDetail(cursor.getString(7));
        }
        return list;
    }
}  
