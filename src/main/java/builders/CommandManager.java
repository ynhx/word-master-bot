package builders;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandManager {

    public static void registerCommands(JDA jda) {

        jda.updateCommands().addCommands(
                Commands.slash("play", "Play a word game")
                        .addOptions(
                                new OptionData(OptionType.STRING, "mode", "Select a game mode", true)
                                        .addChoice("Synonyms", "synonyms")
                                        .addChoice("Word Scramble", "word_scramble")
                                        .addChoice("Word Guess", "word_guess")
                                        .addChoice("Hangman", "hangman")
                                        .addChoice("Word Craft", "word_craft")
                        ).addOption(OptionType.USER, "opponent", "Who are you playing against?", false),
                Commands.slash("help", "Get help")
        ).queue();
    }
}
