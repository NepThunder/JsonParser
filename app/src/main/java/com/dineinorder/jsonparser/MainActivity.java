package com.dineinorder.jsonparser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
    }

    public void parsexml(View v) {
        try {
            InputStream is = getAssets().open("city.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is);
            StringBuilder stringBuilder = new StringBuilder("XML DATA\n-------------");

            NodeList nodeList = document.getElementsByTagName("place");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                stringBuilder.append("\n Name:").append(getValue("name", element));
                stringBuilder.append("\n Latitude:").append(getValue("lat", element));
                stringBuilder.append("\n Longitude:").append(getValue("long", element));
                stringBuilder.append("\n Temperature:").append(getValue("temperature", element));
                stringBuilder.append("\n Humidity:").append(getValue("humidity", element));
                stringBuilder.append("\n----------");
            }

            display.setText(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in reading XML FILE", Toast.LENGTH_LONG).show();
        }
    }

    public void parsejson(View v) {
        try {
            InputStream is = getAssets().open("city.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
            StringBuilder stringBuilder = new StringBuilder("JSON DATA\n--------");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                stringBuilder.append("\n Name:").append(jsonObject.getString("name"));
                stringBuilder.append("\n Latitude:").append(jsonObject.getString("lat"));
                stringBuilder.append("\n Longitude:").append(jsonObject.getString("long"));
                stringBuilder.append("\n Temperature:").append(jsonObject.getString("temperature"));
                stringBuilder.append("\n Humidity:").append(jsonObject.getString("humidity"));
                stringBuilder.append("\n----------");
            }

            display.setText(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in reading JSON file", Toast.LENGTH_LONG).show();
        }
    }

    private String getValue(String tag, Element element) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }
}
