package comp3350.sonicmatic.presentation.login;

import androidx.lifecycle.ViewModel;

import comp3350.sonicmatic.business.AccessProfile;

public class UserViewModel extends ViewModel {

    private AccessProfile profile;
    private boolean loggedOut;

    public UserViewModel()
    {
        loggedOut = false;
    }

    public void setProfile(AccessProfile profile) {
        this.profile = profile;
    }

    public AccessProfile getProfile() {
        return profile;
    }

    public void setLoggedOut(boolean loggedOut) {
        this.loggedOut = loggedOut;
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }
}