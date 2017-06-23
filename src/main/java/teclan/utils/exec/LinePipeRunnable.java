package teclan.utils.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LinePipeRunnable implements PipeRunnable {

    private InputStream in;
    private LineHandler out;

    public LinePipeRunnable(InputStream in, LineHandler out) {
        this.in = in;
        this.out = out;
    }

    public void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in,
                "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            out.handleLine(line);
        }
    }

}
