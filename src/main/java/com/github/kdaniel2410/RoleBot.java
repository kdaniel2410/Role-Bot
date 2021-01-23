package com.github.kdaniel2410;

import com.github.kdaniel2410.commands.RoleMeCommand;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

public class RoleBot {
    public static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        if (args.length < 1) {
            logger.error("Please provide a valid token as the first argument!");
            return;
        }

        DiscordApi api = new DiscordApiBuilder()
                .setToken(args[0])
                .setAllIntentsExcept(Intent.GUILD_PRESENCES)
                .login()
                .join();

        logger.info("You can invite me by using the following url: " + api.createBotInvite());

        api.addServerJoinListener(event -> logger.info("Joined server " + event.getServer().getName()));
        api.addServerLeaveListener(event -> logger.info("Left server " + event.getServer().getName()));

        CommandHandler handler = new JavacordHandler(api);
        handler.registerCommand(new RoleMeCommand());
    }
}
