package com.dgrissom.djbukkit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.NoteBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DJBukkit extends JavaPlugin {
    private static List<Song> loadedSongs = new ArrayList<>();
    private static DJBukkit instance = null;

    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists())
            if (!getDataFolder().mkdir())
                getLogger().warning("Could not create data folder!");
        loadSongs();
    }

    public static DJBukkit getInstance() {
        return instance;
    }

    private static void loadSongs() {
        loadedSongs = new ArrayList<>();
        File[] dataFolderFiles = getInstance().getDataFolder().listFiles();

        if (dataFolderFiles == null) // only happens if size is 0 on some JVM's
            return;

        for (File file : dataFolderFiles) {
            if (file.getName().toLowerCase().endsWith(".djb")) { // this file is a DJB file
                try {
                    loadedSongs.add(new SongParser(file).parse());
                } catch (IOException e) {
                    getInstance().getLogger().severe("Unable to load song file " + file.getName() + "!");
                }
            }
        }
    }

    public static Song getSong(String songName) {
        for (Song song : loadedSongs)
            if (song.getName().equalsIgnoreCase(songName))
                return song;
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getLabel().equalsIgnoreCase("play")) {
            if (!(sender instanceof org.bukkit.entity.Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use that cmd!");
                return true;
            }

            org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;
            if (args.length == 0)
                return false;

            Block block = player.getTargetBlock((Set<Material>) null, 5);
            if (!(block.getState() instanceof NoteBlock)) {
                player.sendMessage(ChatColor.RED + "You must be looking at a note block!");
                return true;
            }

            NoteBlock noteBlock = (NoteBlock) block.getState();

            String songName = StringUtils.join(args, " ", 0);
            Song song = getSong(songName);
            if (song == null) {
                player.sendMessage(ChatColor.RED + "Unknown song (" + args[0] + ")!");
                return true;
            }

            Player.playSong(song, noteBlock);
            return true;
        }

        else if (cmd.getLabel().equalsIgnoreCase("djbukkit")) {
            if (args.length != 1)
                return false;
            if (args[0].equalsIgnoreCase("reload")) {
                loadSongs();
                sender.sendMessage(ChatColor.GREEN + "Reloaded the songs.");
                return true;
            }
        }


        else if (cmd.getLabel().equalsIgnoreCase("songs")) {
            sender.sendMessage(StringUtils.format("&7========== &fAvailable Songs &7=========="));
            for (Song song : loadedSongs)
                sender.sendMessage(song.getName());
            if (loadedSongs.size() == 0)
                sender.sendMessage("No available songs! If you are the owner, make sure you put the song files in the plugins/DJBukkit folder.");
            return true;
        }

        return false;
    }
}
