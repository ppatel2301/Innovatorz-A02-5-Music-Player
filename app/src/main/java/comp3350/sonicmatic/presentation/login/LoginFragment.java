package comp3350.sonicmatic.presentation.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.business.access.AccessPlaylist;
import comp3350.sonicmatic.business.access.AccessProfile;
import comp3350.sonicmatic.databinding.FragmentLoginBinding;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.presentation.player.MusicViewModel;
import comp3350.sonicmatic.presentation.playlist.PlaylistViewModel;

public class LoginFragment extends Fragment {

    private final int USERNAME_PASSWORD_LENGTH = 8;
    private FragmentLoginBinding binding;
    private View loginPanel;
    private View signUpPanel;
    private ImageView logo;
    private TextView login_username;
    private TextView login_password;
    private TextView signup_username;
    private TextView signup_password;
    private TextView signup_password_verify;
    private TextView signup_displayName;

    private UserViewModel userViewModel;
    private PlaylistViewModel playlistViewModel;
    private MusicViewModel musicViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Init all the instance variables
        loginPanel = binding.loginPanel;
        signUpPanel = binding.signupPanel;
        logo = binding.logoImageView;

        login_username = binding.loginUsername;
        login_password = binding.loginPass;

        signup_username = binding.signupUsername;
        signup_password = binding.signupPass;

        signup_password_verify = binding.signupPassVerify;
        signup_displayName = binding.signupDisplayName;

        Button login = binding.loginButton;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateLogin())
                {
                    navigateToMainContent(view);

                    disableEnablePlaylistFeature(true);

                    userViewModel.setLoggedOut(false);
                }else{
                    Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button login_signup = binding.signUpButton;
        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPanel.setVisibility(View.INVISIBLE);
                signUpPanel.setVisibility(View.VISIBLE);

                //set the contarint end start from the login_sinup image of the logo
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) logo.getLayoutParams();
                params.endToStart = signUpPanel.getId();
                logo.setLayoutParams(params);
            }
        });

        Button singup_button = binding.signup;
        singup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateSignUp())
                {
                    openLoginPanel();
                }else{
                    Toast.makeText(getContext(), "Invalid signup details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button signup_login = binding.signupLogin;
        signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPanel();
            }
        });

        Button guest_login = binding.guestButton;
        guest_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Guests cannot create playlists. Login to access the playlists", Snackbar.LENGTH_LONG).show();

                userViewModel.setProfile(new AccessProfile());

                navigateToMainContent(view);

                disableEnablePlaylistFeature(false);
            }
        });
        return root;
    }

    private boolean validateLogin()
    {
        boolean valid_login_info = false;

        String username = login_username.getText().toString();
        String password = login_password.getText().toString();

        if(!username.isEmpty() && !password.isEmpty())
        {
            AccessProfile accessProfile = new AccessProfile();
            boolean login_success = accessProfile.login(username, password);

            if(login_success)
            {
                valid_login_info = true;
                userViewModel.setProfile(accessProfile);

                AccessPlaylist accessPlaylist = new AccessPlaylist();

                ArrayList<IPlaylist> userPlaylists = accessPlaylist.getPlaylists(accessProfile);
                if(userPlaylists.isEmpty())
                {
                    playlistViewModel.updateList(new ArrayList<>());
                }else{
                    playlistViewModel.updateList(userPlaylists);
                }

                musicViewModel.clearListeningHistory();
            }
        }
        return valid_login_info;
    }

    private boolean validateSignUp()
    {
        boolean valid_signup_info = false;

        String username = signup_username.getText().toString();
        String password = signup_password.getText().toString();
        String password_verify = signup_password_verify.getText().toString();
        String displayName = signup_displayName.getText().toString();

        if(!username.isEmpty() && !password.isEmpty() && !password_verify.isEmpty() && !displayName.isEmpty())
        {
            if(password.length() < USERNAME_PASSWORD_LENGTH)
            {
                Toast.makeText(getContext(), "Password created must contain 8 characters", Toast.LENGTH_SHORT).show();
            }else{

                if(password.equals(password_verify))
                {
                    AccessProfile accessProfile = new AccessProfile();
                    boolean profile_created = accessProfile.newProfile(username,displayName, password, false);
                    if(profile_created)
                    {
                        valid_signup_info = true;
                        Toast.makeText(getContext(), "Profile created", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return valid_signup_info;
    }

    private void openLoginPanel()
    {
        signUpPanel.setVisibility(View.INVISIBLE);
        loginPanel.setVisibility(View.VISIBLE);

        //set the contarint end start from the loginPanel image of the logo
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) logo.getLayoutParams();
        params.endToStart = loginPanel.getId();
        logo.setLayoutParams(params);
    }

    private void navigateToMainContent(View view)
    {
        Navigation.findNavController(view).navigate(R.id.navigation_home, null);

        View layout = requireActivity().findViewById(R.id.collasped_music_layout1);
        layout.setVisibility(View.VISIBLE);

        View bottom_view = requireActivity().findViewById(R.id.nav_view);
        bottom_view.setVisibility(View.VISIBLE);
    }

    private void disableEnablePlaylistFeature(boolean bool)
    {
        BottomNavigationView layout = requireActivity().findViewById(R.id.nav_view);
        Menu menu = layout.getMenu();
        menu.findItem(R.id.navigation_dashboard).setVisible(bool);
    }
}