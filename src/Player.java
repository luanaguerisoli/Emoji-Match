import java.io.Serializable;

public class Player implements Serializable {
    private int health;
    private int gold;
    private int experience;
    private int points;
    private String name;

    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.gold = 0;
        this.experience = 0;
        this.points = 0;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}
    public int getGold() {return gold;}
    public void setGold(int gold) {this.gold = gold;}
    public int getExperience() {return experience;}
    public void setExperience(int experience) {this.experience = experience;}
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public int addPoints(int amount) { return points += amount; }

    public int addHealth(int amount){
        return health += amount;
    }
    public int removeHealth(int amount){
        return health -= amount;
    }
    public int addGold(int amount){
        return gold += amount;
    }
    public int addEXP(int amount){
        return experience += amount;
    }

    @Override
    public String toString() {
        return "\n" + "Player: " + name + "\n" +
                "Health: " + health + "\n" +
                "Gold: " + gold + "\n" +
                "Experience: " + experience + "\n" +
                "Points: " + points + "\n";
    }

    // Export player data as string (CSV-like)
    public String exportData() {
        return name + "," + health + "," + gold + "," + experience + "," + points;
    }

    // Create a player from a string
    public static Player fromString(String data) {
        try {
            String[] parts = data.split(",");
            if (parts.length != 5) return null;
            
            Player player = new Player(parts[0]);
            player.setHealth(Integer.parseInt(parts[1]));
            player.setGold(Integer.parseInt(parts[2]));
            player.setExperience(Integer.parseInt(parts[3]));
            player.setPoints(Integer.parseInt(parts[4]));
            
            return player;
        } catch (Exception e) {
            return null;
        }
    }
}
