import java.io.*;
import org.apache.commons.cli.*;

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

    static CommandLine checkArguments(String[] args) {
        Options options = new Options();
        OptionGroup key = new OptionGroup();
        key.addOption(new Option("c", true, "encode key"));
        key.addOption(new Option("d", true, "decode key"));
        key.setRequired(true);
        options.addOptionGroup(key);
        Option inputFile = new Option("i", true, "input file");
        inputFile.setRequired(true);
        options.addOption(inputFile);
        Option outputFile = new Option("o", true, "output file");
        outputFile.setRequired(false);
        options.addOption(outputFile);

        CommandLineParser parser = new BasicParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            System.err.println(e.getMessage());
            formatter.printHelp("ciphxor", options);
            return null;
        }

        return cmd;
    }

    private static PrintWriter getWriter(String path) throws IOException {
        if (path == null) {
            return new PrintWriter(System.out);
        } else {
            return new PrintWriter(new File(path));
        }
    }

    public static void main(String[] args) {
        CommandLine cmd;
        if ((cmd = checkArguments(args)) != null) {
            try (PrintWriter writer = getWriter(cmd.getOptionValue("o"))) {
                writer.print(decode(readFile(cmd.getOptionValue("i")), cmd.getOptionValue("c")+cmd.getOptionValue("d")));
            } catch (IOException e) {
                System.err.print("Input/Output Error");
            }
        }
    }
}
