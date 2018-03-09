package com.example.mama.dongtaidemo;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingkang on 2018/1/24.
 * 自动换行布局
 */

public class AotuLL {

    public Context context;

    public AotuLL(Context contexts) {
        context =contexts;
    }
    private mOnItemClickListener itemClickListener=null;

    public interface mOnItemClickListener{
        void mOnItemClickListener(String text);
    }

    public void setmOnItemClickListener(mOnItemClickListener listener){
        itemClickListener=listener;
    }
    //    绘制自动换行的线性布局
    public  void initAutoLL(final LinearLayout ll_parent, final List<String> datas) {
        Log.d(":::::",datas.size()+"::::::3333333333333");
        SharedPreferenceUtil sputil = new SharedPreferenceUtil(context);
        int widthssss = Integer.parseInt(sputil.getSharePre("screenwidth","0"));


        final List<LinearLayout> llviews = new ArrayList<>();
//        每一行的布局，初始化第一行布局
        LinearLayout rowLL = new LinearLayout(context);

        llviews.add(rowLL);

        LinearLayout.LayoutParams rowLP =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        float rowMargin = dipToPx(10);
        rowLP.setMargins(0, (int) rowMargin, 0, 0);
        rowLL.setLayoutParams(rowLP);
        boolean isNewLayout = false;
        //dipToPx(80) 右边间距
        float maxWidth = getScreenWidth()-dipToPx(32);

//        剩下的宽度
        float elseWidth = maxWidth;
        LinearLayout.LayoutParams textViewLP =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLP.setMargins((int) dipToPx(8), 0, 0, 0);
        for (int i = 0; i < datas.size(); i++) {
//            若当前为新起的一行，先添加旧的那行
//            然后重新创建布局对象，设置参数，将isNewLayout判断重置为false
            if (isNewLayout) {
                ll_parent.addView(rowLL);
                rowLL = new LinearLayout(context);
                rowLL.setLayoutParams(rowLP);
                isNewLayout = false;

                llviews.add(rowLL);
            }
//            计算是否需要换行
            final View textView = LayoutInflater.from(context).inflate(R.layout.view_dongtai_picture, null);
            ImageView imageView = (ImageView) textView.findViewById(R.id.iv_selectpicture);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            //动态设置图片的宽度
            params.width=Math.round((widthssss-dipToPx(16)-dipToPx(32))/3);
            params.height=Math.round((widthssss-dipToPx(16)-dipToPx(32))/3);
            imageView.setLayoutParams(params);

            Glide.with(context).load(datas.get(i)).into(imageView);

            final String url = datas.get(i);
            textView.measure(0, 0);
//            若是一整行都放不下这个文本框，添加旧的那行，新起一行添加这个文本框
            if (maxWidth < textView.getMeasuredWidth()) {
                ll_parent.addView(rowLL);
                rowLL = new LinearLayout(context);
                rowLL.setLayoutParams(rowLP);
                rowLL.addView(textView);
                isNewLayout = true;
                llviews.add(rowLL);
                continue;
            }
//            若是剩下的宽度小于文本框的宽度（放不下了）
//            添加旧的那行，新起一行，但是i要-1，因为当前的文本框还未添加
            if (elseWidth < textView.getMeasuredWidth()) {
                isNewLayout = true;
                i--;
//                重置剩余宽度
                elseWidth = maxWidth;
                continue;
            } else {
//                剩余宽度减去文本框的宽度+间隔=新的剩余宽度
                elseWidth -= textView.getMeasuredWidth() + dipToPx(8);
                if (rowLL.getChildCount() == 0) {
                    rowLL.addView(textView);
                } else {
                    textView.setLayoutParams(textViewLP);
                    rowLL.addView(textView);
                }
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.mOnItemClickListener(url);
                }
            });
        }
//        添加最后一行，但要防止重复添加
        ll_parent.removeView(rowLL);
        ll_parent.addView(rowLL);
    }

    //    dp转px
    private  float dipToPx(int dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dipValue,
                context.getResources().getDisplayMetrics());
    }

    //  获得评论宽度
    private  float getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
