package util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.FileOutputStream;

public class XMLSaver {
    public static void savePartialLevel(Level originalLevel, int completedExerciseCount, String savePath) {
        try {
            Level newLevel = new Level();
            newLevel.setLevel(originalLevel.getLevel());
            newLevel.setDay(originalLevel.getDay());
            newLevel.setKalori(originalLevel.getKalori());

            // Buat objek baru untuk Exercise dan ImagePath yang hanya sebagian
            Exercise newExercise = new Exercise();
            ImagePath newImagePath = new ImagePath();

            String[] originalExercises = originalLevel.getExercise().getAllExercises();
            String[] originalImages = originalLevel.getImagePath().getAllPaths();

            // Isi hanya sampai index yang selesai
            if (completedExerciseCount > originalExercises.length)
                completedExerciseCount = originalExercises.length;

            for (int i = 0; i < completedExerciseCount; i++) {
                switch (i) {
                    case 0:
                        newExercise.setExercise1(originalExercises[0]);
                        newImagePath.setImagePath1(originalImages[0]);
                        break;
                    case 1:
                        newExercise.setExercise2(originalExercises[1]);
                        newImagePath.setImagePath2(originalImages[1]);
                        break;
                    case 2:
                        newExercise.setExercise3(originalExercises[2]);
                        newImagePath.setImagePath3(originalImages[2]);
                        break;
                    case 3:
                        newExercise.setExercise4(originalExercises[3]);
                        newImagePath.setImagePath4(originalImages[3]);
                        break;
                    case 4:
                        newExercise.setExercise5(originalExercises[4]);
                        newImagePath.setImagePath5(originalImages[4]);
                        break;
                    case 5:
                        newExercise.setExercise6(originalExercises[5]);
                        newImagePath.setImagePath6(originalImages[5]);
                        break;
                    case 6:
                        newExercise.setExercise7(originalExercises[6]);
                        newImagePath.setImagePath7(originalImages[6]);
                        break;
                    case 7:
                        newExercise.setExercise8(originalExercises[7]);
                        newImagePath.setImagePath8(originalImages[7]);
                        break;
                    case 8:
                        newExercise.setExercise9(originalExercises[8]);
                        newImagePath.setImagePath9(originalImages[8]);
                        break;
                    case 9:
                        newExercise.setExercise10(originalExercises[9]);
                        newImagePath.setImagePath10(originalImages[9]);
                        break;
                    case 10:
                        newExercise.setExercise11(originalExercises[10]);
                        newImagePath.setImagePath11(originalImages[10]);
                        break;
                }
            }

            newLevel.setExercise(newExercise);
            newLevel.setImagePath(newImagePath);

            // Simpan ke file XML
            XStream xstream = new XStream(new StaxDriver());
            xstream.alias("util.Level", Level.class);

            FileOutputStream fos = new FileOutputStream(savePath);
            xstream.toXML(newLevel, fos);
            fos.close();

            System.out.println("Data berhasil disimpan ke: " + savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
