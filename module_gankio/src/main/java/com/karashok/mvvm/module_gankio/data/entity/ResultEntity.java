package com.karashok.mvvm.module_gankio.data.entity;

import java.util.List;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ResultEntity
 * @date 2019/03/26 9:43
 **/
public class ResultEntity<T> {

    private boolean error;
    private List<T> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
