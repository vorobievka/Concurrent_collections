import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

    static int capacity = 100;
    static ArrayBlockingQueue<String> lenA = new ArrayBlockingQueue<String>(capacity);

    static ArrayBlockingQueue<String> lenB = new ArrayBlockingQueue<String>(capacity);

    static ArrayBlockingQueue<String> lenC = new ArrayBlockingQueue<String>(capacity);


    public static void main(String[] args) {

        new Thread(() -> {

            for (int i = 0; i < 10_000; i++) {
                String text = generateText("abc", 100_000);
                try {
                    lenA.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    lenB.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    lenC.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }).start();

        new Thread(() -> {
            int maxA = 0;
            try {
                for (int j = 0; j < 10_000; j++) {
                    String string = lenA.take();
                    int result = countingChars(string, 'a');
                    if (result > maxA) {
                        maxA = result;
                    }
                }
                System.out.println("maxA= " + maxA);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();

        new Thread(() -> {
            int maxB = 0;
            try {
                for (int j = 0; j < 10_000; j++) {
                    String string = lenB.take();
                    int result = countingChars(string, 'b');
                    if (result > maxB) {
                        maxB = result;
                    }
                }
                System.out.println("maxB= " + maxB);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();

        new Thread(() -> {
            int maxC = 0;
            try {
                for (int j = 0; j < 10_000; j++) {
                    String string = lenC.take();
                    int result = countingChars(string, 'c');
                    if (result > maxC) {
                        maxC = result;
                    }
                }
                System.out.println("maxC= " + maxC);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int countingChars(String string, char ch) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ch) {
                count += 1;
            }
        }
        return count;
    }

}