package wordmaster;

import builders.CommandManager;
import essentials.EssentialsProvider;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class WordMaster {

    public static void main(String[] args) throws InterruptedException, IOException {

        EssentialsProvider provider = new EssentialsProvider();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://wordsapiv1.p.rapidapi.com/words/?letterPattern=eng&limit=100&page=1"))
                .header("x-rapidapi-key", "c1df16a261msh8b46a4befbfcb4bp1ea49djsnb0c464951dcd")
                .header("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println(response.body());
        
        System.out.println("Hello World!");
        
        String token = provider.getBotToken();
        
        JDABuilder builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                .enableIntents(GatewayIntent.DIRECT_MESSAGE_TYPING);

        JDA jda = builder.build();
        jda.awaitReady();
        
        CommandManager.registerCommands(jda);
    }
}
