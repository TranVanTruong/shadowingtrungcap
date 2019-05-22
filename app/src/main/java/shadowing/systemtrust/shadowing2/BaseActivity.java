package shadowing.systemtrust.shadowing2;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class BaseActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    public DataBaseHelper mDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setDatabase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void setDatabase() {
        mDatabase = new DataBaseHelper(this);
        try {
            mDatabase.createEmptyDataBase();
            db = mDatabase.openDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        } catch(SQLException sqle){
            throw sqle;
        }
    }
}
