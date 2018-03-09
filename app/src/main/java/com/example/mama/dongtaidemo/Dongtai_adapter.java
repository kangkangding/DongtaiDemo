package com.example.mama.dongtaidemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mama on 2017/11/30.
 */

public class Dongtai_adapter extends BaseAdapter {
    private String TAG="Dongtai_adapter";
    Context context;
    List<Map<String, String>> dongtailist;
    public Dongtai_adapter(Context contexts, List<Map<String, String>> dongtailists){
        context=contexts;
        dongtailist=dongtailists;
    }
    @Override
    public int getCount() {
        return dongtailist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolders holder ;
        //防止点赞 图片复用，没用ViewHolders
        view = LayoutInflater.from(context).inflate(R.layout.listview_dongtai_item,null);
        holder = new ViewHolders();
        holder.iv_shoucang=(ImageView) view.findViewById(R.id.iv_shoucang);
        holder.iv_userpic=(ImageView) view.findViewById(R.id.iv_userpic);
        holder.tv_username=(TextView)view.findViewById(R.id.tv_username);
        holder.tv_pingluntime=(TextView)view.findViewById(R.id.tv_pingluntime);
        holder.tv_pingluncontent=(TextView)view.findViewById(R.id.tv_pingluncontent);
        holder.tv_shoucang_count=(TextView)view.findViewById(R.id.tv_shoucang_count);
        holder.ll_picture=(LinearLayout)view.findViewById(R.id.ll_picture);
        holder.ll_userpic=(LinearLayout)view.findViewById(R.id.ll_userpic);
        holder.ll_zan=(LinearLayout)view.findViewById(R.id.ll_zan);
        holder.iv_getpicture=(ImageView) view.findViewById(R.id.iv_getpicture);
        view.setTag(holder);

        Glide.with(context).load(dongtailist.get(i).get("head_img")).transform(new CircleImage(context)).into(holder.iv_userpic);
        holder.tv_username.setText(dongtailist.get(i).get("post_user").toString());
        holder.tv_pingluntime.setText(dongtailist.get(i).get("post_time").toString());
        holder.tv_pingluncontent.setText(dongtailist.get(i).get("content").toString());
        holder.tv_shoucang_count.setText(dongtailist.get(i).get("count").toString());
        holder.tv_shoucang_count.setText(dongtailist.get(i).get("count").toString());
        holder.ll_userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //判断本人是否点赞
        if(dongtailist.get(i).get("IsPraise").toString().contains("2")){
            holder.iv_shoucang.setImageResource(R.mipmap.icon_xin_blue);
            holder.tv_shoucang_count.setTextColor(Color.parseColor("#0CCFF2"));
        }else{
            holder.iv_shoucang.setImageResource(R.mipmap.icon_xin_empty);
            holder.tv_shoucang_count.setTextColor(Color.parseColor("#757677"));
        }

        holder.iv_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClickListeners.onZanCilckListener(i);
            }
        });

        //这段代码是 点赞的用户头像
        String userzan = dongtailist.get(i).get("praises_content").toString();
        if(userzan.length()>3){
            try {
                JSONArray jsonArray = new JSONArray(userzan);
                for (int k=0;k<jsonArray.length();k++){
                    holder.ll_zan.setVisibility(View.VISIBLE);
                    ImageView imageViews = new ImageView(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
                    params.gravity= Gravity.CENTER_VERTICAL;
                    params.setMargins(10,0,0,0);
                    imageViews.setLayoutParams(params);
                    imageViews.setImageResource(R.mipmap.ic_launcher);
                    String picurl=jsonArray.getJSONObject(k).getString("user_img");
                    picurl = picurl.replace("\\","");
                    if (picurl.isEmpty()){
                    }else{
                        Glide.with(context).load(picurl).transform(new CircleImage(context)).placeholder(R.mipmap.ic_launcher).into(imageViews);
                        holder.ll_userpic.addView(imageViews);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //这段代码是用户截图
        String[] pictureArray=stringToarray(dongtailist.get(i).get("post_img").toString());
        Log.d(TAG,pictureArray.length+"::::::::::::::::::"+i+":::::::::::::::"+pictureArray[0]);
        holder.iv_getpicture.setVisibility(View.GONE);
        //一张图的时候，占整个屏幕的宽度
        if(pictureArray.length==1 && !pictureArray[0].isEmpty()){
            SharedPreferenceUtil sputil = new SharedPreferenceUtil(context);
            int width = Integer.parseInt(sputil.getSharePre("screenwidth","0"));

            holder.iv_getpicture.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = holder.iv_getpicture.getLayoutParams();
            //一张图的时候，占整个屏幕的宽度
            params.width = (int) Math.round((width)*0.618);
            holder.iv_getpicture.setLayoutParams(params);

            String picurl=pictureArray[0];
            picurl = picurl.replace("\\","");
            Glide.with(context).load(picurl).placeholder(R.mipmap.ic_launcher).into(holder.iv_getpicture);

            final String finalPicurl = picurl;
            holder.iv_getpicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, PictureActivity.class);
                    intent.putExtra("url",finalPicurl);
                    context.startActivity(intent);
                }
            });
        }else if(pictureArray.length>1){//多张图的时候，进行三等分,宽高等比
            List<String> lispicture = new ArrayList<>();
            for (int j=0;j<pictureArray.length;j++){
                String picurl=pictureArray[j];
                picurl = picurl.replace("\\","");
                lispicture.add(picurl);
            }
            //自适应布局，图片自动换行
            AotuLL aotu = new AotuLL(context);
            Log.d("555555555555",pictureArray.length+"::::::::"+lispicture.size()+"::::::::"+lispicture.toString());
            aotu.initAutoLL(holder.ll_picture,lispicture);
            aotu.setmOnItemClickListener(new AotuLL.mOnItemClickListener() {
                @Override
                public void mOnItemClickListener(String text) {
                    Intent intent = new Intent(context, PictureActivity.class);
                    intent.putExtra("url",text);
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }
    public class  ViewHolders{
        TextView tv_username,tv_pingluntime,tv_pingluncontent,tv_shoucang_count;
        LinearLayout ll_picture,ll_userpic,ll_zan;
        ImageView iv_userpic,iv_shoucang,iv_getpicture,iv_getmore;
    }
    public String[] stringToarray(String data){
        data=data.replace("[","");
        data=data.replace("]","");
        data=data.replace("\"","");
        String[] resultArray=data.split(",");
        return resultArray;
    }

    //点赞的监听
    private setOnClickListeners setOnClickListeners;
    public interface setOnClickListeners{
        void onZanCilckListener(int position);
    }

    public void setSetOnClickListeners(setOnClickListeners setOnClickListenerss){
        setOnClickListeners =setOnClickListenerss;
    }

    //刷新数据
    public  void setDongtaiData(List<Map<String, String>> dongtailists){
        dongtailist=dongtailists;
    }

}
