package xero88.ch.qoqa.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import xero88.ch.qoqa.R;

/**
 * Created by Anthony on 21/01/2016.
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        return view;
    }



}
