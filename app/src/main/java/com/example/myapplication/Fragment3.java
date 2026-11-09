package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

public class Fragment3 extends Fragment {

    private static final String ARG_NAME = "user_name";
    private static final String ARG_EMAIL = "user_email";
    private static final String ARG_GENDER = "user_gender";
    private Fragment3Binding binding;
    private OnFragment3InteractionListener listener;

    public interface OnFragment3InteractionListener {
        void onFragment3ConsentChanged(boolean isChecked);
        void onFragment3Finish();
    }

    public static Fragment3 newInstance(String name, String email, String gender) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_GENDER, gender);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment3InteractionListener) {
            listener = (OnFragment3InteractionListener) context;
        } else {
            throw new IllegalStateException("Host activity must implement OnFragment3InteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = Fragment3Binding.inflate(inflater, container, false);
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString(ARG_NAME, "");
            binding.textWelcome.setText("Welcome, " + name + "!");
            binding.textDetails.setText("Email: " + args.getString(ARG_EMAIL, "")
                    + "\nGender: " + args.getString(ARG_GENDER, ""));
        }

        binding.buttonFragment3Continue.setEnabled(false);
        binding.buttonFragment3Continue.setText("Continue");
        binding.checkBoxConsent.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onFragment3ConsentChanged(isChecked);
            }
        });
        binding.buttonFragment3Continue.setOnClickListener(view -> {
            if (binding.checkBoxConsent.isChecked() && listener != null) {
                listener.onFragment3Finish();
            }
        });
        return binding.getRoot();
    }

    public void updateContinueButton(boolean consentGiven) {
        if (binding != null) {
            binding.buttonFragment3Continue.setEnabled(consentGiven);
            binding.buttonFragment3Continue.setText(consentGiven ? "Finish" : "Continue");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}

