package comp3350.sonicmatic.database;

import comp3350.sonicmatic.interfaces.IPersistentItem;

public class DummyPersistent implements IPersistentItem
{
    public String getPrimaryKey()
    {
        return "WOWEE!";
    }
}
