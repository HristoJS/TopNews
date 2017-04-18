package com.evilcorp.topnews.model.remote;

import com.evilcorp.topnews.model.local.Source;

import java.util.List;

/**
 * Created by hristo.stoyanov on 4/3/2017.
 */

public class SourceResponse extends BaseResponse {
    private List<Source> sources;

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
