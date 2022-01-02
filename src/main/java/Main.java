import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String KEY = "bmw";

    public static void main(String[] args) {

        try {
            Gson gson = new Gson();
            Bmw bmw = new Bmw(true);
            gson.toJson(bmw);
            String command_1_add = "curl -i --request PUT --header \"Content-Type: application/json\" --data " + gson.toJson(bmw) + " http://localhost:8098/buckets/s15525/keys/" + KEY;
            execute(command_1_add);

            String command_2_get = "curl -i --request GET http://localhost:8098/buckets/s15525/keys/" + KEY;
            String outPut = execute(command_2_get);
            bmw = gson.fromJson(getResult(outPut), Bmw.class);
            System.out.println(bmw.toString());
            bmw.setFan(false);

            String command_3_update = "curl -i --request PUT --header \"Content-Type: application/json\" --data " + gson.toJson(bmw) + " http://localhost:8098/buckets/s15525/keys/" + KEY;
            execute(command_3_update);

            String command_4_get = "curl -i --request GET http://localhost:8098/buckets/s15525/keys/" + KEY;
            outPut = execute(command_4_get);
            bmw = gson.fromJson(getResult(outPut), Bmw.class);
            System.out.println(bmw.toString());

            String command_5_DELETE = "curl -i --request DELETE http://localhost:8098/buckets/s15525/keys/" + KEY;
            execute(command_5_DELETE);

            String command_6_get = "curl -i --request GET http://localhost:8098/buckets/s15525/keys/" + KEY;
            outPut = execute(command_6_get);
            System.out.println(getResult(outPut));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String execute(String command) throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        InputStream inputStream = process.getInputStream();
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

    private static String getResult(String input) {
        String[] arr = input.split("\n");
        return !arr[arr.length - 1].equals("") ? arr[arr.length - 1] : "NOTHING!";
    }
}

