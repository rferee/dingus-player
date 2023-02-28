package site.Andorvini;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static site.Andorvini.Queue.clearQueue;

public class AloneInChannelHandler {
    public static void startAloneTimer(TextChannel channel, Server server, DiscordApi api) {

        Timer timer = new Timer();

        int leaveSeconds = 60;

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.CYAN)
                .setAuthor("I'm alone :(")
                .addField("", "Leaving in `" + leaveSeconds + "` seconds");

        channel.sendMessage(embed);

        timer.scheduleAtFixedRate(new TimerTask() {
            int i = leaveSeconds;
            @Override
            public void run() {
                if (i == 0) {
                    Player.stopPlaying();
                    clearQueue();
                    server.getConnectedVoiceChannel(api.getYourself()).get().disconnect();
                    timer.cancel();
                }
                i--;
            }
        },0,1000);
    }
}
