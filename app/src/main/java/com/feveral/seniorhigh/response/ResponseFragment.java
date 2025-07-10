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
import com.feveral.seniorhigh.utility.HttpUtility;

import java.util.Date;

/**
 * Created by feveral on 2017/8/15.
 */

public class ResponseFragment extends BaseFragment {

    private View fragment;
    private EditText responseEdit;
    private EditText emailEdit;
    private Button submitButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        submitButton.setOnClickListener(view -> submitResponse());
    }

    public void submitResponse(){
        String body = String.format("{\"text\":\"Email: %s\nFeedback: %s\n \"}", responseEdit.getText().toString(), emailEdit.getText().toString());
        HttpUtility.post("https://hooks.slack.com/services/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", body, (response) -> {});
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
