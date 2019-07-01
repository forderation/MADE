package com.example.myflexiblefragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionDialogFragment extends DialogFragment implements View.OnClickListener{
    Button btnChoose, btnClose;
    RadioGroup rgOptions;
    RadioButton rbSaf, rbMou, rbLvg, rbMoyes;
    onOptionDialogListener OptionDialogListener;

    public OptionDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment fragment = getParentFragment();
        if(fragment instanceof DetailCategoryFragment){
            DetailCategoryFragment detailCategoryFragment = (DetailCategoryFragment)
                    fragment;
            this.OptionDialogListener = detailCategoryFragment.optionDialogListener;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.OptionDialogListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_option_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rgOptions = view.findViewById(R.id.rg_options);
        rbLvg = view.findViewById(R.id.rb_lvg);
        rbMou = view.findViewById(R.id.rb_mou);
        rbSaf = view.findViewById(R.id.rb_saf);
        rbMoyes = view.findViewById(R.id.rb_moyes);
        btnChoose = view.findViewById(R.id.btn_choose);
        btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);
        btnChoose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close:
                getDialog().cancel();
                break;
            case R.id.btn_choose:
                int checkRadioButtonId = rgOptions.getCheckedRadioButtonId();
                if(checkRadioButtonId != -1){
                    String coach = null;
                    switch (checkRadioButtonId){
                        case R.id.rb_saf:
                            coach = rbSaf.getText().toString().trim();
                            break;
                        case R.id.rb_mou:
                            coach = rbMou.getText().toString().trim();
                            break;
                        case R.id.rb_lvg:
                            coach = rbLvg.getText().toString().trim();
                            break;
                        case R.id.rb_moyes:
                            coach = rbMoyes.getText().toString().trim();
                            break;
                    }
                    if(OptionDialogListener != null){
                        OptionDialogListener.onOptionChosen(coach);
                    }
                    getDialog().dismiss();
                }
                break;
        }
    }

    public interface onOptionDialogListener{
        void onOptionChosen(String text);
    }
}
