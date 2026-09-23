import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Tracks two-day scores for a golf tournament and reports the leader(s)
 * and everyone within 10 strokes of the lead.
 *
 * Run interactively:   java MastersGolf
 * Run with random data: java MastersGolf --auto
 */
public class MastersGolf {

    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 40;
    private static final int MIN_SCORE = 18;      // one stroke per hole is the best possible round
    private static final int MAX_SCORE = 95;
    private static final int STROKE_WINDOW = 10;
    private static final int BANNER_WIDTH = 100;

    // Random names generated at: http://random-name-generator.info/ (50, M/F, Rare)
    private static final String[] AUTO_PLAYERS = {
        "Alyson Myles", "Kemberly Battle", "Heidy Garland", "Easter Solis", "Leigha Andersen",
        "Margarett Ruff", "Micheline Ness", "Latrina Egan", "Eneida Sneed", "Lea Earl",
        "Lavone Roney", "Kimiko Peter", "Setsuko Mcclendon", "Maia Beers", "Kasie Peyton",
        "Sanda Easley", "Dionne Mosby", "Denae Cameron", "Broderick Parnell", "Karleen Ricks",
        "Britt Mayes", "Rolanda Snowden", "Dudley Langley", "Mac Hudgens", "Rebbecca Hyatt",
        "Jamika Hooper", "Denyse Scully", "Inge Woodcock", "Gaston Mintz", "Lisette Ocasio",
        "Katharina Whitmire", "Stevie Sage", "Nilda Foust", "Trudy Schell", "Candelaria Boatwright",
        "Gia Nesbitt", "Valery Jung", "Sylvie Escamilla", "Aundrea Meador", "Jamila Anglin",
        "Jaleesa Hadden", "Coralie Southerland", "Crysta Guajardo", "Penni Mast", "Yael Hardin",
        "Margert Bader", "Tristan Aguirre", "Monnie Short", "Cathrine Whelan", "Mathilda Lipscomb"
    };

    /** One player's results. The combined score is derived, so it can never disagree with the rounds. */
    record Golfer(String name, int day1, int day2) {
        int combined() {
            return day1 + day2;
        }
    }

    public static void main(String[] args) {
        boolean auto = args.length > 0 && args[0].equals("--auto");

        System.out.println("It's the Master's Golf Tournament. Time to track scores for the golfers.");

        List<Golfer> golfers;
        if (auto) {
            golfers = generatePlayers(new Random());
        } else {
            // One Scanner for all input; closed automatically when done.
            try (Scanner in = new Scanner(System.in)) {
                golfers = collectPlayers(in);
            }
        }

        printResults(golfers);
        printLeaders(golfers);
        System.out.println(banner("See Ya' At the 19th Hole"));
    }

    // ---------------------------------------------------------------- input

    private static List<Golfer> collectPlayers(Scanner in) {
        int count = readInt(in, "\nPlease enter the number of players:", MIN_PLAYERS, MAX_PLAYERS);
        List<Golfer> golfers = new ArrayList<>(count);

        for (int i = 1; i <= count; i++) {
            System.out.println(banner("Player " + i));
            String name = readName(in, "Please enter the Player " + i + " name:");
            int day1 = readInt(in, "Please enter the day 1 score for " + name + ":", MIN_SCORE, MAX_SCORE);
            int day2 = readInt(in, "Please enter the day 2 score for " + name + ":", MIN_SCORE, MAX_SCORE);
            golfers.add(new Golfer(name, day1, day2));
        }
        return golfers;
    }

    private static List<Golfer> generatePlayers(Random rng) {
        int count = rng.nextInt(MAX_PLAYERS - 5 + 1) + 5;   // 5..40 players

        List<String> names = new ArrayList<>(Arrays.asList(AUTO_PLAYERS));
        Collections.shuffle(names, rng);

        List<Golfer> golfers = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Golfer g = new Golfer(names.get(i), randomScore(rng), randomScore(rng));
            System.out.println(banner("Player " + (i + 1)));
            System.out.println(g.name());
            System.out.println("Day 1 score: " + g.day1());
            System.out.println("Day 2 score: " + g.day2());
            golfers.add(g);
        }
        return golfers;
    }

    private static int randomScore(Random rng) {
        return rng.nextInt(MAX_SCORE - MIN_SCORE + 1) + MIN_SCORE;
    }

    /**
     * Reads whole lines and parses them, so there is never a leftover newline
     * to trip up the next read (the problem nextInt() causes).
     */
    private static int readInt(Scanner in, String prompt, int min, int max) {
        while (true) {
            String line = readLine(in, prompt);
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // not a number; fall through to the error message
            }
            System.out.println("That's not a valid number! Enter " + min + " to " + max + ".");
        }
    }

    private static String readName(Scanner in, String prompt) {
        while (true) {
            String name = readLine(in, prompt);
            if (!name.isEmpty()) {
                return name;
            }
            System.out.println("The name can't be blank.");
        }
    }

    private static String readLine(Scanner in, String prompt) {
        System.out.println(prompt);
        if (!in.hasNextLine()) {
            System.out.println("Input ended unexpectedly. Exiting.");
            System.exit(1);
        }
        return in.nextLine().trim();
    }

    // --------------------------------------------------------------- output

    private static void printResults(List<Golfer> golfers) {
        List<Golfer> leaderboard = new ArrayList<>(golfers);
        leaderboard.sort(Comparator.comparingInt(Golfer::combined)
                                   .thenComparing(Golfer::name));

        int nameWidth = "Player".length();
        for (Golfer g : leaderboard) {
            nameWidth = Math.max(nameWidth, g.name().length());
        }
        String row = "%-" + (nameWidth + 3) + "s%-15s%-15s%-15s%n";

        System.out.println(banner("Tournament Statistics"));
        System.out.printf(row, "Player", "Day 1 Score", "Day 2 Score", "Combined Score");
        for (Golfer g : leaderboard) {
            System.out.printf(row, g.name(), g.day1(), g.day2(), g.combined());
        }
    }

    private static void printLeaders(List<Golfer> golfers) {
        int best = golfers.stream().mapToInt(Golfer::combined).min().orElseThrow();

        List<Golfer> leaders = golfers.stream()
                .filter(g -> g.combined() == best)
                .toList();

        // Nobody can beat the best score, so only the upper bound needs checking.
        List<Golfer> chasers = golfers.stream()
                .filter(g -> g.combined() > best && g.combined() <= best + STROKE_WINDOW)
                .sorted(Comparator.comparingInt(Golfer::combined))
                .toList();

        String title = leaders.size() == 1 ? "Leader" : "Leaders (tied)";
        System.out.println(banner(title + " at " + best));
        leaders.forEach(g -> System.out.println(g.name()));

        System.out.println(banner("Players Within " + STROKE_WINDOW + " Strokes of the Lead"));
        if (chasers.isEmpty()) {
            System.out.println("(none)");
        } else {
            chasers.forEach(g -> System.out.println(g.name() + " (+" + (g.combined() - best) + ")"));
        }
    }

    /** Centers a title in a line of dashes exactly BANNER_WIDTH characters wide. */
    private static String banner(String title) {
        int dashes = Math.max(0, BANNER_WIDTH - title.length() - 2);
        int left = dashes / 2;
        return "-".repeat(left) + " " + title + " " + "-".repeat(dashes - left);
    }
}
