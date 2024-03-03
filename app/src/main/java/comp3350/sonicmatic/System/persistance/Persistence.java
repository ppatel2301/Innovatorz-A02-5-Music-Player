package comp3350.sonicmatic.System.persistance;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.sonicmatic.exceptions.PersistentTypeMismatchException;
import comp3350.sonicmatic.interfaces.IPersistentItem;

import android.util.Log;

public abstract class Persistence
{

    // ** class constants **
    private final String DB_FOLDER = "database";  // folder in assets where it is located

    // ** class variables **
    public static Context context;

    // ** initialized variables **
    private String database_path = "";
    private boolean init = false;

    // ** constructors **
    public Persistence(String dbName)
    {
        if (!init)
        {
            database_path = initDatabsePersistence(dbName, DB_FOLDER);
        }

    }

    // ** abstract methods **
    protected abstract IPersistentItem fromResultSet(ResultSet rs) throws SQLException;
    public abstract IPersistentItem get(String primaryKey);
    public abstract IPersistentItem update(IPersistentItem item);
    public abstract boolean insert(IPersistentItem item);
    public abstract boolean delete(IPersistentItem item);

    // ** class methods **

    // most of the code in this method is adapted from sample project, written by Franklin Bristow
    public String initDatabsePersistence(String dbName, String dbFolder)
    {

        String result = "";

        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final String DB_PATH = "database";

        String[] assetNames;
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = context.getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            result = dataDirectory.toString() + "/" + dbName;

        } catch (final IOException ioe) {
            Log.d("WARNING", "Unable to access application data: " + ioe.getMessage());
        }


        init = true;
        return result;
    }


    // method adapted from sample project, written by Franklin Bristow
    private void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = context.getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    // ** instance methods **

    protected Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:hsqldb:file:"+database_path+";shutdown=true", "SA", "");
    }


}
