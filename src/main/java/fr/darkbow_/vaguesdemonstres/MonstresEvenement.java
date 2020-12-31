package fr.darkbow_.vaguesdemonstres;

import fr.darkbow_.vaguesdemonstres.scoreboard.ScoreboardSign;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonstresEvenement implements Listener {
    private VaguesdeMonstres main;

    public MonstresEvenement(VaguesdeMonstres vaguesdemonstres){this.main = vaguesdemonstres;}

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Random random = new Random();
        main.nextrandomspawn.put(player, main.timer + random.nextInt(240) + 1);

        if(main.VaguesdeMonstres){
            if(!main.EstEnPause && VaguesdeMonstres.task.isCancelled()){
                VaguesdeMonstres.task = new Taches(main.getInstance()).runTaskTimer(main.getInstance(), 20L, 20L);
            }

            if(main.mode.equals("Progressif")){
                if(main.VeutVoirInfos(player)) {
                    ScoreboardSign sb;
                    if(main.getBoards().containsKey(player)){
                        sb = main.getBoards().get(player);
                    } else {
                        sb = new ScoreboardSign(player, "§c§lVagues de Monstres");
                        sb.create();
                    }

                    sb.setLine(0, "§e");
                    sb.setLine(1, ChatColor.GOLD + "Timer : " + ChatColor.WHITE + main.getTimeFormat(main.timer));
                    sb.setLine(2, "§b");

                    int monstres = 0;
                    if(main.getMonstres().containsKey(player)){
                        if(main.getMonstres().get(player).size() > 0){
                            monstres = main.getMonstres().get(player).size();
                        }
                    }

                    String pluriel = "";
                    if(monstres > 1){
                        pluriel = "s";
                    }

                    sb.setLine(3, ChatColor.AQUA + "Horde : " + ChatColor.WHITE + monstres + " " + "Monstre" + pluriel);
                    sb.setLine(4, "§d");

                    int tempsrestant = -1;
                    if(main.timer%main.monstresbasiques >= main.timer%main.monstresvener){
                        tempsrestant = main.timer%main.monstresbasiques;
                    } else {
                        tempsrestant = main.timer%main.monstresvener;
                    }

                    main.restantvener = main.monstresvener - main.timer%main.monstresvener;

                    sb.setLine(5, ChatColor.BLUE + "Basiques : " + ChatColor.WHITE + main.getTimeFormat(main.restantbasique));
                    sb.setLine(6, "§c");
                    sb.setLine(7, ChatColor.DARK_PURPLE + "§lTERRIIIBLE : " + ChatColor.WHITE + main.getTimeFormat(main.restantvener));

                    main.getBoards().put(player, sb);
                }
            }

            if(!main.getSurvivants().contains(player)){
                main.getSurvivants().add(player);
            }

            if(!main.getMonstres().containsKey(player)){
                List<EntityType> entitytype = new ArrayList<>();
                entitytype.add(EntityType.SKELETON);
                main.getMonstres().put(player, entitytype);
            }
        }

        if(!main.VeulentVoirInfosActionBar().containsKey(player)){
            main.VeulentVoirInfosActionBar().put(player, true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(main.getBoards().containsKey(player)){
            main.getBoards().get(player).destroy();
            main.getBoards().remove(player);
        }
    }
}
