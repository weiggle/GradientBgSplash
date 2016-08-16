package com.weiggle.github.gradientbgsplash;

import android.animation.ArgbEvaluator;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mLayout;
     private ViewPager mViewPager;
     private int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        mLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        colors = getResources().getIntArray(R.array.colorw);
        final MyAdapter adapter = new MyAdapter(R.array.icons,R.array.descs);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int bg;
                if(position < adapter.getCount()-1){
                     bg = (int)argbEvaluator.evaluate(positionOffset,colors[position],colors[position+1]);
                }else{
                    bg = colors[position];
                }

                mLayout.setBackgroundColor(bg);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class MyAdapter extends PagerAdapter{
        private String[] descs;
        private TypedArray icons;

        public MyAdapter(int icoImage, int des) {
            descs = getResources().getStringArray(des);
            icons = getResources().obtainTypedArray(icoImage);
        }

        @Override
        public int getCount() {
            return descs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.item_view, container, false);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            TextView desc = (TextView) view.findViewById(R.id.desc);
            Button btn = (Button) view.findViewById(R.id.btn);
            icon.setImageResource(icons.getResourceId(position, 0));
            desc.setText(descs[position]);
            if (position == getCount() - 1) {
                btn.setVisibility(View.VISIBLE);
            } else {
                btn.setVisibility(View.GONE);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
