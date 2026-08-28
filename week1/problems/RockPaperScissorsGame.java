package week1.problems;

import java.util.Random;

public class RockPaperScissorsGame {

    public static final String ROCK = "Rock";
    public static final String PAPER = "Paper";
    public static final String SCISSORS = "Scissors";
    public static final String[] MOVES = {ROCK, PAPER, SCISSORS};

    public static String playRound(String playerMove, String computerMove) {
        if (playerMove == null || computerMove == null) {
            return "Invalid Move";
        }

        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equalsIgnoreCase(ROCK) && computerMove.equalsIgnoreCase(SCISSORS)) ||
            (playerMove.equalsIgnoreCase(PAPER) && computerMove.equalsIgnoreCase(ROCK)) ||
            (playerMove.equalsIgnoreCase(SCISSORS) && computerMove.equalsIgnoreCase(PAPER))) {
            return "Player Wins";
        } else {
            return "Computer Wins";
        }
    }

    public static String getRandomComputerMove(Random random) {
        return MOVES[random.nextInt(MOVES.length)];
    }

    public static void runGameSimulation(String[] playerMoves) {
        Random random = new Random(42); // Seeded for deterministic output / testing
        int totalRounds = playerMoves.length;
        int wins = 0;
        int losses = 0;
        int draws = 0;

        String[] computerMoves = new String[totalRounds];
        String[] results = new String[totalRounds];

        for (int i = 0; i < totalRounds; i++) {
            String compMove = getRandomComputerMove(random);
            computerMoves[i] = compMove;
            String result = playRound(playerMoves[i], compMove);
            results[i] = result;

            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else if (result.equals("Draw")) {
                draws++;
            }

            System.out.printf("Round %d - Player: %s, Computer: %s | %s%n",
                    i + 1, playerMoves[i], compMove, result);
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%-8s | %-12s | %-14s | %-15s%n", "Round", "Player Move", "Computer Move", "Result");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < totalRounds; i++) {
            System.out.printf("%-8d | %-12s | %-14s | %-15s%n",
                    i + 1, playerMoves[i], computerMoves[i], results[i]);
        }
        System.out.println("------------------------------------------------------------");

        double winPercent = (totalRounds > 0) ? ((double) wins / totalRounds) * 100.0 : 0.0;
        System.out.printf("Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n",
                wins, losses, draws, winPercent);
    }

    public static void main(String[] args) {
        String[] demoMoves = {"Rock", "Paper", "Scissors", "Rock", "Paper"};
        runGameSimulation(demoMoves);
    }
}
