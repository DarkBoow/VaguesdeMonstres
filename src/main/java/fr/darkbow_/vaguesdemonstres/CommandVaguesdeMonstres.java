package fr.darkbow_.vaguesdemonstres;

import fr.darkbow_.vaguesdemonstres.scoreboard.ScoreboardSign;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandVaguesdeMonstres implements CommandExecutor {
    private VaguesdeMonstres main;
    
    public CommandVaguesdeMonstres(VaguesdeMonstres vaguesdemonstres){this.main = vaguesdemonstres;}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(args.length == 1){
            if(sender.hasPermission("vaguesdemonstres.admin")){
                if(args[0].equalsIgnoreCase("start")){
                    if(main.VaguesdeMonstres){
                        sender.sendMessage("§cLes vagues de Monstres sont déjà activées.");
                    } else {
                        if(main.mode.equals("Progressif")){
                            main.monstresbasiques = main.monstresbasiquesinitial;
                            main.monstresvener = main.monstresvenerinitial;
                            for(Player pls : Bukkit.getOnlinePlayers()){
                                if(main.VeutVoirInfos(pls) && !main.getBoards().containsKey(pls)) {
                                    ScoreboardSign sb = new ScoreboardSign(pls, "§c§lVagues de Monstres");
                                    sb.create();
                                    main.getBoards().put(pls, sb);
                                }
                            }

                            if(main.VeulentVoirInfos().containsValue(true)){
                                for(Map.Entry<Player, ScoreboardSign> boards : main.getBoards().entrySet()){
                                    boards.getValue().setLine(0, "§e");
                                    boards.getValue().setLine(1, ChatColor.GOLD + "Timer : " + ChatColor.WHITE + "0s");
                                    boards.getValue().setLine(2, "§b");
                                    boards.getValue().setLine(3, ChatColor.AQUA + "Horde : " + ChatColor.WHITE + "0 " + "Monstre");

                                    boards.getValue().setLine(4, "§d");

                                    int tempsrestant = -1;
                                    if(main.timer%main.monstresbasiques >= main.timer%main.monstresvener){
                                        tempsrestant = main.timer%main.monstresbasiques;
                                    } else {
                                        tempsrestant = main.timer%main.monstresvener;
                                    }

                                    main.restantvener = main.monstresvener - main.timer%main.monstresvener;

                                    main.nextmonstresvener = main.timer + (main.monstresvener - main.timer%main.monstresvener);

                                    boards.getValue().setLine(5, ChatColor.BLUE + "Basiques : " + ChatColor.WHITE + main.getTimeFormat(main.restantbasique));
                                    boards.getValue().setLine(6, "§c");
                                    boards.getValue().setLine(7, ChatColor.DARK_PURPLE + "§lTERRIIIBLE : " + ChatColor.WHITE + main.getTimeFormat(main.restantvener));
                                }
                            }
                        }

                        main.VaguesdeMonstres = true;
                        main.EstEnPause = false;
                        VaguesdeMonstres.task = new Taches(main.getInstance()).runTaskTimer(main.getInstance(), 20L, 20L);
                        Bukkit.broadcastMessage("§cVagues de Monstres §4§lActivées §c!");
                    }
                }

                if(args[0].equalsIgnoreCase("play") || args[0].equalsIgnoreCase("resume")){
                    if(main.mode.equals("Progressif")){
                        if(main.VaguesdeMonstres){
                            main.EstEnPause = false;
                            VaguesdeMonstres.task = new Taches(main.getInstance()).runTaskTimer(main.getInstance(), 20L, 20L);
                            sender.sendMessage("§cVagues de Monstres Réactivées.");
                        } else {
                            sender.sendMessage("§cLes vagues de Monstres ne sont pas activées.");
                        }
                    }

                    if(main.mode.equals("Random")){
                        sender.sendMessage("§cTu peux pas mettre en pause les Vagues, donc cette commande sert à rien mon pote !");
                    }
                }

                if(args[0].equalsIgnoreCase("pause")){
                    if(main.VaguesdeMonstres){
                        if(main.mode.equals("Progressif")){
                            if(main.EstEnPause){
                                sender.sendMessage("§bT'as trouvé le trick, §6§lGG");
                            } else {
                                sender.sendMessage("§bTu crois vraiment que tu peux faire pause aussi facilement ?? §6§lLOL");
                            }
                        }

                        if(main.mode.equals("Random")){
                            if(main.EstEnPause){
                                sender.sendMessage("§bT'as trouvé le trick, §6§lGG");
                            } else {
                                sender.sendMessage("§bAh, mais j'suis désolé, tu Peux PAS me mettre en pause mon gars !");
                            }
                        }
                    } else {
                        sender.sendMessage("§cLes vagues de Monstres ne sont pas activées.");
                    }
                }

                if(args[0].equalsIgnoreCase("esuap")){
                    if(main.mode.equals("Progressif")){
                        if(main.VaguesdeMonstres){
                            if(main.EstEnPause){
                                sender.sendMessage("§bLes Vagues de Monstres sont déjà en Pause !");
                            } else {
                                main.EstEnPause = true;
                                VaguesdeMonstres.task.cancel();
                                Bukkit.broadcastMessage("§bVagues de Monstres mises en pause.");
                            }
                        } else {
                            sender.sendMessage("§cLes vagues de Monstres ne sont pas activées.");
                        }
                    }
                }

                if(args[0].equalsIgnoreCase("stop")){
                    if(main.mode.equals("Progressif")){
                        if(main.VaguesdeMonstres){
                            main.VaguesdeMonstres = false;
                            main.EstEnPause = false;
                            VaguesdeMonstres.task.cancel();
                            main.monstresbasiques = main.monstresbasiquesinitial;
                            main.monstresvener = main.monstresvenerinitial;
                            if(!main.getSurvivants().isEmpty()){
                                main.getSurvivants().clear();
                                main.getMonstres().clear();
                            }

                            if(!main.getBoards().isEmpty()){
                                for(Player pls : main.getBoards().keySet()){
                                    main.getBoards().get(pls).destroy();
                                    main.getBoards().remove(pls);
                                }
                            }

                            main.randomvener = 100;

                            main.timer = 0;

                            Bukkit.broadcastMessage("§cVagues de Monstres §a§lDésactivées §c!");
                        } else {
                            sender.sendMessage("§cLes Vagues de Monstres ne sont pas Activées.");
                        }
                    }

                    if(main.mode.equals("Random")){
                        sender.sendMessage("Cette commande n'existe pas.");
                    }
                }
            }

            if(args[0].equalsIgnoreCase("tab") || args[0].equalsIgnoreCase("scoreboard")){
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    main.VeulentVoirInfos().put(player, !main.VeutVoirInfos(player));
                    player.sendMessage(ChatColor.BLUE + "Scoreboard Personnel " + main.bool(main.VeutVoirInfos(player), 0));

                    if(main.VeutVoirInfos(player)){
                        ScoreboardSign sb = new ScoreboardSign(player, "§c§lVagues de Monstres");
                        sb.create();
                        main.getBoards().put(player, sb);
                    } else {
                        if(main.getBoards().containsKey(player)){
                            main.getBoards().get(player).destroy();
                            main.getBoards().remove(player);
                        }
                    }
                } else {
                    sender.sendMessage("§cSeuls les Joueurs peuvent exécuter cette commande.");
                }
            }

            if(args[0].equalsIgnoreCase("actionbar")){
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    if(main.VeulentVoirInfosActionBar().containsKey(player)){
                        main.VeulentVoirInfosActionBar().put(player, !main.VeulentVoirInfosActionBar().get(player));
                    } else {
                        main.VeulentVoirInfosActionBar().put(player, false);
                    }
                    player.sendMessage(ChatColor.BLUE + "Informations de la Barre d'Actions " + main.bool(main.VeulentVoirInfosActionBar().get(player), 1));
                } else {
                    sender.sendMessage("§cSeuls les Joueurs peuvent exécuter cette commande.");
                }
            }
        }

        if(args.length == 3 && main.mode.equals("Progressif")){
            if(args[0].equalsIgnoreCase("set")){
                if(args[1].equalsIgnoreCase("horde")){
                    if(StringUtils.isNumeric(args[2])){
                        int delaihorde = Integer.parseInt(args[2]);
                        main.monstresbasiques = delaihorde;
                        main.monstresbasiquesinitial = delaihorde;
                        main.restantbasique = main.monstresbasiques - main.timer%main.monstresbasiques;
                        Bukkit.broadcastMessage("§4[VDM] §3Délai d'apparition de la §cHorde Monstrueuse §3ajusté à §d§l" + main.getTimeFormat(main.monstresbasiques) + " §3!!");
                    }
                }

                if(args[1].equalsIgnoreCase("terrifiant")){
                    if(StringUtils.isNumeric(args[2])){
                        int delaiterrifiant = Integer.parseInt(args[2]);
                        main.monstresvener = delaiterrifiant;
                        main.monstresvenerinitial = delaiterrifiant;
                        main.restantvener = main.monstresvener - main.timer%main.monstresvener;
                        Bukkit.broadcastMessage("§4[VDM] §3Délai d'apparition du §5Monstre Terrifiant §3ajusté à §d§l" + main.getTimeFormat(main.monstresvener) + " §3!!");
                    }
                }
            }
        }

        return false;
    }
}