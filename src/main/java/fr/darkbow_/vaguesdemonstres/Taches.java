package fr.darkbow_.vaguesdemonstres;

import fr.darkbow_.vaguesdemonstres.scoreboard.ScoreboardSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Taches extends BukkitRunnable {

    private VaguesdeMonstres main;
    public Taches(VaguesdeMonstres vaguesdemonstres) {this.main = vaguesdemonstres;}

    @Override
    public void run() {
        if(main.VaguesdeMonstres && !main.EstEnPause){
            if(Bukkit.getOnlinePlayers().size() > 0){
                Random r = new Random();
                //Toutes les 3 minutes y a un zombie, squelette, creeper, enderman ou araignée en plus

                if(main.timer > 0){
                    if(main.timer%main.monstresbasiques == 0){
                        boolean lightning_creeper;

                        if(Bukkit.getOnlinePlayers().size() > 0){
                            for(Player pls : Bukkit.getOnlinePlayers()){
                                if(!main.getSurvivants().contains(pls)){
                                    main.getSurvivants().add(pls);
                                    List<EntityType> entitieslist = new ArrayList<>();
                                    main.getMonstres().put(pls, entitieslist);
                                }

                                if(main.getSurvivants().contains(pls)){
                                    if(main.monstresbasiques > 5 && main.monstresvener > 10){
                                        int entite = 7;
                                        entite = r.nextInt(6);

                                        EntityType etype = null;

                                        switch (entite){
                                            case 0:
                                                etype = EntityType.ZOMBIE;
                                                break;
                                            case 1:
                                                etype = EntityType.SKELETON;
                                                break;
                                            case 2:
                                                etype = EntityType.SPIDER;
                                                break;
                                            case 3:
                                                etype = EntityType.CAVE_SPIDER;
                                                break;
                                            case 4:
                                                etype = EntityType.ENDERMAN;
                                                break;
                                            case 5:
                                                etype = EntityType.CREEPER;
                                                break;
                                        }

                                        main.getMonstres().get(pls).add(etype);


                                    }

                                    if(!main.getMonstres().get(pls).isEmpty()){
                                        for(EntityType entitytype : main.getMonstres().get(pls)){
                                            Entity entity = VaguesdeMonstres.spawnEntity(entitytype, pls.getLocation(), 5, 3);
                                            if(entitytype == EntityType.CREEPER){
                                                lightning_creeper = r.nextBoolean();
                                                if(lightning_creeper){
                                                    ((Creeper) entity).setPowered(true);
                                                }
                                            }
                                        }
                                    }
                                }

                                int monstres = 0;
                                if(main.getMonstres().containsKey(pls)){
                                    if(main.getMonstres().get(pls).size() > 0){
                                        monstres = main.getMonstres().get(pls).size();
                                    }
                                }

                                pls.sendMessage("§cSpawn de " + monstres + " Monstres");
                                pls.playSound(pls.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1f, 1f);
                            }
                        }
                    }

                    if(main.timer%main.monstresvener == 0){
                        for(Player pls : Bukkit.getOnlinePlayers()){
                            if(!main.getSurvivants().contains(pls)){
                                main.getSurvivants().add(pls);
                                List<EntityType> entitieslist = new ArrayList<>();
                                main.getMonstres().put(pls, entitieslist);
                            }

                            int entite = r.nextInt(main.randomvener);

                            EntityType etype = null;

                            switch (entite){
                                case 0: case 1:
                                    etype = EntityType.ZOMBIE_VILLAGER;
                                    break;
                                case 2: case 3:
                                    etype = EntityType.DROWNED;
                                    break;
                                case 4: case 5:
                                    etype = EntityType.ZOMBIFIED_PIGLIN;
                                    break;
                                case 6: case 7:
                                    etype = EntityType.BLAZE;
                                    break;
                                case 8: case 9:
                                    etype = EntityType.EVOKER;
                                    break;
                                case 10: case 11:
                                    etype = EntityType.ILLUSIONER;
                                    break;
                                case 12: case 13:
                                    etype = EntityType.PHANTOM;
                                    break;
                                case 14: case 15:
                                    etype = EntityType.PILLAGER;
                                    break;
                                case 16: case 17:
                                    etype = EntityType.ENDERMITE;
                                    break;
                                case 18: case 19:
                                    etype = EntityType.HOGLIN;
                                    break;
                                case 20: case 21:
                                    etype = EntityType.MAGMA_CUBE;
                                    break;
                                case 22: case 23:
                                    etype = EntityType.PIGLIN;
                                    break;
                                case 24: case 25:
                                    etype = EntityType.STRAY;
                                    break;
                                case 26: case 27:
                                    etype = EntityType.SLIME;
                                    break;
                                case 28: case 29:
                                    etype = EntityType.VEX;
                                    break;
                                case 30: case 31:
                                    etype = EntityType.VINDICATOR;
                                    break;
                                case 32: case 33:
                                    etype = EntityType.WITCH;
                                    break;
                                case 34:
                                    etype = EntityType.ZOGLIN;
                                    break;
                                case 35: case 36:
                                    etype = EntityType.WITHER_SKELETON;
                                    break;
                                case 37:
                                    etype = EntityType.RAVAGER;
                                    break;
                                case 38:
                                    int witherchance = r.nextInt(4);
                                    if(witherchance == 1){
                                        etype = EntityType.WITHER;
                                    }
                                    break;
                            }

                            if(main.randomvener > 39){
                                if(main.randomvener >= 49){
                                    main.randomvener = main.randomvener - 10;
                                } else {
                                    main.randomvener = 39;
                                }

                                double spawnpurcent = (double) main.randomvener;
                                spawnpurcent = (39/spawnpurcent)*100;

                                pls.sendMessage("§5Chance de Spawn du Montre Terrifiant descendue à §d§l" + Math.round(spawnpurcent) + "%§5...");
                            }

                            if(etype == null){
                                pls.sendMessage("§dAucun Monstre Terrifiant n'est apparu, §lc'est Dommage...");
                            } else {
                                Entity entity = VaguesdeMonstres.spawnEntity(etype, pls.getLocation(), 5, 3);
                                if(main.timer >= 1800){
                                    main.getMonstres().get(pls).add(etype);
                                    pls.sendMessage("§5Spawn du Monstre Terrifiant\n§6§l+1 à la §lHorde de Monstres\n§bT'as pas Fini le jeu assez vite, §6§lCHEH !!");
                                } else {
                                    pls.sendMessage("§5Spawn du Monstre Terrifiant");
                                }
                            }

                            pls.playSound(pls.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1f, 1f);
                        }

                        main.nextmonstresvener = main.timer + (main.monstresvener - main.timer%main.monstresvener);
                    }
                }

                //Toutes les 20 minutes y a 50% de chances d'avoir un ravageur ou un autre monstre super pété



                //Règles (choisir 4 règles au hasard (selon leur niveau de difficulté)
                //1 : 4 zombies toutes les 5 minutes
                //2 : 3 creepers toutes les 10 minutes
                //3 : 2 monstres random toutes les 5 minutes
                //4 : 4 monstres random au début mais pareil après toutes les 30 minutes
                //5 : 1 Boss toutes les heures
                //6 : 1 Enderman invisible toutes les 20 minutes
                //7 : 1 loup toutes les 30 minutes
                //8 : 1 Blaze toutes les 20 minutes

                //Les règles changent toutes les heures vers le temps/2 des autres règles
                //Exemple : 4 zombies toutes les 5 minutes = 4 zombies toutes les 2min30

                for(Player pls : Bukkit.getOnlinePlayers()){
                    int monstres = 0;

                    main.restantbasique = main.monstresbasiques - main.timer%main.monstresbasiques;
                    main.restantvener = main.monstresvener - main.timer%main.monstresvener;

                    if(main.getMonstres().containsKey(pls)){
                        if(main.getMonstres().get(pls).size() > 0){
                            monstres = main.getMonstres().get(pls).size();
                        }
                    }

                    String pluriel = "";

                    if(main.getBoards().containsKey(pls)){
                        main.getBoards().get(pls).setLine(0, "§e");
                        main.getBoards().get(pls).setLine(1, "§6Timer : §r" + main.getTimeFormat(main.timer));
                        main.getBoards().get(pls).setLine(2, "§b");

                        if(monstres > 1){
                            pluriel = "s";
                        }

                        main.getBoards().get(pls).setLine(3, ChatColor.AQUA + "Horde : " + ChatColor.WHITE + monstres + " " + "Monstre" + pluriel);
                        main.getBoards().get(pls).setLine(4, "§d");

                        int tempsrestant = -1;

                        if(main.restantbasique <= main.restantvener){
                            tempsrestant = main.restantbasique;
                        } else {
                            tempsrestant = main.restantvener;
                        }

                        main.getBoards().get(pls).setLine(5, ChatColor.BLUE + "Basiques : " + ChatColor.WHITE + main.getTimeFormat(main.restantbasique));
                        main.getBoards().get(pls).setLine(6, "§c");
                        main.getBoards().get(pls).setLine(7, ChatColor.DARK_PURPLE + "§lTERRIIIBLE : " + ChatColor.WHITE + main.getTimeFormat(main.restantvener));
                    }

                    if(main.VeulentVoirInfosActionBar().get(pls)){
                        monstres = monstres + 1;
                        if(monstres > 1){
                            pluriel = "s";
                        }

                        double spawnpurcent = (double) main.randomvener;
                        spawnpurcent = (38/spawnpurcent)*100;

                        main.title.sendActionBar(pls, "§4§l" + monstres + " Monstre" + pluriel + " : §c" + main.getTimeFormat(main.restantbasique) + " §2| §5§lTERRIIIBLE : §b" + main.getTimeFormat(main.restantvener) + " (§3" + Math.round(spawnpurcent) + "%§b)");
                    }
                }

                main.timer++;
            }
        } else {
            cancel();
        }
    }
}