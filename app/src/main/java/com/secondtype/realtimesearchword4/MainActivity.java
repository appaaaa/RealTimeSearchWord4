package com.secondtype.realtimesearchword4;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    //test git
    // test 2 git
    //branch change
    Button refreshBtn;

    public static Boolean switchs = false;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SearchWord> myDataset;

    private LinearLayout loadingLayout;
    private TextView loadingTime;
    private TextView titleTime;

    private ArrayList<Reply> replyList;




    //////////// + tab 대신 임시 사용 //////////////
    private ImageButton firstButton;
    private ImageButton secondButton;
    /////////////////////////////////////////////

    ////////////////// + list all ////////////////
    LinearLayout linearLayoutListAll;

    TextView listAllText1;
    TextView listAllText2;
    TextView listAllText3;
    TextView listAllText4;
    TextView listAllText5;
    TextView listAllText6;
    TextView listAllText7;
    TextView listAllText8;
    TextView listAllText9;
    TextView listAllText10;
    TextView listAllText11;
    TextView listAllText12;
    TextView listAllText13;
    TextView listAllText14;
    TextView listAllText15;
    TextView listAllText16;
    TextView listAllText17;
    TextView listAllText18;
    TextView listAllText19;
    TextView listAllText20;
    ///////////////////////////////////////////////////

    /////// 데이터 수신중 클릭 방지 /////////
    LinearLayout contentLinearlayout;
    //////////////////////////////////////////

    private ArrayList<String> listAll;

    ////// + button naver, daum 추가
    Button naverBtn;
    Button daumBtn;
    ////////////////////////////////

    ////// + searchword 만 따로 받아오기. 리스트 표시용
    ArrayList<String> wordList;
    ////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseMessagingService.Subscribe);
        FirebaseInstanceId.getInstance().getToken();


        loadingLayout = (LinearLayout)findViewById(R.id.linearlayout_loading);
        loadingTime = (TextView)findViewById(R.id.textview_starttime);
        titleTime = (TextView)findViewById(R.id.textview_title);

        ///////////////tab 대신 임시사용 ///////////////////
        firstButton = (ImageButton) findViewById(R.id.button_first);
        firstButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
                linearLayoutListAll.setVisibility(View.GONE);
            }
        });

        secondButton = (ImageButton) findViewById(R.id.button_second);
        secondButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setVisibility(View.GONE);
                linearLayoutListAll.setVisibility(View.VISIBLE);
            }
        });
        ////////////////////////////////////////////////////

        /////////////////// + listview list all /////////////
        linearLayoutListAll = (LinearLayout)findViewById(R.id.linearlayout_listall);

        listAllText1 = (TextView)findViewById(R.id.textview_listall_text1);
        listAllText2 = (TextView)findViewById(R.id.textview_listall_text2);
        listAllText3 = (TextView)findViewById(R.id.textview_listall_text3);
        listAllText4 = (TextView)findViewById(R.id.textview_listall_text4);
        listAllText5 = (TextView)findViewById(R.id.textview_listall_text5);
        listAllText6 = (TextView)findViewById(R.id.textview_listall_text6);
        listAllText7 = (TextView)findViewById(R.id.textview_listall_text7);
        listAllText8 = (TextView)findViewById(R.id.textview_listall_text8);
        listAllText9 = (TextView)findViewById(R.id.textview_listall_text9);
        listAllText10 = (TextView)findViewById(R.id.textview_listall_text10);
        listAllText11 = (TextView)findViewById(R.id.textview_listall_text11);
        listAllText12 = (TextView)findViewById(R.id.textview_listall_text12);
        listAllText13 = (TextView)findViewById(R.id.textview_listall_text13);
        listAllText14 = (TextView)findViewById(R.id.textview_listall_text14);
        listAllText15 = (TextView)findViewById(R.id.textview_listall_text15);
        listAllText16 = (TextView)findViewById(R.id.textview_listall_text16);
        listAllText17 = (TextView)findViewById(R.id.textview_listall_text17);
        listAllText18 = (TextView)findViewById(R.id.textview_listall_text18);
        listAllText19 = (TextView)findViewById(R.id.textview_listall_text19);
        listAllText20 = (TextView)findViewById(R.id.textview_listall_text20);
        ///////////////////////////////////////////////////////////////



        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        mAdapter = new SearchWordAdapter(getApplication(),myDataset, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);


        refreshBtn = (Button)findViewById(R.id.button_refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myDataset.clear();
                switchs = false;
                NaverParser mParser = new NaverParser(); // Asynctask는 1회용이라서 매번 다시 생성해줘야함
                mParser.execute();

            }
        });

        //// naver
        naverBtn = (Button)findViewById(R.id.button_naver);
        naverBtn.setTextSize(19);
        naverBtn.setTextColor(Color.parseColor("#00c73c"));

        naverBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                daumBtn.setTextSize(16);
                daumBtn.setTextColor(Color.parseColor("#000000"));
                naverBtn.setTextSize(19);
                naverBtn.setTextColor(Color.parseColor("#00c73c"));

                myDataset.clear();
                switchs = false;

                NaverParser NParser = new NaverParser();
                NParser.execute();
            }
        });

        //// daum
        daumBtn = (Button)findViewById(R.id.button_daum);
        daumBtn.setTextSize(16);
        daumBtn.setTextColor(Color.parseColor("#000000"));

        daumBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                daumBtn.setTextSize(19);
                daumBtn.setTextColor(Color.parseColor("#f1685e"));
                naverBtn.setTextSize(16);
                naverBtn.setTextColor(Color.parseColor("#000000"));
                Toast toast = Toast.makeText(getApplicationContext(), "열심히 카페인을 들이부으며 준비중입니다.", Toast.LENGTH_SHORT);
                toast.show();

                myDataset.clear();
                switchs = false;

                DaumParser DParser = new DaumParser();
                DParser.execute();
            }
        });

        /////////////////////////////////////////////

        ////// + searchword만 따로 받아오기
        wordList = new ArrayList<>();
        ///////////////////////////////////

        ////////// + 데이터 수신 중 막기 //////////
        //  contentLinearlayout = (LinearLayout)findViewById(R.id.linearlayout_contents);
        //  contentLinearlayout.setClickable(false);

        myDataset.clear();
        NaverParser NParser = new NaverParser();
        NParser.execute( );
    }


    public class NaverParser extends AsyncTask<Void, SearchWord, ArrayList<SearchWord>> {

        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";
        Integer position = 0;

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {
            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
                Elements mElements = mDocument.select("div.select_date ul li");

                ////// + list만 빨리 먼저 받아오기
                wordList.clear();
                for(int i = 0; i < 20; i++){
                    wordList.add(mElements.get(i).select("span.title").text());
                }

                for(int i = 0; i < mElements.size(); i++) {
                    SearchWord searchWord = new SearchWord();
                    searchWord.setNumber(Integer.toString(i + 1));
                    searchWord.setWord(mElements.get(i).select("span.title").text());

                    String newsUrl = "https://search.naver.com/search.naver?where=news&sm=tab_jum&ie=utf8&query=" + searchWord.getWord();

                    try {
                        Document mDocument2 = Jsoup.connect(newsUrl).get();
                        Elements newsElement = mDocument2.select("div.news ul.type01>li");

                        if (newsElement != null) { //기사에 이미지 있는 경우만 이미지 받아옴
                            if(!newsElement.get(0).select("div.thumb a img").attr("src").isEmpty()){
                                String temp = newsElement.get(0).select("div.thumb a img").attr("src");
                                String s = newsElement.get(0).select("div.thumb a img").attr("src").substring(0, temp.length()-28);
                                searchWord.setNewsImage(s);
                            }
                            else{
                                searchWord.setNewsImage("NOIMAGE");
                            }

                            Log.v("naver", newsElement.get(0).select("dl dt a").attr("href"));
                            Log.v("naver", newsElement.get(0).select("dl dt a").text());

                            searchWord.setNewsURL(newsElement.get(0).select("dl dt a").attr("href"));
                            searchWord.setNewsURL2(newsElement.get(1).select("dl dt a").attr("href"));
                            searchWord.setNewsURL3(newsElement.get(2).select("dl dt a").attr("href"));
                            searchWord.setNewsURL4(newsElement.get(3).select("dl dt a").attr("href"));

                            searchWord.setNewsTitle(newsElement.get(0).select("dl dt a").text());
                            searchWord.setNewsTitle2(newsElement.get(1).select("dl dt a").text());
                            searchWord.setNewsTitle3(newsElement.get(2).select("dl dt a").text());
                            searchWord.setNewsTitle4(newsElement.get(3).select("dl dt a").text());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";

                    try{
                        Document mDocument3 = Jsoup.connect(replysUrl).get();
                        Elements replyElements = mDocument3.select("ul.type01 li");

                        for(int j = 0; j < replyElements.size(); j++){
                            Reply mReply = new Reply();
                            mReply.name = replyElements.get(j).select(".user_name").text();

                            if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
                                mReply.text = replyElements.get(j).select(".cmmt").text();
                                mReply.time = replyElements.get(j).select(".time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
                                mReply.count2 = replyElements.get(j).select(".sub_interest").text();
                                mReply.count3 = "  ";
                            }
                            else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
                                mReply.text = replyElements.get(j).select(".txt_link").text();
                                mReply.time = replyElements.get(j).select(".sub_time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_reply").text();
                                mReply.count2 = replyElements.get(j).select(".sub_like").text();
                                mReply.count3 = replyElements.get(j).select(".sub_dis").text();
                            }
                            searchWord.getReplyArrayList().add(mReply);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    publishProgress(searchWord);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return tempList;
        }

        @Override
        protected void onPreExecute() {
            currentTimer();
            loadingLayout.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(SearchWord... values) {
            myDataset.add(values[0]);
            mAdapter.notifyItemInserted(position++);

            ListAllBinding(Integer.parseInt(values[0].getNumber()));

            if(Integer.parseInt(values[0].getNumber()) == 2){
                loadingLayout.setVisibility(View.GONE);
            }

        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            switchs = true; //데이터를 다 가져왔으면 클릭 가능
            mAdapter.notifyDataSetChanged();
        }
    }

    public class DaumParser extends AsyncTask<Void, SearchWord, ArrayList<SearchWord>> {

        String url = "http://www.daum.net";
        Integer position = 0;

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... params) {
            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
                Elements mElements = mDocument.select("ol.list_hotissue li");


                wordList.clear();
                for (int i = 0; i < mElements.size(); i++) {
                    wordList.add(mElements.get(i).select("a[href]").get(0).text()); //title
                 }


                 for(int i = 0; i < mElements.size(); i++){
                     SearchWord searchWord = new SearchWord();
                     searchWord.setNumber(Integer.toString(i + 1));
                     searchWord.setWord(mElements.get(i).select("a[href]").get(0).text());

                     String url = "http://search.daum.net/search?nil_suggest=btn&w=tot&DA=SBC&q=" + searchWord.getWord();

                     try{
                         Document mDocument2 = Jsoup.connect(url).get();
                         Elements newsElement = mDocument2.select("ul#clusterResultUL li");

                         try{
                             String onlyImageURL = "https://search.naver.com/search.naver?where=news&sm=tab_jum&ie=utf8&query=" + searchWord.getWord();
                             Document onlyImageDocument = Jsoup.connect(onlyImageURL).get();
                             Elements onlyImageElement = onlyImageDocument.select("div.news ul.type01>li");

                             if(!onlyImageElement.get(0).select("div.thumb a img").attr("src").isEmpty()){
                                 String temp = onlyImageElement.get(0).select("div.thumb a img").attr("src");
                                 String s = onlyImageElement.get(0).select("div.thumb a img").attr("src").substring(0, temp.length()-28);
                                 searchWord.setNewsImage(s);
                             }
                             else{
                                 searchWord.setNewsImage("NOIMAGE");
                             }
                         }catch(Exception e){
                             e.printStackTrace();
                         }

                         searchWord.setNewsURL(newsElement.get(0).select("div.wrap_cont a").attr("href"));
                         searchWord.setNewsURL2(newsElement.get(1).select("div.wrap_cont a").attr("href"));
                         searchWord.setNewsURL3(newsElement.get(2).select("div.wrap_cont a").attr("href"));
                         searchWord.setNewsURL4(newsElement.get(3).select("div.wrap_cont a").attr("href"));

                         searchWord.setNewsTitle(newsElement.get(0).select("div.wrap_cont a.f_link_b").text());
                         searchWord.setNewsTitle2(newsElement.get(1).select("div.wrap_cont a.f_link_b").text());
                         searchWord.setNewsTitle3(newsElement.get(2).select("div.wrap_cont a.f_link_b").text());
                         searchWord.setNewsTitle4(newsElement.get(3).select("div.wrap_cont a.f_link_b").text());

                     }catch(Exception e){
                         e.printStackTrace();
                     }

                     try{
                         String replysUrl = "http://search.daum.net/search?w=social&m=web&period=&sd=&ed=&sort_type=socialweb&nil_search=btn&enc=utf8&q=" + searchWord.getWord()+ "&DA=STC";

                         Document mDocument3 = Jsoup.connect(replysUrl).get();
                         Elements replyElements = mDocument3.select("ul.list_live li");

                         for(int j = 0; j < replyElements.size(); j++){
                             Reply mReply = new Reply();

                             mReply.name = replyElements.get(j).select("div.wrap_tit a strong").text();
                             mReply.text = replyElements.get(j).select("span.f_eb").text();
                             mReply.count1 = replyElements.get(j).select("span.f_nb").text();
                             mReply.count2 = replyElements.get(j).select("a.f_nb").get(0).text();
                             mReply.count3 = "  ";

                             searchWord.getReplyArrayList().add(mReply);
                         }

                     }catch (Exception e){
                         e.printStackTrace();
                     }
                     publishProgress(searchWord);
                 }

            }catch(Exception e){
                e.printStackTrace();
            }
            return tempList;
        }


        @Override
        protected void onPreExecute() {
            currentTimer();
            loadingLayout.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(SearchWord... values) {
            myDataset.add(values[0]);
            mAdapter.notifyItemInserted(position++);
            Log.v("datatest", values[0].getNumber() + ". " + values[0].getWord());
            ListAllBinding(Integer.parseInt(values[0].getNumber()));

            if(Integer.parseInt(values[0].getNumber()) == 2){
                loadingLayout.setVisibility(View.GONE);
            }

        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            switchs = true; //데이터를 다 가져왔으면 클릭 가능
            mAdapter.notifyDataSetChanged();
        }
    }



    public void currentTimer(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
        String formateDate = sdfNow.format(date);
        loadingTime.setText(formateDate);
        titleTime.setText(formateDate+" 지금이슈");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void ListAllBinding(int num){
        Log.v("binding", Integer.toString(num));
        switch(num){
            case 1:
                listAllText1.setText("1. " + wordList.get(0));
                break;
            case 2:
                listAllText2.setText("2. " + wordList.get(1));
                break;
            case 3:
                listAllText3.setText("3. " + wordList.get(2));
                break;
            case 4:
                listAllText4.setText("4. " + wordList.get(3));
                break;
            case 5:
                listAllText5.setText("5. " + wordList.get(4));
                break;
            case 6:
                listAllText6.setText("6. " + wordList.get(5));
                break;
            case 7:
                listAllText7.setText("7. " + wordList.get(6));
                break;
            case 8:
                listAllText8.setText("8. " + wordList.get(7));
                break;
            case 9:
                listAllText9.setText("9. " + wordList.get(8));
                break;
            case 10:
                listAllText10.setText("10. " + wordList.get(9));
                break;
            case 11:
                listAllText11.setText("11. " + wordList.get(10));
                break;
            case 12:
                listAllText12.setText("12. " + wordList.get(11));
                break;
            case 13:
                listAllText13.setText("13. " + wordList.get(12));
                break;
            case 14:
                listAllText14.setText("14. " + wordList.get(13));
                break;
            case 15:
                listAllText15.setText("15. " + wordList.get(14));
                break;
            case 16:
                listAllText16.setText("16. " + wordList.get(15));
                break;
            case 17:
                listAllText17.setText("17. " + wordList.get(16));
                break;
            case 18:
                listAllText18.setText("18. " + wordList.get(17));
                break;
            case 19:
                listAllText19.setText("19. " + wordList.get(18));
                break;
            case 20:
                listAllText20.setText("20. " + wordList.get(19));
                break;
        }

        listAllText1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(0).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true); //리스트에서 선택한 것
                    intent.putExtra("currentNumber", 0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if(myDataset.size()>=1) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(1).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if(myDataset.size()>=2) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(2).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true); //리스트에서 선택한 것
                    intent.putExtra("currentNumber", 2);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if(myDataset.size()>=3) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(3).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 3);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=4) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(4).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 4);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=5) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(5).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 5);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=6) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(6).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 6);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(myDataset.size()==20) {
                    // if(myDataset.size()>=7) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(7).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 7);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=8) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(8).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 8);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=9) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(9).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 9);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=10) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(10).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 10);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=11) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(11).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 11);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText13.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=12) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(12).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 12);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText14.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=13) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(13).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 13);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText15.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=14) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(14).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 14);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText16.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=15) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(15).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 15);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText17.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=16) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(16).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 16);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText18.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=17) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(17).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 17);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText19.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=18) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(18).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 18);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
        listAllText20.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=19) {
                if(myDataset.size()==20) {
                    Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                    intent.putExtra("newsURL", myDataset.get(19).getNewsURL());
                    intent.putExtra("mDataset", myDataset);
                    intent.putExtra("allList", true);
                    intent.putExtra("currentNumber", 19);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });
    }
}

