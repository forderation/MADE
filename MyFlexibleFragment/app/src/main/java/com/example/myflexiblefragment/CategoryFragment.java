package com.example.myflexiblefragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener{

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnDetailCategory = view.findViewById(R.id.btn_detail_category);
        btnDetailCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_detail_category){
            DetailCategoryFragment mDetailCategoryFragment = new DetailCategoryFragment();
            Bundle mBundle = new Bundle();
            mBundle.putString(DetailCategoryFragment.EXTRA_NAME,"Lifestyle");
            String Description = "Kategori ini akan masuk produk lifestyle";
            mDetailCategoryFragment.setArguments(mBundle);
            mDetailCategoryFragment.setDescription(Description);
            FragmentManager mFragmentManager = getFragmentManager();
            if(mFragmentManager!= null){
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame_container,mDetailCategoryFragment,
                        DetailCategoryFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        }
    }
}
