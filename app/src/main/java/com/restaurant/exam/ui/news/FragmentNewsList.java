package com.restaurant.exam.ui.news;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.restaurant.exam.data.model.News;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import restaurant.exam.R;

public class FragmentNewsList extends Fragment implements NewsAdapter.ItemListener{
private RecyclerView recyclerViewNews;
    NewsAdapter adapter;
@Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
        }

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewNews = view.findViewById(R.id.recycleViewNews);

        adapter = new NewsAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewNews.setLayoutManager(manager);
        recyclerViewNews.setAdapter(adapter);

        adapter.setClickListener(this);

//        new ReadRSS().execute("https://vnexpress.net/rss/khoa-hoc.rss");
        new ReadRSS().execute("https://thanhnien.vn/rss/thoi-su/quoc-phong-64.rss");
        }

@Override
public void onItemClick(View view, int position) {
//        Toast.makeText(getContext(), adapter.getItem(position).getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), NewsActivity.class);
        intent.putExtra("oneNews", adapter.getItem(position));
        startActivity(intent);
        }

private class ReadRSS extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = "";
            while((line=bufferedReader.readLine())!=null){
                content.append(line);
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        XMLDOMParser parser = new XMLDOMParser();
        Document document = parser.getDocument(s);
        NodeList nodeList = document.getElementsByTagName("item");
        for(int i=0; i<nodeList.getLength(); i++){
            Element element = (Element) nodeList.item(i);
            String a = parser.getValue(element,"image");
            NodeList title = element.getElementsByTagName("title");
            Element line = (Element) title.item(0);
            adapter.add(new News(0,0,a,
                    getCharacterDataFromElement(line),
                    parser.getValue(element, "link")));
        }
    }
    public String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return null;
    }
}
}
