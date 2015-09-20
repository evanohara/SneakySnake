package com.tweekgames.sneakysnake.connections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.net.HttpParametersUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by EvansDesktop on 3/30/2015.
 */
public class HighScoreConnector {
    String status;
    Map parameters;
//    Json jsonObject;
//    ScoreJSONObj scoreJSONObj;

//    public class ScoreJSONObj {
//        private String name;
//        private int score;
//
//        public ScoreJSONObj(String name, int score){
//            this.name = name;
//            this.score = score;
//        }
//    }

    public HighScoreConnector(String name, long score){
        parameters = new HashMap();
        parameters.put("name",name);
        parameters.put("score",Long.toString(score));

//        scoreJSONObj = new ScoreJSONObj(name,score);
//        jsonObject = new Json();
//        jsonObject.setOutputType(JsonWriter.OutputType.json);
//        status = "";

        HttpRequest httpPost = new HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl("http://www.tweekgames.com/highscore.php");
//        httpPost.setHeader("Content-Type", "application/json");
//        httpPost.setContent(jsonObject.toJson(scoreJSONObj));
        httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
        Gdx.net.sendHttpRequest(httpPost,new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                status = httpResponse.getResultAsString();
                Gdx.app.log("Status: ", status);
            }

            @Override
            public void failed(Throwable t) {
                status = "failed";
                Gdx.app.log("Status: ", status);
            }

            @Override
            public void cancelled() {
                Gdx.app.log("Status: ", "Cancelled");
            }
        });

    }
}
