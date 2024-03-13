package comp3350.sonicmatic.presentation.login;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private View loginPanel;
    private View signUpPanel;
    private ImageView logo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loginPanel = root.findViewById(R.id.loginPanel);
        signUpPanel = root.findViewById(R.id.signupPanel);
        logo = root.findViewById(R.id.logoImageView);

        Button login = root.findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.navigation_home, null);
            }
        });

        Button login_signup = root.findViewById(R.id.signUpButton);
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

        Button singup_button = root.findViewById(R.id.signup);
        singup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpPanel.setVisibility(View.INVISIBLE);
                loginPanel.setVisibility(View.VISIBLE);

                //set the contarint end start from the loginPanel image of the logo
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) logo.getLayoutParams();
                params.endToStart = loginPanel.getId();
                logo.setLayoutParams(params);
            }
        });

        return root;
    }
}
