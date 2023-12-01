import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(94, 10, 2, 254.32);
        GameProgress save2 = new GameProgress(78, 23, 5, 576.38);
        GameProgress save3 = new GameProgress(99, 3, 1, 24.3);

        saveGame("Games/savegames/save1.dat", save1);
        saveGame("Games/savegames/save2.dat", save2);
        saveGame("Games/savegames/save3.dat", save3);

        List<String> saves = new ArrayList<>(
                Arrays.asList(
                        "Games/savegames/save1.dat",
                        "Games/savegames/save2.dat",
                        "Games/savegames/save3.dat"
                )
        );
        zipFiles("Games/savegames/zip.zip", saves);

        for (String save : saves) {
            File file = new File(save);
            if (file.delete()) {
                System.out.println("Сохранение " + save + " удалено");
            }
        }
    }

    private static void saveGame(String path, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String zipPath, List<String> saves) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String save : saves) {
                try (FileInputStream fis = new FileInputStream(save)) {
                    ZipEntry entry = new ZipEntry(save);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
