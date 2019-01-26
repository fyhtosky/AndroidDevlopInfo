package com.example.fanyuanhua.netpower.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.example.fanyuanhua.netpower.R;

public class DrawPageFragment extends Fragment {
   public static final String LayoutId="LayoutId";
   @LayoutRes
   int sampleLayoutRes;
    public static DrawPageFragment newInstance(int layoutId){
        DrawPageFragment drawPageFragment =new DrawPageFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(LayoutId,layoutId);
        drawPageFragment.setArguments(bundle);
        return drawPageFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            sampleLayoutRes = args.getInt(LayoutId);
        }

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.draw_page_fragment, container, false);
        ViewStub sampleStub = (ViewStub) view.findViewById(R.id.sampleStub);
        sampleStub.setLayoutResource(sampleLayoutRes);
        sampleStub.inflate();
        return view;
    }
}
