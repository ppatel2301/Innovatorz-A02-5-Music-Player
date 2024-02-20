package comp3350.sonicmatic.System.database;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import comp3350.sonicmatic.exceptions.PersistentTypeMismatchException;
import comp3350.sonicmatic.interfaces.IPersistentItem;

import android.content.res.AssetManager;

public abstract class Persistence
{

    private final String DB_PATH = "database";
    private final String DB_NAME = "SMDB";

    public static Context context;

    public Persistence()
    {
        String[] assetNames;
        AssetManager assetManager = context.getApplicationContext().getAssets();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);

        try {

            assetNames = assetManager.list(DB_PATH);

        } catch (IOException ioe)
        {

        }


    }

    protected Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:hsqldb:file:"+DB_NAME+";shutdown=true", "SA", "ElanElanElanElan");
    }

    public abstract IPersistentItem get(String primaryKey);
    public abstract void update(IPersistentItem item) throws PersistentTypeMismatchException;
    public abstract void insert(IPersistentItem item) throws PersistentTypeMismatchException;
    public abstract void delete(IPersistentItem item) throws PersistentTypeMismatchException;

}
