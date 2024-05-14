import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250, 150};
    public static int[] heroesDamage = {20, 15, 10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic"};
    public static int roundNumber = 0;
    public static int medicHealAmount = 30;
    public static boolean medicAlive = true;
    public static boolean healedThisRound = true;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for(int i = 0; i < heroesHealth.length ; i++) {
            if(heroesHealth[i] > 0){
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }

        return false;
    }

    public static void medicHeals() {
        if (medicAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != 3 && heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                    heroesHealth[i] += medicHealAmount;
                    System.out.println("Medic heals " + heroesAttackType[i] + " for " + medicHealAmount + " health points");
                    healedThisRound = true;
                    return;
                }
            }
        }
    }
    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        if(heroesAttackType[randomIndex] != "Medic") {
            bossDefence = heroesAttackType[randomIndex];
        } else{
            randomIndex = random.nextInt(heroesAttackType.length);
            bossDefence = heroesAttackType[randomIndex];
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        showStatistics();
        medicHeals();
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if(heroesAttackType[i] == bossDefence){
                    Random random = new Random();
                    int coefficient = random.nextInt(8) + 2;
                    damage = heroesDamage[i] * coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
        if (heroesHealth[3] <= 0) {
            medicAlive = false;
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");

        System.out.println("Boss health: " + bossHealth + " damage: "
                + bossDamage + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            if(heroesAttackType[i] != "Medic") {
                System.out.println(heroesAttackType[i] + " health: "
                        + heroesHealth[i] + " damage: " + heroesDamage[i]);
            }
        }
        System.out.println("Medic health: " + heroesHealth[3] + " alive: " +  medicAlive);
    }
}