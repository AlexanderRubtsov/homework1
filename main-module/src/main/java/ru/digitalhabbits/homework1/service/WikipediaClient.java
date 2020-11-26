package ru.digitalhabbits.homework1.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Set;

public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);
        HttpGet request = new HttpGet(uri);
        String responseJson = "";

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null){ responseJson = EntityUtils.toString(entity);
            }
            JsonElement jsonElement = JsonParser.parseString(responseJson);
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            JsonElement jsonElement1 = asJsonObject.get("query");
            JsonObject asJsonObject1 = jsonElement1.getAsJsonObject();
            JsonElement jsonElement2 = asJsonObject1.get("pages");
            JsonObject asJsonObject2 = jsonElement2.getAsJsonObject();
            Set<String> stringSet = asJsonObject2.keySet();
            Iterator<String> iterator = stringSet.iterator();
            JsonElement jsonElement3 = asJsonObject2.get(iterator.next());
            JsonObject asJsonObject3 = jsonElement3.getAsJsonObject();
            JsonElement jsonElement4 = asJsonObject3.get("extract");
            String text = jsonElement4.getAsString();
            return text.replaceAll("\\\\n", "\n")/*.toLowerCase()*/;

        } catch (IOException exception){
            return exception.getMessage();
        }
        // TODO: NotImplemented
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
