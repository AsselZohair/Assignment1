package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        Fragment2.OnFragment2InteractionListener,
        Fragment3.OnFragment3InteractionListener {

    private ActivityMainBinding binding;
    private String userName;
    private String userEmail;
    private String userGender;
    private boolean consentGiven;

    private static final String STATE_NAME = "state_name";
    private static final String STATE_EMAIL = "state_email";
    private static final String STATE_GENDER = "state_gender";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            userName = savedInstanceState.getString(STATE_NAME);
            userEmail = savedInstanceState.getString(STATE_EMAIL);
            userGender = savedInstanceState.getString(STATE_GENDER);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Fragment1())
                    .commit();
        }

        binding.buttonContinue.setOnClickListener(view -> showFragment2());
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment visible = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            binding.buttonContinue.setVisibility(visible instanceof Fragment1
                    ? android.view.View.VISIBLE : android.view.View.GONE);
        });
    }

    @Override
    protected void onSaveInstanceState(@androidx.annotation.NonNull Bundle outState) {
        outState.putString(STATE_NAME, userName);
        outState.putString(STATE_EMAIL, userEmail);
        outState.putString(STATE_GENDER, userGender);
        super.onSaveInstanceState(outState);
    }

    private void showFragment2() {
        binding.buttonContinue.setVisibility(android.view.View.GONE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Fragment2.newInstance(userName))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragment2Continue(String name, String email, String gender) {
        userName = name;
        userEmail = email;
        userGender = gender;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        Fragment3.newInstance(userName, userEmail, userGender))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragment3ConsentChanged(boolean isChecked) {
        consentGiven = isChecked;
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (fragment instanceof Fragment3) {
            ((Fragment3) fragment).updateContinueButton(consentGiven);
        }
    }

    @Override
    public void onFragment3Finish() {
        Toast.makeText(this, "Setup complete for " + userName,
                Toast.LENGTH_SHORT).show();
    }
}



