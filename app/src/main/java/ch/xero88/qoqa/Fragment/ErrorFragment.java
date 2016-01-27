package ch.xero88.qoqa.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.xero88.qoqa.R;

/**
 * Created by Anthony on 21/01/2016.
 */
public class ErrorFragment extends Fragment {

    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    @Bind(R.id.error2Message) TextView errorMessageTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_error, container, false);
        ButterKnife.bind(this, view);

        if(getArguments() != null) {
            String errorMessage = getArguments().getString(ErrorFragment.ERROR_MESSAGE);
            errorMessageTextView.setText(errorMessage);
        }

        // Inflate the layout for this fragment
        return view;
    }



}
