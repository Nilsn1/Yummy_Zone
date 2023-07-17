package com.nilscreation.yummyzone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactFragment extends Fragment {

    TextView subject, complain;
    Button submit;

    public ContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        subject = view.findViewById(R.id.subject);
        complain = view.findViewById(R.id.complain);
        submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userSubject = subject.getText().toString();
                String userComplain = complain.getText().toString();
                if (subject.getText().toString().isEmpty() && complain.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    String[] to = {"nileshsonawanen1@gmail.com"};
                    intent.putExtra(Intent.EXTRA_EMAIL, to);
                    intent.putExtra(Intent.EXTRA_SUBJECT, userSubject);
                    intent.putExtra(Intent.EXTRA_TEXT, userComplain);

                    startActivity(Intent.createChooser(intent, "Send Email"));
                }
            }
        });

        return view;
    }
}