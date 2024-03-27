package comp3350.sonicmatic.persistance;

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

import comp3350.sonicmatic.interfaces.IPersistentItem;

import android.util.Log;

public abstract class Persistence
{

    // ** class variables **
    private static final int DB_STRING_LENGTH = 128; // all strings in the database are of this length
    public static Context context;
    private static String full_database_path = "";
    private static boolean init = false;

    // ** constructors **
    public Persistence(String dbName, String dbPath)
    {
        if (!init)
        {
            full_database_path = initDatabsePersistence(dbName, dbPath);
        }

    }

    // ** abstract methods **
    protected abstract IPersistentItem fromResultSet(ResultSet rs) throws SQLException;
    public abstract IPersistentItem get(String primaryKey);
    public abstract IPersistentItem update(IPersistentItem item);
    public abstract boolean insert(IPersistentItem item);
    public abstract boolean delete(IPersistentItem item);

    public IPersistentItem get(String [] primaryKey)
    {
        IPersistentItem return_me;

        if (primaryKey != null && primaryKey.length > 0)
        {
            return_me = get(primaryKey[0]);
        }
        else
        {
            return_me = null;
        }

        return return_me;
    }
    // ** class methods **

    // determines if a string is suitable to be in the database
    public static boolean isStringOkay(String testMe)
    {
        return testMe != null
                && !testMe.isEmpty() && testMe.length() <= DB_STRING_LENGTH;
    }

    // most of the code in this method is adapted from sample project, written by Franklin Bristow
    public static String initDatabsePersistence(String dbName, String dbPath)
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

        if (context != null)
        {
            String[] assetNames;
            File dataDirectory = context.getDir(dbPath, Context.MODE_PRIVATE);
            AssetManager assetManager = context.getAssets();

            try {

                assetNames = assetManager.list(dbPath);
                for (int i = 0; i < assetNames.length; i++) {
                    assetNames[i] = dbPath + "/" + assetNames[i];
                }

                copyAssetsToDirectory(assetNames, dataDirectory);

                result = dataDirectory.toString() + "/" + dbName;

            } catch (final IOException ioe) {
                Log.d("WARNING", "Unable to access application data: " + ioe.getMessage());
            }

            init = true;
        }

        return result;
    }


    // method adapted from sample project, written by Franklin Bristow
    private static void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
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
        return DriverManager.getConnection("jdbc:hsqldb:file:"+full_database_path+";shutdown=true", "SA", "");
    }

}
