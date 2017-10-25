package com.secondtype.realtimesearchword4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
import com.hanks.htextview.base.HTextView;
import com.hanks.htextview.typer.TyperTextView;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class MainActivity extends AppCompatActivity {

    public static Context mContext;

    public static Boolean switchs = false;
    public Boolean checkLast = false;

    private FirebaseAnalytics mFirebaseAnalytics;
    static Context context;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SearchWord> myDataset;
    private ArrayList<SearchWord> daumDataset;

    private LinearLayout loadingLayout;
    private TextView loadingTime;
    private HTextView loadingText;

    private TextView titleTime;

    private ArrayList<Reply> replyList;

    public static final String NAVER = "NAVER";
    public static final String DAUM = "DAUM";
    public static final String TAB1 = "TAB1";
    public static final String TAB2 = "TAB2";
    public static final String TAB3 = "TAB3";

    public static final String CLICK_LIST = "CLICK_LIST";
    public static final String CLICK_REPLY = "CLICK_REPLY";
    public static final String CLICK_OTHER_NEWS = "CLICK_OTER_NEWS";
    public static final String CLICK_NEWS = "CLICK_NEWS";
    public static final String CLICK_DAUM = "CLICK_DAUM";
    public static final String VIEW_NAVER_NEWSTOPIC = "VIEW_NAVER_NEWSTOPIC";
    public static final String VIEW_NAVER_ENTERTAINMENT = "VIEW_NAVER_ENTERTAINMENT";
    public static final String VIEW_DAUM_NEWSTOPIC = "VIEW_DAUM_NEWSTOPIC";
    public static final String VIEW_DAUM_ENTERTAINMENT = "VIEW_DAUM_ENTERTAINMENT";


    private String state = NAVER; //detailweb에서 현재가 네이버인지, 다음인지 구분하기 위한 글로벌 변수
    private String state2 = TAB1;

    private boolean isAllList = false;
    private boolean isLast = false;


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
    FloatingActionButton fab;

    Button tabButton1;
    Button tabButton2;
    Button tabButton3;
    ////////////////////////////////

    ////// + searchword 만 따로 받아오기. 리스트 표시용
    ArrayList<String> wordList;
    ////////////////////////////////////////////////////

    public NaverParser NParser;  //네이버 asynctask
    public NaverParser2 NParser2; //네이버 뉴스토픽
    public NaverParser3 NParser3; // 네이버 연애&스포츠

    public DaumParser DParser; // 다음 asynctask
    public DaumParser2 DParser2;
    public DaumParser3 DParser3;

    SwipyRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mContext = this;

        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseMessagingService.Subscribe);
        FirebaseInstanceId.getInstance().getToken();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        context = this;


        loadingLayout = (LinearLayout)findViewById(R.id.linearlayout_loading);
        loadingTime = (TextView)findViewById(R.id.textview_starttime);
        loadingText = (HTextView) findViewById(R.id.textview_loadingtext);
        loadingText.animateText("지금이슈");



        titleTime = (TextView)findViewById(R.id.textview_title);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!isAllList){ //현재 카드보기면 검색어 리스트로 바꿈
                    isAllList = true;
                    mRecyclerView.setVisibility(View.GONE);
                    linearLayoutListAll.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.ic_video_label_white_24px);
                    onSendList();
                    onClickList();
                }else{ //현재 검색어 리스트면 카드보기로 바꿈
                    isAllList = false;
                    mRecyclerView.setVisibility(View.VISIBLE);
                    linearLayoutListAll.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.ic_format_list_numbered_white_24px);
                }
            }
        });


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

        mSwipeRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){

                    mSwipeRefreshLayout.setRefreshing(false);
                } else if(direction == SwipyRefreshLayoutDirection.BOTTOM){
                    if(isLast){
                        if(state2.equals(TAB1)){
                            tab2Event();
                        }else if(state2.equals(TAB2)){
                            tab3Event();
                        }else if(state2.equals(TAB3)){
                            state = DAUM;
                            tab1Event();
                            topListDesign();
                        }
                    }

                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        myDataset = new ArrayList<>();
        mAdapter = new SearchWordAdapter(getApplication(),myDataset, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = mRecyclerView.getAdapter().getItemCount() -1;

                if(checkLast && lastVisibleItemPosition == itemTotalCount){
                    if(state2.equals(TAB1) || state2.equals(TAB2))
                        Toast.makeText(getApplicationContext(), "마지막입니다. 스크롤하시면 다음페이지로 넘어갑니다. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        daumDataset = new ArrayList<>();

        topListDesign();
        naverBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                state = NAVER;
                topListDesign();
                tab1Event();
            }
        });
        daumBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                state = DAUM;
                topListDesign();
                tab1Event();
                onSendDAUM();
                onClickDaum();
            }
        });
        tabButton1 = (Button)findViewById(R.id.button_tab1);
        tabButton1.setTextSize(19);
        tabButton1.setTextColor(Color.parseColor("#00c73c"));

        tabButton2 = (Button)findViewById(R.id.button_tab2);
        tabButton2.setTextSize(16);
        tabButton2.setTextColor(Color.parseColor("#000000"));

        tabButton3 = (Button)findViewById(R.id.button_tab3);
        tabButton3.setTextSize(16);
        tabButton3.setTextColor(Color.parseColor("#000000"));

        tabButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tab1Event();
            }
        });

        tabButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tab2Event();
            }
        });

        tabButton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tab3Event();
            }
        });

        ////// + searchword만 따로 받아오기
        wordList = new ArrayList<>();
        ///////////////////////////////////

        myDataset.clear();
        NParser = new NaverParser();
        NParser.execute( );
    }

    public void topListDesign() {
        if(state.equals(NAVER)) {
            naverBtn = (Button)findViewById(R.id.button_naver);
            naverBtn.setTextSize(18);
            naverBtn.setTextColor(Color.parseColor("#00c73c"));

            daumBtn = (Button)findViewById(R.id.button_daum);
            daumBtn.setTextSize(16);
            daumBtn.setTextColor(Color.parseColor("#000000"));
        } else if(state.equals(DAUM)) {
            naverBtn = (Button) findViewById(R.id.button_naver);
            naverBtn.setTextSize(16);
            naverBtn.setTextColor(Color.parseColor("#000000"));

            daumBtn = (Button) findViewById(R.id.button_daum);
            daumBtn.setTextSize(18);
            daumBtn.setTextColor(Color.parseColor("#f1685e"));
        }
    }

    public void tab1Event(){
        tabButton2.setTextSize(16);
        tabButton2.setTextColor(Color.parseColor("#000000"));
        tabButton3.setTextSize(16);
        tabButton3.setTextColor(Color.parseColor("#000000"));


        mRecyclerView.setVisibility(View.VISIBLE);
        linearLayoutListAll.setVisibility(View.GONE);
        fab.setImageResource(R.drawable.ic_format_list_numbered_white_24px);

        if(NParser != null && NParser.getStatus() == AsyncTask.Status.RUNNING){
            NParser.cancel(true);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(NParser2 != null && NParser2.getStatus() == AsyncTask.Status.RUNNING){
            NParser2.cancel(true);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(NParser3 != null && NParser3.getStatus() == AsyncTask.Status.RUNNING){
            NParser3.cancel(true);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(DParser != null && DParser.getStatus() == AsyncTask.Status.RUNNING){
            DParser.cancel(true);
            Log.v("appaaaa", "DParser cancel in button");
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(DParser2 != null && DParser2.getStatus() == AsyncTask.Status.RUNNING){
            DParser2.cancel(true);
            Log.v("appaaaa", "DParser cancel in button");
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(DParser3 != null && DParser3.getStatus() == AsyncTask.Status.RUNNING){
            DParser3.cancel(true);
            Log.v("appaaaa", "DParser cancel in button");
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(state.equals("NAVER")) {
            tabButton1.setTextSize(19);
            tabButton1.setTextColor(Color.parseColor("#00c73c"));

            myDataset.clear();
            mRecyclerView.setAdapter(mAdapter);
            switchs = false;

            state2 = TAB1;

            NParser = new NaverParser();
            NParser.execute();

        } else if(state.equals("DAUM")) {
            tabButton1.setTextSize(19);
            tabButton1.setTextColor(Color.parseColor("#f1685e"));

            myDataset.clear();
            mRecyclerView.setAdapter(mAdapter);
            switchs = false;

            state2 = TAB1;

            DParser = new DaumParser();
            DParser.execute();
        }
    }

    public void tab2Event(){
        tabButton1.setTextSize(16);
        tabButton1.setTextColor(Color.parseColor("#000000"));
        tabButton3.setTextSize(16);
        tabButton3.setTextColor(Color.parseColor("#000000"));

        mRecyclerView.setVisibility(View.VISIBLE);
        linearLayoutListAll.setVisibility(View.GONE);
        fab.setImageResource(R.drawable.ic_format_list_numbered_white_24px);

        if(NParser != null && NParser.getStatus() == AsyncTask.Status.RUNNING){
            NParser.cancel(true);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(NParser2 != null && NParser2.getStatus() == AsyncTask.Status.RUNNING){
            NParser2.cancel(true);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(NParser3 != null && NParser3.getStatus() == AsyncTask.Status.RUNNING){
            NParser3.cancel(true);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(DParser != null && DParser.getStatus() == AsyncTask.Status.RUNNING){
            DParser.cancel(true);
            Log.v("appaaaa", "DParser cancel in button");
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(DParser2 != null && DParser2.getStatus() == AsyncTask.Status.RUNNING){
            DParser2.cancel(true);
            Log.v("appaaaa", "DParser cancel in button");
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(DParser3 != null && DParser3.getStatus() == AsyncTask.Status.RUNNING){
            DParser3.cancel(true);
            Log.v("appaaaa", "DParser cancel in button");
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(state.equals("NAVER")) {
            tabButton2.setTextSize(19);
            tabButton2.setTextColor(Color.parseColor("#00c73c"));

            myDataset.clear();
            mRecyclerView.setAdapter(mAdapter);
            switchs = false;

            state2 = TAB2;

            NParser2 = new NaverParser2();
            NParser2.execute();

            onClickNaverNewstopic();

        } else if(state.equals("DAUM")) {
            tabButton2.setTextSize(19);
            tabButton2.setTextColor(Color.parseColor("#f1685e"));

            myDataset.clear();
            mRecyclerView.setAdapter(mAdapter);
            switchs = false;

            state2 = TAB2;

            DParser2 = new DaumParser2();
            DParser2.execute();

            onClickDaumNewstopic();
        }
    }

    public void tab3Event(){
        tabButton1.setTextSize(16);
        tabButton1.setTextColor(Color.parseColor("#000000"));
        tabButton2.setTextSize(16);
        tabButton2.setTextColor(Color.parseColor("#000000"));

        mRecyclerView.setVisibility(View.VISIBLE);
        linearLayoutListAll.setVisibility(View.GONE);
        fab.setImageResource(R.drawable.ic_format_list_numbered_white_24px);

        if(NParser != null && NParser.getStatus() == AsyncTask.Status.RUNNING){
            NParser.cancel(true);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(NParser2 != null && NParser2.getStatus() == AsyncTask.Status.RUNNING){
            NParser2.cancel(true);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(NParser3 != null && NParser3.getStatus() == AsyncTask.Status.RUNNING){
            NParser3.cancel(true);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(DParser != null && DParser.getStatus() == AsyncTask.Status.RUNNING){
            DParser.cancel(true);
            Log.v("appaaaa", "DParser cancel in button");
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(DParser2 != null && DParser2.getStatus() == AsyncTask.Status.RUNNING){
            DParser2.cancel(true);
            Log.v("appaaaa", "DParser cancel in button");
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(DParser3 != null && DParser3.getStatus() == AsyncTask.Status.RUNNING){
            DParser3.cancel(true);
            Log.v("appaaaa", "DParser cancel in button");
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(state.equals("NAVER")) {
            tabButton3.setTextSize(19);
            tabButton3.setTextColor(Color.parseColor("#00c73c"));

            myDataset.clear();
            mRecyclerView.setAdapter(mAdapter);
            switchs = false;

            state2 = TAB3;

            NParser3 = new NaverParser3();
            NParser3.execute();

            onClickNaverEntertainment();

        } else if(state.equals("DAUM")) {
            tabButton3.setTextSize(19);
            tabButton3.setTextColor(Color.parseColor("#f1685e"));

            myDataset.clear();
            mRecyclerView.setAdapter(mAdapter);
            switchs = false;

            state2 = TAB3;

            DParser3 = new DaumParser3();
            DParser3.execute();

            onClickDaumEntertainment();
        }
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


                myDataset.clear();
                ////// + list만 빨리 먼저 받아오기
                wordList.clear();
                for(int i = 0; i < 20; i++){
                    if(NParser.isCancelled()){
                        Log.v("appaaaa", "break1 : " + i);
                        return null;
                    }
                    wordList.add(mElements.get(i).select("span.title").text());
                    //Log.v("appaaaa", "반복문1 : " + i);
                }

                for(int i = 0; i < wordList.size(); i++) {
                    if(NParser.isCancelled()){
                        Log.v("appaaaa", "naver break2 : " + i);
                        return null;
                    }
                    Log.v("appaaaa", "naver 반복문2 : " + i);
                    SearchWord searchWord = new SearchWord();
                    searchWord.setNumber(Integer.toString(i + 1));
                    searchWord.setWord(wordList.get(i));

                    String newsUrl = "https://search.naver.com/search.naver?where=news&sm=tab_jum&ie=utf8&query=" + searchWord.getWord();
                    Log.v("testerror", newsUrl);

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
                            if(NParser.isCancelled()){
                                Log.v("appaaaa", "naver break3 : " + j);
                                return null;
                            }
                            //Log.v("naver appaaaa", "반복문3 : " + j);
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
            loadingViewShow();

            listAllText11.setVisibility(View.VISIBLE);
            listAllText12.setVisibility(View.VISIBLE);
            listAllText13.setVisibility(View.VISIBLE);
            listAllText14.setVisibility(View.VISIBLE);
            listAllText15.setVisibility(View.VISIBLE);
            listAllText16.setVisibility(View.VISIBLE);
            listAllText17.setVisibility(View.VISIBLE);
            listAllText18.setVisibility(View.VISIBLE);
            listAllText19.setVisibility(View.VISIBLE);
            listAllText20.setVisibility(View.VISIBLE);

            checkLast=false;
            isLast = false;

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(SearchWord... values) {
            myDataset.add(values[0]);
            mAdapter.notifyItemInserted(position++);

            //    ListAllBinding(Integer.parseInt(values[0].getNumber()));

            if(Integer.parseInt(values[0].getNumber()) == 2){
                loadingLayout.setVisibility(View.GONE);

                for(int i = 0; i < wordList.size(); i++){
                    ListAllBinding(i+1);
                }
            }

        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            switchs = true; //데이터를 다 가져왔으면 클릭 가능
            checkLast = true;
            isLast = true;
            mAdapter.notifyDataSetChanged();
        }
    }
    public class NaverParser2 extends AsyncTask<Void, SearchWord, ArrayList<SearchWord>> {

        String url2 = "http://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1=001";
        Integer position = 0;

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {
            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument_news = Jsoup.connect(url2).get();
                Elements mElements_news = mDocument_news.select("#newstopic_news li");

                myDataset.clear();
                ////// + list만 빨리 먼저 받아오기
                wordList.clear();

                for(int i = 0; i < 10; i++){
                    if(NParser2.isCancelled()){
                        Log.v("appaaaa", "break1 : " + i);
                        return null;
                    }
                    Log.v("testerror", mElements_news.get(i).select("a").attr("title"));
                    wordList.add(mElements_news.get(i).select("a").attr("title"));
                }

                for(int i = 0; i < wordList.size(); i++) {
                    if(NParser2.isCancelled()){
                        Log.v("appaaaa", "naver break2 : " + i);
                        return null;
                    }
                    Log.v("appaaaa", "naver 반복문2 : " + i);
                    SearchWord searchWord = new SearchWord();
                    searchWord.setNumber(Integer.toString(i + 1));
                    searchWord.setWord(wordList.get(i));

                    String newsUrl = "https://search.naver.com/search.naver?where=news&sm=tab_jum&ie=utf8&query=" + searchWord.getWord();
                    Log.v("testerror", newsUrl);

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
                            if(NParser2.isCancelled()){
                                Log.v("appaaaa", "naver break3 : " + j);
                                return null;
                            }
                            //Log.v("naver appaaaa", "반복문3 : " + j);
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
            loadingViewShow();

            listAllText11.setVisibility(View.VISIBLE);
            listAllText12.setVisibility(View.VISIBLE);
            listAllText13.setVisibility(View.VISIBLE);
            listAllText14.setVisibility(View.VISIBLE);
            listAllText15.setVisibility(View.VISIBLE);
            listAllText16.setVisibility(View.VISIBLE);
            listAllText17.setVisibility(View.VISIBLE);
            listAllText18.setVisibility(View.VISIBLE);
            listAllText19.setVisibility(View.VISIBLE);
            listAllText20.setVisibility(View.VISIBLE);

            checkLast=false;
            isLast=false;
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(SearchWord... values) {
            myDataset.add(values[0]);
            mAdapter.notifyItemInserted(position++);

            //    ListAllBinding(Integer.parseInt(values[0].getNumber()));

            if(Integer.parseInt(values[0].getNumber()) == 2){
                loadingLayout.setVisibility(View.GONE);

                for(int i = 0; i < wordList.size(); i++){
                    ListAllBinding(i+1);
                }
            }

        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            switchs = true; //데이터를 다 가져왔으면 클릭 가능
            checkLast = true;
            isLast = true;
            mAdapter.notifyDataSetChanged();
        }
    }
    public class NaverParser3 extends AsyncTask<Void, SearchWord, ArrayList<SearchWord>> {

        String url2 = "http://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1=001";
        Integer position = 0;

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {
            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{

                Document mDocument_news = Jsoup.connect(url2).get();
                Elements mElements_news = mDocument_news.select("#newstopic_entertain li");

                myDataset.clear();
                ////// + list만 빨리 먼저 받아오기
                wordList.clear();

                for(int i = 0; i < 10; i++){
                    if(NParser3.isCancelled()){
                        Log.v("appaaaa", "break1 : " + i);
                        return null;
                    }
                    wordList.add(mElements_news.get(i).select("a").attr("title"));
                }

                for(int i = 0; i < wordList.size(); i++) {
                    if(NParser3.isCancelled()){
                        Log.v("appaaaa", "naver break2 : " + i);
                        return null;
                    }
                    Log.v("appaaaa", "naver 반복문2 : " + i);
                    SearchWord searchWord = new SearchWord();
                    searchWord.setNumber(Integer.toString(i + 1));
                    searchWord.setWord(wordList.get(i));

                    String newsUrl = "https://search.naver.com/search.naver?where=news&sm=tab_jum&ie=utf8&query=" + searchWord.getWord();
                    Log.v("testerror", newsUrl);

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
                            if(NParser3.isCancelled()){
                                Log.v("appaaaa", "naver break3 : " + j);
                                return null;
                            }
                            //Log.v("naver appaaaa", "반복문3 : " + j);
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
            loadingViewShow();

            listAllText11.setVisibility(View.VISIBLE);
            listAllText12.setVisibility(View.VISIBLE);
            listAllText13.setVisibility(View.VISIBLE);
            listAllText14.setVisibility(View.VISIBLE);
            listAllText15.setVisibility(View.VISIBLE);
            listAllText16.setVisibility(View.VISIBLE);
            listAllText17.setVisibility(View.VISIBLE);
            listAllText18.setVisibility(View.VISIBLE);
            listAllText19.setVisibility(View.VISIBLE);
            listAllText20.setVisibility(View.VISIBLE);

            checkLast = false;
            isLast = false;

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(SearchWord... values) {
            myDataset.add(values[0]);
            mAdapter.notifyItemInserted(position++);

            //    ListAllBinding(Integer.parseInt(values[0].getNumber()));

            if(Integer.parseInt(values[0].getNumber()) == 2){
                loadingLayout.setVisibility(View.GONE);

                for(int i = 0; i < wordList.size(); i++){
                    ListAllBinding(i+1);
                }
            }

        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            switchs = true; //데이터를 다 가져왔으면 클릭 가능
            checkLast = true;
            isLast = true;
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

                myDataset.clear();
                wordList.clear();
                for (int i = 0; i < 10; i++) {
                    if(DParser.isCancelled()){
                        Log.v("appaaaa", "daum break1 : " + i);
                        return null;
                    }
                    wordList.add(mElements.get(i).select("a[href]").get(0).text()); //title
                    //Log.v("appaaaa", "daum 반복문1 : " + i);
                }

                for(int i = 0; i < 10; i++){
                    if(DParser.isCancelled()){
                        Log.v("appaaaa", "daum break2 : " + i);
                        return null;
                    }
                    Log.v("appaaaa", "daum 반복문2 : " + i);
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
                            if(DParser.isCancelled()){
                                Log.v("appaaaa", "daum break3 : " + j);
                                return null;
                            }
                            //Log.v("appaaaa", "daum 반복문3 : " + j);
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
            loadingViewShow();

            listAllText11.setVisibility(View.GONE);
            listAllText12.setVisibility(View.GONE);
            listAllText13.setVisibility(View.GONE);
            listAllText14.setVisibility(View.GONE);
            listAllText15.setVisibility(View.GONE);
            listAllText16.setVisibility(View.GONE);
            listAllText17.setVisibility(View.GONE);
            listAllText18.setVisibility(View.GONE);
            listAllText19.setVisibility(View.GONE);
            listAllText20.setVisibility(View.GONE);

            checkLast = false;
            isLast = false;

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(SearchWord... values) {
            myDataset.add(values[0]);
            mAdapter.notifyItemInserted(position++);
            //ListAllBinding(Integer.parseInt(values[0].getNumber()));
            if(loadingLayout.getVisibility() == View.VISIBLE){
                currentTimer();
            }

            if(Integer.parseInt(values[0].getNumber()) == 2){
                loadingLayout.setVisibility(View.GONE);

                for(int i = 0; i < wordList.size(); i++){
                    ListAllBinding(i+1);
                }
            }
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            switchs = true; //데이터를 다 가져왔으면 클릭 가능
            checkLast = true;
            isLast = true;
            mAdapter.notifyDataSetChanged();
        }
    }
    public class DaumParser2 extends AsyncTask<Void, SearchWord, ArrayList<SearchWord>> {

        String url = "http://media.daum.net/entertain/ranking/keyword#none";
        Integer position = 0;

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... params) {
            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
                Elements mElements = mDocument.select("div.aside_search li a.link_txt");

                myDataset.clear();
                wordList.clear();
                for (int i = 10; i < 20; i++) {
                    if(DParser2.isCancelled()){
                        return null;
                    }
                    wordList.add(mElements.get(i).select("a[href]").get(0).text()); //title
                    Log.v("dparser", "daum 반복문1 : " + i);
                    Log.v("dparser", mElements.get(i).select("a[href]").get(0).text());
                }

                for(int i = 0; i < wordList.size(); i++){
                    if(DParser2.isCancelled()){
                        Log.v("appaaaa", "daum break2 : " + i);
                        return null;
                    }
                    Log.v("appaaaa", "daum 반복문2 : " + i);
                    SearchWord searchWord = new SearchWord();
                    searchWord.setNumber(Integer.toString(i + 1));
                    searchWord.setWord(wordList.get(i));

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
                            if(DParser2.isCancelled()){
                                Log.v("appaaaa", "daum break3 : " + j);
                                return null;
                            }
                            //Log.v("appaaaa", "daum 반복문3 : " + j);
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
            loadingViewShow();

            listAllText11.setVisibility(View.GONE);
            listAllText12.setVisibility(View.GONE);
            listAllText13.setVisibility(View.GONE);
            listAllText14.setVisibility(View.GONE);
            listAllText15.setVisibility(View.GONE);
            listAllText16.setVisibility(View.GONE);
            listAllText17.setVisibility(View.GONE);
            listAllText18.setVisibility(View.GONE);
            listAllText19.setVisibility(View.GONE);
            listAllText20.setVisibility(View.GONE);

            checkLast = false;
            isLast = false;

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(SearchWord... values) {
            myDataset.add(values[0]);
            mAdapter.notifyItemInserted(position++);
            //ListAllBinding(Integer.parseInt(values[0].getNumber()));
            if(loadingLayout.getVisibility() == View.VISIBLE){
                currentTimer();
            }

            if(Integer.parseInt(values[0].getNumber()) == 2){
                loadingLayout.setVisibility(View.GONE);

                for(int i = 0; i < wordList.size(); i++){
                    ListAllBinding(i+1);
                }
            }
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            switchs = true; //데이터를 다 가져왔으면 클릭 가능
            checkLast = true;
            isLast = true;
            mAdapter.notifyDataSetChanged();
        }
    }

    public class DaumParser3 extends AsyncTask<Void, SearchWord, ArrayList<SearchWord>> {

        String url = "http://media.daum.net/entertain/ranking/keyword#none";
        Integer position = 0;

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... params) {
            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
                Elements mElements = mDocument.select("div.aside_search li a.link_txt");

                myDataset.clear();
                wordList.clear();
                for (int i = 20; i < 40; i++) {
                    if(DParser3.isCancelled()){
                        return null;
                    }
                    wordList.add(mElements.get(i).select("a[href]").get(0).text()); //title
                    //Log.v("appaaaa", "daum 반복문1 : " + i);
                }

                for(int i = 0; i < wordList.size(); i++){
                    if(DParser3.isCancelled()){
                        Log.v("appaaaa", "daum break2 : " + i);
                        return null;
                    }
                    Log.v("appaaaa", "daum 반복문2 : " + i);
                    SearchWord searchWord = new SearchWord();
                    searchWord.setNumber(Integer.toString(i + 1));
                    searchWord.setWord(wordList.get(i));

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
                            if(DParser3.isCancelled()){
                                Log.v("appaaaa", "daum break3 : " + j);
                                return null;
                            }
                            //Log.v("appaaaa", "daum 반복문3 : " + j);
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
            loadingViewShow();

            listAllText11.setVisibility(View.GONE);
            listAllText12.setVisibility(View.GONE);
            listAllText13.setVisibility(View.GONE);
            listAllText14.setVisibility(View.GONE);
            listAllText15.setVisibility(View.GONE);
            listAllText16.setVisibility(View.GONE);
            listAllText17.setVisibility(View.GONE);
            listAllText18.setVisibility(View.GONE);
            listAllText19.setVisibility(View.GONE);
            listAllText20.setVisibility(View.GONE);

            checkLast = false;
            isLast = false;

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(SearchWord... values) {
            myDataset.add(values[0]);
            mAdapter.notifyItemInserted(position++);
            //ListAllBinding(Integer.parseInt(values[0].getNumber()));
            if(loadingLayout.getVisibility() == View.VISIBLE){
                currentTimer();
            }

            if(Integer.parseInt(values[0].getNumber()) == 2){
                loadingLayout.setVisibility(View.GONE);

                for(int i = 0; i < wordList.size(); i++){
                    ListAllBinding(i+1);
                }
            }
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            switchs = true; //데이터를 다 가져왔으면 클릭 가능
            checkLast = true;
            isLast = true;
            mAdapter.notifyDataSetChanged();
        }
    }



    public void loadingViewShow(){
        String str = "";

        loadingLayout.setVisibility(View.VISIBLE);

        if(state.equals(NAVER)) {
            loadingLayout.setBackgroundResource(R.color.colorNaver);
            str += "네이버 ";
        }else if(state.equals(DAUM)) {
            loadingLayout.setBackgroundResource(R.color.colorDaum);
            str += "다음 ";
        }

        if(state2.equals(TAB1)) {
            str += "실시간 검색어";
        }else if(state2.equals(TAB2)) {
            str += "뉴스토픽";
        }else if(state2.equals(TAB3)) {
            str += "연애&스포츠";
        }

        loadingText.setText(str);
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

        if(isAllList) {
            isAllList = false;
            mRecyclerView.setVisibility(View.VISIBLE);
            linearLayoutListAll.setVisibility(View.GONE);
            fab.setImageResource(R.drawable.ic_format_list_numbered_white_24px);
        }else{
            super.onBackPressed();
        }
    }

    public void ListAllBinding(int num){
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
                if(switchs) {
                    IntentList(0);
                }
            }
        });
        listAllText2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if(myDataset.size()>=1) {
                if(switchs) {
                    IntentList(1);
                }
            }
        });
        listAllText3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if(myDataset.size()>=2) {
                if(switchs) {
                    IntentList(2);
                }
            }
        });
        listAllText4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if(myDataset.size()>=3) {
                if(switchs) {
                    IntentList(3);
                }
            }
        });
        listAllText5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=4) {
                if(switchs) {
                    IntentList(4);
                }
            }
        });
        listAllText6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=5) {
                if(switchs) {
                    IntentList(5);
                }
            }
        });
        listAllText7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=6) {
                if(switchs) {
                    IntentList(6);
                }
            }
        });
        listAllText8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(switchs) {
                    IntentList(7);
                }
            }
        });
        listAllText9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=8) {
                if(switchs) {
                    IntentList(8);
                }
            }
        });
        listAllText10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=9) {
                if(switchs) {
                    IntentList(9);
                }
            }
        });
        listAllText11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=10) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(10);
                }
            }
        });
        listAllText12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=11) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(11);
                }
            }
        });
        listAllText13.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=12) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(12);
                }
            }
        });
        listAllText14.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=13) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(13);
                }
            }
        });
        listAllText15.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=14) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(14);
                }
            }
        });
        listAllText16.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=15) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(15);
                }
            }
        });
        listAllText17.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=16) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(16);
                }
            }
        });
        listAllText18.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // if(myDataset.size()>=17) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(17);
                }
            }
        });
        listAllText19.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=18) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(18);
                }
            }
        });
        listAllText20.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  if(myDataset.size()>=19) {
                if(switchs && state.equals("NAVER")) {
                    IntentList(19);
                }
            }
        });
    }

    public void IntentList(Integer i){
        Intent intent = new Intent(MainActivity.this, DetailWeb.class);
        intent.putExtra("newsURL", myDataset.get(i).getNewsURL());
        intent.putExtra("mDataset", myDataset);
        intent.putExtra("allList", true);
        intent.putExtra("currentNumber", i);
        intent.putExtra("state", state);
        startActivity(intent);
        overridePendingTransition(R.anim.rightin, R.anim.notmove);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(NParser != null && NParser.getStatus() == AsyncTask.Status.RUNNING){
            NParser.cancel(true);
        }
        if(DParser != null && DParser.getStatus() == AsyncTask.Status.RUNNING){
            DParser.cancel(true);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public void onSendList( ){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "List");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void onSendShare( ){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Share");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
    }

    public void onSendReply( ){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Reply");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, bundle);
    }

    public void onSendOtherPost( ){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "others");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.GENERATE_LEAD, bundle);
    }

    public void onSendNEWS( ){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "news");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.PRESENT_OFFER, bundle);
    }

    public void onSendDAUM( ){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "daum");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_UP, bundle);
    }

    public void onClickList(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "click tab naver");
        mFirebaseAnalytics.logEvent(CLICK_LIST, bundle);
    }

    public void onClickReply(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "click tab naver");
        mFirebaseAnalytics.logEvent(CLICK_REPLY, bundle);
    }

    public void onClickOtherNews(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "click tab naver");
        mFirebaseAnalytics.logEvent(CLICK_OTHER_NEWS, bundle);
    }

    public void onClickNews(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "click tab naver");
        mFirebaseAnalytics.logEvent(CLICK_NEWS, bundle);
    }

    public void onClickDaum(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "click tab naver");
        mFirebaseAnalytics.logEvent(CLICK_DAUM, bundle);
    }

    public void onClickNaverNewstopic(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "click tab naver");
        mFirebaseAnalytics.logEvent(VIEW_NAVER_NEWSTOPIC, bundle);
    }

    public void onClickNaverEntertainment(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "click tab naver");
        mFirebaseAnalytics.logEvent(VIEW_NAVER_ENTERTAINMENT, bundle);
    }

    public void onClickDaumNewstopic(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "click tab naver");
        mFirebaseAnalytics.logEvent(VIEW_DAUM_NEWSTOPIC, bundle);
    }

    public void onClickDaumEntertainment(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "click tab naver");
        mFirebaseAnalytics.logEvent(VIEW_DAUM_ENTERTAINMENT, bundle);
    }



}

