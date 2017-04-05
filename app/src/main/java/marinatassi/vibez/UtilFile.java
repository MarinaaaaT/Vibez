package marinatassi.vibez;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Marina on 4/5/17.
 */

public class UtilFile extends MainActivity{

    public static File getFile(String filename, Context context) throws IOException {
        File path = context.getExternalFilesDir(null);
        File file = new File(path, filename);
        System.out.println(file);
        file.createNewFile();
        return file;
    }

    public static File writeToFile(String toWrite, File file) throws IOException{
        FileOutputStream stream = new FileOutputStream(file);
        try {
            stream.write(toWrite.getBytes());
        }
        catch (FileNotFoundException e){
            System.out.println("FILE NOT FOUND");
        }
        finally {
            stream.close();
        }
        return file;
    }

    public static String fileToString(File file) throws IOException{
        int length = (int) file.length();

        byte[] bytes = new byte[length];

        FileInputStream in = new FileInputStream(file);
        try {
            in.read(bytes);
        }
        catch (FileNotFoundException e){
            System.out.println("FILE NOT FOUND");
        }
        finally {
            in.close();
        }

        String contents = new String(bytes);

        return contents;
    }

}