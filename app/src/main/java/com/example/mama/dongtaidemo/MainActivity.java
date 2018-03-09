package com.example.mama.dongtaidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lv_dongtai;
    private Dongtai_adapter dongtaiAdapter;
    private List<Map<String,String>> dongtailist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        //获取屏幕宽度，并保存到本地
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(MainActivity.this);
        sharedPreferenceUtil.saveSharePre("screenwidth", getscreenWidth()+"");
        //解析数据
        jiexiData(data);
    }

    private void initView() {
        lv_dongtai = (ListView) findViewById(R.id.lv_dongtai);
    }

    //获取屏幕宽度
    public int getscreenWidth() {

        WindowManager manager = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        Log.d("屏幕宽度","width::::::"+width+":::::height::::::"+height);
        return width;
    }

    //解析数据
    private void jiexiData(String response) {
        dongtailist = new ArrayList<>();
        Map<String,String> map ;
        try {
            JSONObject object = new JSONObject(response);
            if (object.getString("code").toString().equals("0")){
                JSONArray jsonArray = new JSONArray(object.getString("data"));

                for (int i=0;i<jsonArray.length();i++){
                    map = new HashMap<>();
                    map.put("IsPraise",jsonArray.getJSONObject(i).getString("IsPraise")+"");
                    map.put("IsPraiseId",jsonArray.getJSONObject(i).getString("IsPraiseId")+"");
                    map.put("blog_id",jsonArray.getJSONObject(i).getString("blog_id")+"");
                    map.put("content",jsonArray.getJSONObject(i).getString("content")+"");
                    map.put("count",jsonArray.getJSONObject(i).getString("count")+"");
                    map.put("head_img",jsonArray.getJSONObject(i).getString("head_img")+"");
                    map.put("id",jsonArray.getJSONObject(i).getString("id")+"");

                    map.put("post_time",jsonArray.getJSONObject(i).getString("post_time")+"");
                    map.put("post_uid",jsonArray.getJSONObject(i).getString("post_uid")+"");
                    map.put("post_user",jsonArray.getJSONObject(i).getString("post_user")+"");

                    if (Integer.parseInt(jsonArray.getJSONObject(i).getString("count").toString())>0){
                        map.put("praises_content",jsonArray.getJSONObject(i).getString("praises_content"));
                    }else{
                        map.put("praises_content","[]");
                    }
                    try {
                        map.put("post_img",jsonArray.getJSONObject(i).getString("post_img"));
                    }catch (Exception e){
                        map.put("post_img","[]");
                    }
                    dongtailist.add(map);
                }

                if(dongtaiAdapter!=null){
                    dongtaiAdapter.setDongtaiData(dongtailist);
                    dongtaiAdapter.notifyDataSetChanged();
                }else {
                    dongtaiAdapter = new Dongtai_adapter(this,dongtailist);
                    lv_dongtai.setAdapter(dongtaiAdapter);
                    dongtaiAdapter.setSetOnClickListeners(new Dongtai_adapter.setOnClickListeners() {
                        @Override
                        public void onZanCilckListener(int position) {
                            Toast.makeText(getApplicationContext(),"你对第"+position+"记录进行了点赞",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String data="{\n" +
            "    \"code\": 0,\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 130,\n" +
            "            \"content\": \"我发布了图片了吗\",\n" +
            "            \"count\": 0,\n" +
            "            \"head_img\": \"http://pic.qiantucdn.com/58pic/22/06/55/57b2d98e109c6_1024.jpg\",\n" +
            "            \"id\": 130,\n" +
            "            \"post_img\": [],\n" +
            "            \"post_time\": \"2018/03/08 16:01:12\",\n" +
            "            \"post_uid\": 152,\n" +
            "            \"post_user\": \"David\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 128,\n" +
            "            \"content\": \"2222222ceshi\",\n" +
            "            \"count\": 0,\n" +
            "            \"head_img\": \"http://img3.3lian.com/2013/c2/78/d/38.jpg\",\n" +
            "            \"id\": 128,\n" +
            "            \"post_img\": [\n" +
            "                \"http://img3.3lian.com/2013/c2/78/d/38.jpg\",\n" +
            "                \"http://img3.3lian.com/2013/c2/78/d/38.jpg\",\n" +
            "                \"http://img3.3lian.com/2013/c2/78/d/38.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/03/07 17:13:09\",\n" +
            "            \"post_uid\": 141,\n" +
            "            \"post_user\": \"wemex007\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 127,\n" +
            "            \"content\": \"\",\n" +
            "            \"count\": 1,\n" +
            "            \"head_img\": \"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=9b867a04b299a9012f38537575fc600e/4d086e061d950a7b86bee8d400d162d9f2d3c913.jpg\",\n" +
            "            \"id\": 127,\n" +
            "            \"post_img\": [\n" +
            "                \"http://pic.nipic.com/2008-03-22/200832216338855_2.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/03/07 15:22:48\",\n" +
            "            \"post_uid\": 152,\n" +
            "            \"post_user\": \"David\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 127,\n" +
            "                    \"id_pr\": 197,\n" +
            "                    \"time\": \"2018/03/08 16:12:23\",\n" +
            "                    \"user_id\": 152,\n" +
            "                    \"user_img\": \"http://wemex.forxc.cn/upload/20180308/0f4542c3f3d98c48c18b891281f6eaf9.jpg\",\n" +
            "                    \"user_name\": \"David\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 126,\n" +
            "            \"content\": \"\",\n" +
            "            \"count\": 0,\n" +
            "            \"head_img\": \"http://pic.58pic.com/58pic/13/77/57/38M58PICufF_1024.jpg\",\n" +
            "            \"id\": 126,\n" +
            "            \"post_img\": [\n" +
            "                \"http://img15.3lian.com/2015/a1/03/d/163.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/03/07 15:22:38\",\n" +
            "            \"post_uid\": 152,\n" +
            "            \"post_user\": \"David\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 123,\n" +
            "            \"content\": \"555555555\",\n" +
            "            \"count\": 1,\n" +
            "            \"head_img\": \"http://pic.58pic.com/58pic/13/84/94/358PICz58PICmKB_1024.jpg\",\n" +
            "            \"id\": 123,\n" +
            "            \"post_img\": [\n" +
            "                \"http://pic.58pic.com/58pic/13/77/57/38M58PICufF_1024.jpg\",\n" +
            "                \"http://pic.58pic.com/58pic/13/77/57/38M58PICufF_1024.jpg\",\n" +
            "                \"http://pic.58pic.com/58pic/13/77/57/38M58PICufF_1024.jpg\",\n" +
            "                \"http://pic.58pic.com/58pic/13/77/57/38M58PICufF_1024.jpg\",\n" +
            "                \"http://pic.58pic.com/58pic/13/77/57/38M58PICufF_1024.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/03/06 09:44:54\",\n" +
            "            \"post_uid\": 141,\n" +
            "            \"post_user\": \"wemex007\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 123,\n" +
            "                    \"id_pr\": 169,\n" +
            "                    \"time\": \"2018/03/06 19:01:35\",\n" +
            "                    \"user_id\": 152,\n" +
            "                    \"user_img\": \"http://pic16.nipic.com/20110829/3441550_094446882000_2.jpg\",\n" +
            "                    \"user_name\": \"David\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 122,\n" +
            "            \"content\": \"\",\n" +
            "            \"count\": 1,\n" +
            "            \"head_img\": \"http://pic.qiantucdn.com/58pic/13/71/35/24k58PICSiB_1024.jpg\",\n" +
            "            \"id\": 122,\n" +
            "            \"post_img\": [\n" +
            "                \"http://img1.3lian.com/2015/w8/2/d/85.jpg\",\n" +
            "                \"http://pic.58pic.com/58pic/13/18/06/95y58PICJYj_1024.jpg\",\n" +
            "                \"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=2f9334cf252eb938f86072b1bd0bef40/9a504fc2d56285359ea9e50f9aef76c6a7ef6356.jpg\",\n" +
            "                \"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=c8d6b428a5c3793169658e6a83addd30/0b55b319ebc4b745f38fc138c5fc1e178a821580.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/03/06 09:43:33\",\n" +
            "            \"post_uid\": 141,\n" +
            "            \"post_user\": \"wemex007\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 122,\n" +
            "                    \"id_pr\": 171,\n" +
            "                    \"time\": \"2018/03/06 19:01:41\",\n" +
            "                    \"user_id\": 152,\n" +
            "                    \"user_img\": \"http://pic.qiantucdn.com/58pic/26/08/91/58bdf307d07cf_1024.jpg\",\n" +
            "                    \"user_name\": \"David\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 112,\n" +
            "            \"content\": \"\",\n" +
            "            \"count\": 0,\n" +
            "            \"head_img\": \"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=2bf80c7b0633874488c8273f3966b38c/eaf81a4c510fd9f90af607422f2dd42a2834a43c.jpg\",\n" +
            "            \"id\": 112,\n" +
            "            \"post_img\": [\n" +
            "                \"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=1a68e1f43301213fdb3e469f3c8e5ca4/b90e7bec54e736d1b856592591504fc2d5626952.jpg\",\n" +
            "                \"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=1a68e1f43301213fdb3e469f3c8e5ca4/b90e7bec54e736d1b856592591504fc2d5626952.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/03/04 10:51:10\",\n" +
            "            \"post_uid\": 142,\n" +
            "            \"post_user\": \"你马爹\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 111,\n" +
            "            \"content\": \"cxxxxxxxxxx\",\n" +
            "            \"count\": 1,\n" +
            "            \"head_img\": \"http://pic.58pic.com/58pic/11/81/62/90I58PICH9B.jpg\",\n" +
            "            \"id\": 111,\n" +
            "            \"post_img\": [\n" +
            "                \"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=5b492698a3773912d02b8d229070ec6d/b21bb051f81986189b308fa140ed2e738bd4e6a4.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/03/03 15:14:31\",\n" +
            "            \"post_uid\": 168,\n" +
            "            \"post_user\": \"李四\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 111,\n" +
            "                    \"id_pr\": 110,\n" +
            "                    \"time\": \"2018/03/03 15:14:37\",\n" +
            "                    \"user_id\": 168,\n" +
            "                    \"user_img\": \"http://img.taopic.com/uploads/allimg/140713/318752-140G311400431.jpg\",\n" +
            "                    \"user_name\": \"李四\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 110,\n" +
            "            \"content\": \"\",\n" +
            "            \"count\": 2,\n" +
            "            \"head_img\": \"http://pic34.photophoto.cn/20150314/0034034877183417_b.jpg\",\n" +
            "            \"id\": 110,\n" +
            "            \"post_img\": [\n" +
            "                \"http://pic34.photophoto.cn/20150314/0034034877183417_b.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/03/02 17:48:40\",\n" +
            "            \"post_uid\": 152,\n" +
            "            \"post_user\": \"David\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 110,\n" +
            "                    \"id_pr\": 105,\n" +
            "                    \"time\": \"2018/03/02 17:51:54\",\n" +
            "                    \"user_id\": 143,\n" +
            "                    \"user_img\": \"http://img15.3lian.com/2015/a1/13/d/22.jpg\",\n" +
            "                    \"user_name\": \"kangkang\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"blog_id\": 110,\n" +
            "                    \"id_pr\": 173,\n" +
            "                    \"time\": \"2018/03/06 19:06:23\",\n" +
            "                    \"user_id\": 152,\n" +
            "                    \"user_img\": \"http://pic33.photophoto.cn/20141201/0006019005828945_b.jpg\",\n" +
            "                    \"user_name\": \"David\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 108,\n" +
            "            \"content\": \"1345（y y yy hh\",\n" +
            "            \"count\": 1,\n" +
            "            \"head_img\": \"http://pic33.photophoto.cn/20141201/0006019005828945_b.jpg\",\n" +
            "            \"id\": 108,\n" +
            "            \"post_img\": [\n" +
            "                \"http://pic13.nipic.com/20110321/3803997_093550725382_2.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/03/02 17:26:57\",\n" +
            "            \"post_uid\": 142,\n" +
            "            \"post_user\": \"kangkang\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 108,\n" +
            "                    \"id_pr\": 111,\n" +
            "                    \"time\": \"2018/03/04 10:50:35\",\n" +
            "                    \"user_id\": 142,\n" +
            "                    \"user_img\": \"http://pic7.photophoto.cn/20080407/0034034882306001_b.jpg\",\n" +
            "                    \"user_name\": \"kangkang\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 102,\n" +
            "            \"content\": \"1555525\",\n" +
            "            \"count\": 1,\n" +
            "            \"head_img\": \"http://pic13.nipic.com/20110321/3803997_093550725382_2.jpg\",\n" +
            "            \"id\": 102,\n" +
            "            \"post_img\": [\n" +
            "                \"http://pic.58pic.com/58pic/13/17/93/19K58PICUQi_1024.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/02/28 09:21:27\",\n" +
            "            \"post_uid\": 152,\n" +
            "            \"post_user\": \"David\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 102,\n" +
            "                    \"id_pr\": 172,\n" +
            "                    \"time\": \"2018/03/06 19:02:09\",\n" +
            "                    \"user_id\": 152,\n" +
            "                    \"user_img\": \"http://pic5.nipic.com/20091231/3986304_093926011328_2.jpg\",\n" +
            "                    \"user_name\": \"David\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 100,\n" +
            "            \"content\": \"99999999\",\n" +
            "            \"count\": 1,\n" +
            "            \"head_img\": \"http://pic5.nipic.com/20091231/3986304_093926011328_2.jpg\",\n" +
            "            \"id\": 100,\n" +
            "            \"post_img\": [\n" +
            "                \"http://pic5.photophoto.cn/20071228/0005018448652645_b.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/02/27 11:35:19\",\n" +
            "            \"post_uid\": 149,\n" +
            "            \"post_user\": \"123\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 100,\n" +
            "                    \"id_pr\": 117,\n" +
            "                    \"time\": \"2018/03/05 14:42:11\",\n" +
            "                    \"user_id\": 143,\n" +
            "                    \"user_img\": \"http://pic30.photophoto.cn/20140114/0034034810150817_b.jpg\",\n" +
            "                    \"user_name\": \"王五\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 99,\n" +
            "            \"content\": \"QQ\",\n" +
            "            \"count\": 1,\n" +
            "            \"head_img\": \"http://pic38.nipic.com/20140226/18064511_101736603151_2.jpg\",\n" +
            "            \"id\": 99,\n" +
            "            \"post_img\": [\n" +
            "                \"http://pic30.nipic.com/20130617/4499633_102731882000_2.jpg\"\n" +
            "            ],\n" +
            "            \"post_time\": \"2018/02/27 10:49:06\",\n" +
            "            \"post_uid\": 152,\n" +
            "            \"post_user\": \"David\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 99,\n" +
            "                    \"id_pr\": 187,\n" +
            "                    \"time\": \"2018/03/07 15:30:37\",\n" +
            "                    \"user_id\": 152,\n" +
            "                    \"user_img\": \"http://pic30.nipic.com/20130617/4499633_102731882000_2.jpg\",\n" +
            "                    \"user_name\": \"David\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 1,\n" +
            "            \"IsPraiseId\": 0,\n" +
            "            \"blog_id\": 98,\n" +
            "            \"content\": \"第一条\",\n" +
            "            \"count\": 1,\n" +
            "            \"head_img\": \"http://pic.58pic.com/58pic/13/76/32/89858PIC4tU_1024.jpg\",\n" +
            "            \"id\": 98,\n" +
            "            \"post_img\": [],\n" +
            "            \"post_time\": \"2018/02/27 10:47:07\",\n" +
            "            \"post_uid\": 152,\n" +
            "            \"post_user\": \"David\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 98,\n" +
            "                    \"id_pr\": 86,\n" +
            "                    \"time\": \"2018/02/27 10:47:27\",\n" +
            "                    \"user_id\": 152,\n" +
            "                    \"user_img\": \"http://pic.58pic.com/58pic/13/76/32/89858PIC4tU_1024.jpg\",\n" +
            "                    \"user_name\": \"David\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"IsPraise\": 2,\n" +
            "            \"IsPraiseId\": 125,\n" +
            "            \"blog_id\": 97,\n" +
            "            \"content\": \"发布一个新动态\",\n" +
            "            \"count\": 4,\n" +
            "            \"head_img\": \"http://pic.58pic.com/58pic/15/15/10/24v58PICkd6_1024.jpg\",\n" +
            "            \"id\": 97,\n" +
            "            \"post_img\": [],\n" +
            "            \"post_time\": \"2018/02/26 23:30:13\",\n" +
            "            \"post_uid\": 140,\n" +
            "            \"post_user\": \"7\",\n" +
            "            \"praises_content\": [\n" +
            "                {\n" +
            "                    \"blog_id\": 97,\n" +
            "                    \"id_pr\": 92,\n" +
            "                    \"time\": \"2018/02/27 11:30:53\",\n" +
            "                    \"user_id\": 152,\n" +
            "                    \"user_img\": \"http://pic34.photophoto.cn/20150112/0034034439579927_b.jpg\",\n" +
            "                    \"user_name\": \"David\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"blog_id\": 97,\n" +
            "                    \"id_pr\": 109,\n" +
            "                    \"time\": \"2018/03/03 15:14:07\",\n" +
            "                    \"user_id\": 168,\n" +
            "                    \"user_img\": \"http://pic.nipic.com/2008-03-27/2008327841539_2.jpg\",\n" +
            "                    \"user_name\": \"王五\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"blog_id\": 97,\n" +
            "                    \"id_pr\": 112,\n" +
            "                    \"time\": \"2018/03/04 10:50:37\",\n" +
            "                    \"user_id\": 142,\n" +
            "                    \"user_img\": \"http://pic16.photophoto.cn/20100916/0006019019198028_b.jpg\",\n" +
            "                    \"user_name\": \"王五\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"blog_id\": 97,\n" +
            "                    \"id_pr\": 125,\n" +
            "                    \"time\": \"2018/03/05 15:10:24\",\n" +
            "                    \"user_id\": 141,\n" +
            "                    \"user_img\": \"http://pic29.photophoto.cn/20131009/0011024046767402_b.jpg\",\n" +
            "                    \"user_name\": \"wemex007\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"msg\": \"success\"\n" +
            "}";
}
