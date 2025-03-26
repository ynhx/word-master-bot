package api.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataServer {

    public String requestWord() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://wordsapiv1.p.rapidapi.com/words/?random=true"))
                .header("x-rapidapi-key", "c1df16a261msh8b46a4befbfcb4bp1ea49djsnb0c464951dcd")
                .header("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            String readWord = jsonObject.get("word").getAsString();

            String[] args = readWord.split(" ");
            String word = args[0];

            return word;
        } catch (IOException | JsonSyntaxException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public String[] querySynonyms() {
        String word = requestWord();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://wordsapiv1.p.rapidapi.com/words/" + word + "/synonyms"))
                .header("x-rapidapi-key", "c1df16a261msh8b46a4befbfcb4bp1ea49djsnb0c464951dcd")
                .header("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            JsonArray synonymsArray = jsonObject.getAsJsonArray("synonyms");

            String[] synonyms = new String[synonymsArray.size()];
            for (int i = 0; i < synonymsArray.size(); i++) {
                synonyms[i] = synonymsArray.get(i).getAsString();
            }
            
            return synonyms;
        } catch (IOException | JsonSyntaxException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
 