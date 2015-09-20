package com.tweekgames.sneakysnake.connections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.tweekgames.sneakysnake.game.Assets;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvansDesktop on 4/3/2015.
 */
public class LoadScoresConnector {
    String content;
    Json json;
    Net.HttpRequest httpGet;

    public LoadScoresConnector() {
        json = new Json();
        httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
        httpGet.setUrl("http://www.tweekgames.com/loadscores.php");
        httpGet.setHeader("Content-Type", "applications/json");
        httpGet.setContent(content);

    }

    public void loadScores(final List<Assets.UserScore> alltime,
                           final List<Assets.UserScore> monthly, final List<Assets.UserScore> daily) {
        Gdx.net.sendHttpRequest(httpGet, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                // Get the content
                content = httpResponse.getResultAsString();

                // Try to store the information into the high score arrays
                try {
                    // Make the string a json object
                    ScoresJson scoresJson = json.fromJson(ScoresJson.class, content);

                    // Put the values into the asset arrays
                    for(int i = 0; i < scoresJson.alltime.size(); i++) {
                        alltime.add(json.fromJson(Assets.UserScore.class,scoresJson.alltime.get(i).toString()));
                    }
                    for(int i = 0; i < scoresJson.monthly.size(); i++) {
                        monthly.add(json.fromJson(Assets.UserScore.class,scoresJson.monthly.get(i).toString()));
                    }
                    for(int i = 0; i < scoresJson.daily.size(); i++) {
                        daily.add(json.fromJson(Assets.UserScore.class,scoresJson.daily.get(i).toString()));
                    }
                } catch (Exception e){
                    Gdx.app.log("Exception ", e.toString());
                }
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("Status: ", "Failed.");
            }

            @Override
            public void cancelled() {
                Gdx.app.log("Status: ", "Cancelled");
            }
        });

    }

    public static class ScoresJson{
        public ArrayList alltime;
        public List monthly;
        public List daily;
        public int success;
    }
}
