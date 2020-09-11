import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    @Test
    void testDecode() {
        for (String str : List.of("Hello world", "Test string", "VEEEEEEEEEEEEEEEEEEEEEEERYYYYYYYYY BIG STRING")) {
            for (String key : List.of("Key1", "A", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")) {
                assertEquals(ciphxor.decode(ciphxor.decode(str.getBytes(), key).getBytes(), key), str);
            }
        }
    }

    @Test
    void testReadFile() throws IOException {
        for (String str : List.of("Hello World", "\n\n\nABB", "CCA", "", "\t\nA B\n")) {
            PrintWriter writer = new PrintWriter(new File("input.txt"));
            writer.print(str);
            writer.close();
            assertEquals(str, new String(ciphxor.readFile("input.txt")));
        }
    }

    @Test
    void testCheckArguments() {
        for (String str : List.of("-c key input.txt", "-c a-aa@ file.txt -o file2.txt", "-d c f -o f")) {
            Assertions.assertTrue(ciphxor.checkArguments(str.split(" ")));
        }
        for (String str : List.of("-a key input.txt", "", "-c -c -c -c -c", "-c a file.txt file.txt", "-c a-aa@ f f -o file2.txt", "-d c f f")) {
            Assertions.assertFalse(ciphxor.checkArguments(str.split(" ")));
        }
    }
}
