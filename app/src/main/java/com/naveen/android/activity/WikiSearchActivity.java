package com.naveen.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.naveen.android.R;
import com.naveen.android.adapters.WikiImageAdapter;
import com.naveen.android.bean.PageDetail;
import com.naveen.android.helper.DividerItemDecoration;
import com.naveen.android.interfaces.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WikiSearchActivity extends BaseActivity implements TextView.OnEditorActionListener {

    private int width;
    private ArrayList<PageDetail> pageDetailList = new ArrayList<>();
    private WikiImageAdapter mWikiAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        findViewByIds();
    }

    private void findViewByIds() {

        AppCompatEditText searchInput = (AppCompatEditText) findViewById(R.id.etInput);
        assert searchInput != null;
        searchInput.setOnEditorActionListener(this);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        mWikiAdapter = new WikiImageAdapter(WikiSearchActivity.this, pageDetailList);
        recyclerView.setAdapter(mWikiAdapter);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!v.getText().toString().isEmpty()) {
                searchItem(v.getText().toString());
                handled = true;
            }
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return handled;
    }

    public void searchItem(String query) {

        showDialog();
        pageDetailList.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://en.wikipedia.org")
                .build();
        NetworkTask task = retrofit.create(NetworkTask.class);
        Call<String> call = task.getDetails(width, query);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dismissDialog();
                if (response.isSuccessful()) {
                    String s = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONObject query = jsonObject.getJSONObject("query");
                        JSONObject page = query.getJSONObject("pages");
                        //getting json keys from pages
                        Iterator keys = page.keys();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            PageDetail pageDetail = new Gson().fromJson(page.getJSONObject(key).toString(), new TypeToken<PageDetail>() {
                            }.getType());
                            pageDetailList.add(pageDetail);
                        }
                        if (pageDetailList.size() > 0) {
                            mWikiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dismissDialog();
                t.printStackTrace();
                Toast.makeText(WikiSearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
