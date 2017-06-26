package com.secondtype.realtimesearchword4;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by appaaaa on 2017-02-17.
 */

public class SearchWord implements Serializable{

    private String number;
    private String word;
    //    private String arrow;
//    private String ranking;
    private String newsTitle;
    private String newsTitle2;
    private String newsTitle3;
    private String newsTitle4;
    private String newsImage;
    private String newsURL;
    private String newsURL2;
    private String newsURL3;
    private String newsURL4;
    private ArrayList<Reply> replyArrayList = new ArrayList<Reply>();

    public SearchWord(){
    }

    public SearchWord(String number, String word,  String newsTitle, String newsTitle2, String newsTitle3, String newsTitle4, String newsImage, String newsURL, String newsURL2, String newsURL3, String newsURL4, ArrayList<Reply> replyArrayList){
        this.number = number;
        this.word = word;
//        this.arrow = arrow;
//        this.ranking = ranking;
        this.newsTitle = newsTitle;
        this.newsTitle2 = newsTitle2;
        this.newsTitle3 = newsTitle3;
        this.newsTitle4 = newsTitle4;
        this.newsImage = newsImage;
        this.newsURL = newsURL;
        this.newsURL2 = newsURL2;
        this.newsURL3 = newsURL3;
        this.newsURL4 = newsURL4;
        this.replyArrayList.addAll(replyArrayList);
    }

    public String getNumber() {return number; }
    public String getWord() {return word; }
    //    public String getArrow() {return arrow; }
//    public String getRanking() {return ranking; }
    public String getNewsTitle() {return newsTitle; }
    public String getNewsTitle2() {return newsTitle2; }
    public String getNewsTitle3() {return newsTitle3; }
    public String getNewsTitle4() {return newsTitle4; }
    public String getNewsImage() {return newsImage; }
    public String getNewsURL(){return newsURL;}
    public String getNewsURL2(){return newsURL2;}
    public String getNewsURL3(){return newsURL3;}
    public String getNewsURL4(){return newsURL4;}
    public ArrayList<Reply> getReplyArrayList(){return replyArrayList;}

    public void setNumber(String number){this.number = number;}
    public void setWord(String word){this.word = word;}
    //    public void setArrow(String arrow){this.arrow = arrow;}
//    public void setRanking(String ranking){this.ranking = ranking;}
    public void setNewsTitle(String newsTitle){this.newsTitle = newsTitle;}
    public void setNewsTitle2(String newsTitle2){this.newsTitle2 = newsTitle2;}
    public void setNewsTitle3(String newsTitle3){this.newsTitle3 = newsTitle3;}
    public void setNewsTitle4(String newsTitle4){this.newsTitle4 = newsTitle4;}
    public void setNewsImage(String newsImage){this.newsImage = newsImage;}
    public void setNewsURL(String newsURL){this.newsURL = newsURL;}
    public void setNewsURL2(String newsURL2){this.newsURL2 = newsURL2;}
    public void setNewsURL3(String newsURL3){this.newsURL3 = newsURL3;}
    public void setNewsURL4(String newsURL4){this.newsURL4 = newsURL4;}
    public void setReplyArrayList(ArrayList<Reply> replyArrayList){this.replyArrayList.addAll(replyArrayList);}

//    public SearchWord(Parcel in){
//        this.number = in.readString();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(this.number);
//        parcel.writeString(this.word);
//        parcel.writeString(this.arrow);
//        parcel.writeString(this.ranking);
//        parcel.writeString(this.newsTitle);
//        parcel.writeString(this.newsImage);
//        parcel.writeString(this.newsURL);
//    }
}
