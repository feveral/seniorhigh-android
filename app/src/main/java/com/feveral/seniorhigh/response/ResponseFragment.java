package com.feveral.seniorhigh.response;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feveral.seniorhigh.BaseFragment;
import com.feveral.seniorhigh.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by feveral on 2017/8/15.
 */

public class ResponseFragment extends BaseFragment {

    private View fragment;
    private EditText responseEdit;
    private EditText emailEdit;
    private Button submitButton;
    private DatabaseReference firebaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseReference = database.getReference();
        firebaseReference.child("response").child("size").setValue(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_response,container,false);
        responseEdit = (EditText) fragment.findViewById(R.id.response_edit);
        emailEdit = (EditText) fragment.findViewById(R.id.response_email_edit);
        submitButton = (Button) fragment.findViewById(R.id.response_submit);
        setSubmitButtonClick();
        return fragment;
    }

    public void setSubmitButtonClick(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitResponse();
            }
        });
    }

    public void submitResponse(){
        Date date = new Date();
        firebaseReference.child("response").child(date.toString()).child("comment").setValue(responseEdit.getText().toString());
        firebaseReference.child("response").child(date.toString()).child("email").setValue(emailEdit.getText().toString());
        responseEdit.setText("");
        emailEdit.setText("");
        Toast.makeText(getContext(),"您的回復我們已經收到了~謝謝您的肯定與支持",Toast.LENGTH_LONG).show();
    }

    @Override
    public int getTitleColorId() {
        return R.color.primary;
    }

    @Override
    public int getTitleStringId() {
        return R.string.response_text;
    }
}
