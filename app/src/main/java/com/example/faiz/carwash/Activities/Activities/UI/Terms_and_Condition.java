package com.example.faiz.carwash.Activities.Activities.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/16/2017.
 */

public class Terms_and_Condition extends android.support.v4.app.Fragment {

    public Button termsBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.terms_and_condition,null);

        termsBtn = (Button)view.findViewById(R.id.terms_and_cond_btn);


        termsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity().getSupportFragmentManager().findFragmentById(R.id.termcontainer) != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                    .remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.termcontainer)).commit();
                }else if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container_main) != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                    .remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container_main)).commit();
                }
            }
        });

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
    @Override
    public void onResume() {
        super.onResume();

    }
}
