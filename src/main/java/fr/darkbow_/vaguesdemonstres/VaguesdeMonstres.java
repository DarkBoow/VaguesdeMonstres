package fr.darkbow_.vaguesdemonstres;

import fr.darkbow_.vaguesdemonstres.scoreboard.ScoreboardSign;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class VaguesdeMonstres extends JavaPlugin {
    public static BukkitTask task;
    private VaguesdeMonstres instance;
    public Titles title = new Titles();
    public int timer = 0;
    private List<Player> survivants;
    private HashMap<Player, List<EntityType>> monstres;
    private HashMap<Player, Boolean> veulentvoirinfos;
    private HashMap<Player, Boolean> voirinfosactionbar;
    private HashSet<Material> badblocks;
    public int monstresbasiques = 120; //5 minutes = 300
    public int monstresbasiquesinitial = 120;
    public int monstresvener = 600; //20 minutes = 1200
    public int monstresvenerinitial = 600;
    public int nextmonstresvener = 600;
    public int randomvener = 100;
    public int restantbasique = -1;
    public int restantvener = -1;
    public boolean VaguesdeMonstres = false;
    public boolean EstEnPause = false;
    public HashMap<Player, Integer> nextrandomspawn;
    public String mode = "Random"; //Progessif existe aussi

    private Map<Player, ScoreboardSign> boards;

    public VaguesdeMonstres getInstance() {
        return this.instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        if(Objects.requireNonNull(getConfig().getString("mode")).equalsIgnoreCase("Progressif")){
            mode = "Progressif";
        }

        this.boards = new HashMap<>();

        this.survivants = new ArrayList<>();
        this.monstres = new HashMap<>();
        this.veulentvoirinfos = new HashMap<>();
        this.voirinfosactionbar = new HashMap<>();
        this.nextrandomspawn = new HashMap<>();

        Random r = new Random();
        this.nextmonstresvener = this.monstresvener;
        this.randomvener = 100;
        this.restantbasique = this.monstresbasiques;
        this.restantvener = this.monstresvener;

        this.badblocks = new HashSet<>();
        badblocks.add(Material.LAVA);
        badblocks.add(Material.FIRE);

        Objects.requireNonNull(getCommand("vaguesdemonstres")).setExecutor(new CommandVaguesdeMonstres(this));
        getServer().getPluginManager().registerEvents(new MonstresEvenement(this), this);

        System.out.println("[VaguesdeMonstres] Plugin Activé !!");
    }

    @Override
    public void onDisable() {
        System.out.println("[VaguesdeMonstres] Plugin Désactivé !");
    }

    public List<Player> getSurvivants() {
        return this.survivants;
    }

    public HashMap<Player, List<EntityType>> getMonstres() {
        return monstres;
    }

    private static final Random RANDOM = new Random();

    public static <T extends Entity> T spawnEntity(EntityType entityType, Location location, int xRadius, int yRadius)
    {
        return spawnEntity(entityType, location, xRadius, yRadius, 1);
    }

    public static <T extends Entity> T spawnEntity(EntityType entityType, Location location, int xRadius, int yRadius, int xMin)
    {
        int angle = RANDOM.nextInt(360);
        xRadius = RANDOM.nextInt(xRadius)+xMin;
        Location position = new Location(location.getWorld(), location.getX() + (xRadius * Math.cos(angle)), location.getY(), location.getZ() + (xRadius * Math.sin(angle)));

        int downPosition = getNearestSafeBlock(location, yRadius, false);
        int upPosition = getNearestSafeBlock(location, yRadius, true);

        return (T) ((downPosition == upPosition && downPosition == -1) ? location.getWorld().spawnEntity(location, entityType) : location.getWorld().spawnEntity(position.add(0, Math.min(downPosition, upPosition) + 1,0), entityType));
    }

    private static int getNearestSafeBlock(Location location, int yRadius, boolean up)
    {
        int pass = 0;
        for(int y = 0; y < yRadius; y++)
        {
            Block block = location.getBlock().getRelative(0, up ? y : -y, 0);
            if(block == null){ break; }
            if(!block.getType().equals(Material.AIR) && !block.getType().equals(Material.LAVA)
                    && block.getRelative(0, 1, 0).getType().equals(Material.AIR)
                    && block.getRelative(0, 2, 0).getType().equals(Material.AIR)
            ){ return pass; }
            pass++;
        }
        return -1;
    }

    public Map<Player, ScoreboardSign> getBoards(){
        return this.boards;
    }

    public String getTimeFormat(long seconds) {
        int jour = (int) TimeUnit.SECONDS.toDays(seconds);
        long heures = TimeUnit.SECONDS.toHours(seconds) - (jour * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long seconde = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        String textejour = "";
        if(seconds >= 86400){
            textejour = jour + "j:";
        }

        String texteheures = "";
        if(seconds >= 3600){
            texteheures = heures + "h";
        }

        String texteminutes = "";
        if(seconds >= 60){
            texteminutes = minute + "min";
        }

        return textejour + texteheures + texteminutes + seconde + "s";
    }

    public Map<Player, Boolean> VeulentVoirInfos(){
        return this.veulentvoirinfos;
    }

    public boolean VeutVoirInfos(Player player){
        if(!this.VeulentVoirInfos().containsKey(player)){
            VeulentVoirInfos().put(player, true);
        }

        return VeulentVoirInfos().get(player);
    }

    public String bool(boolean b, int i){
        String bool = null;
        switch (i){
            case 0:
                if(b){
                    bool = ChatColor.GREEN + "§lAffiché";
                } else {
                    bool = ChatColor.RED + "§lCaché";
                }
                break;
            case 1:
                if(b){
                    bool = ChatColor.GREEN + "§lAffichées";
                } else {
                    bool = ChatColor.RED + "§lCachées";
                }
                break;
        }

        return bool;
    }

    public HashMap<Player, Boolean> VeulentVoirInfosActionBar() {
        return voirinfosactionbar;
    }
}