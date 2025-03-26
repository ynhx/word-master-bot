package commands;

import handlers.ChallengeTimeoutHandler;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class PlayCommand extends ListenerAdapter {

    private final ChallengeTimeoutHandler timeoutHandler;

    public PlayCommand(ChallengeTimeoutHandler timeoutHandler) {
        this.timeoutHandler = timeoutHandler;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String gameName;

        if (event.getName().equals("play")) {
            gameName = event.getOption("mode").getAsString();

            OptionMapping option = event.getOption("opponent");

            if (option != null) {
                User opponent = option.getAsUser();
                EmbedBuilder embedBuilder = new EmbedBuilder();
                String opponentId = opponent.getId();
                String challengerId = event.getUser().getId();

                if (gameName.equals("synonyms")) {
                    embedBuilder.setTitle("Synonyms Game Challenge");
                    embedBuilder.setDescription(event.getUser().getAsMention() + " has challenged you into a synonyms game.");
                    embedBuilder.setColor(Color.yellow);

                    Button acceptButton = Button.success("accept_challenge:" + challengerId + ":" + opponentId, "Accept");
                    Button declineButton = Button.danger("decline_challenge:" + challengerId + ":" + opponentId, "Decline");
                    Button aboutButton = Button.secondary("about_challenge", "How it works");

                    event.reply("Challenge for " + opponent.getAsMention()).addEmbeds(embedBuilder.build())
                            .addActionRow(acceptButton, declineButton, aboutButton).queue(hook -> {
                        hook.retrieveOriginal().queue(message -> {
                            timeoutHandler.handleTimeout(message, opponent);
                        });
                    });
                }

            } else {
                // start the game alone
            }
        }
    }
}
