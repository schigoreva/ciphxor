import java.io.*;

public class ciphxor {

    static String decode(byte[] input, String key) {
        byte[] keyBytes = key.getBytes();
        for (int i = 0; i < input.length; i++) {
            input[i] ^= keyBytes[i % keyBytes.length];
        }
        return new String(input);
    }

    static byte[] readFile(String path) throws IOException {
        File file = new File(path);
        try(FileInputStream reader = new FileInputStream(file)) {
            byte[] input = new byte[(int) file.length()];
            reader.read(input);
            return input;
        }
    }

    static boolean checkArguments(String[] args) {
        if (args.length != 3 && args.length != 5) {
            System.err.println("Usage: [-c key] [-d key] inputname.txt [-o outputname.txt]");
            return false;
        }
        if (!args[0].equals("-c") && !args[0].equals("-d")) {
            System.err.println("Please, enter the key");
            return false;
        }
        if (args.length == 5 && !args[3].equals("-o")) {
            System.err.println(String.format("%s, unknown flag", args[3]));
            return false;
        }
        return true;
    }

    private static PrintWriter getWriter(String path) throws IOException {
        if (path.equals("")) {
            return new PrintWriter(System.out);
        } else {
            return new PrintWriter(new File(path));
        }
    }

    public static void main(String[] args) {
        if (checkArguments(args)) {
            try (PrintWriter writer = getWriter(args.length == 3 ? "" : args[4])) {
                writer.print(decode(readFile(args[2]), args[1]));
            } catch (IOException e) {
                System.err.print("Input/Output Error");
            }
        }
    }
}
