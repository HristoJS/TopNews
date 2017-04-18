package com.evilcorp.topnews.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import com.evilcorp.topnews.model.remote.ArticleResponse;

import java.lang.reflect.Type;

/**
 * Created by hristo.stoyanov on 4/3/2017.
 */

public class ResponseAdapter implements JsonDeserializer<ArticleResponse> {

    @Override
    public ArticleResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
