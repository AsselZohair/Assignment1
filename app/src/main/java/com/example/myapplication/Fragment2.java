package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment2Binding;

public class Fragment2 extends Fragment {

    private static final String ARG_NAME = "user_name";
    private Fragment2Binding binding;
    private OnFragment2InteractionListener listener;

    public interface OnFragment2InteractionListener {
        void onFragment2Continue(String name, String email, String gender);
    }

    public static Fragment2 newInstance(String name) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment2InteractionListener) {
            listener = (OnFragment2InteractionListener) context;
        } else {
            throw new IllegalStateException("Host activity must implement OnFragment2InteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = Fragment2Binding.inflate(inflater, container, false);
        String initialName = getArguments() == null ? null : getArguments().getString(ARG_NAME);
        binding.editText.setText(initialName);

        binding.buttonFragment2Continue.setOnClickListener(view -> submitDetails());
        return binding.getRoot();
    }

    private void submitDetails() {
        String name = binding.editText.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        int selectedGenderId = binding.radioGroupGender.getCheckedRadioButtonId();

        if (TextUtils.isEmpty(name)) {
            binding.editText.setError("Enter your name");
            binding.editText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError("Enter a valid email address");
            binding.editTextEmail.requestFocus();
            return;
        }
        if (selectedGenderId == -1) {
            binding.textGenderError.setVisibility(View.VISIBLE);
            return;
        }

        binding.textGenderError.setVisibility(View.GONE);
        String gender = selectedGenderId == R.id.radio_button_female ? "Female" : "Male";
        if (listener != null) {
            listener.onFragment2Continue(name, email, gender);
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
