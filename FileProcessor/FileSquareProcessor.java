import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileSquareProcessor implements Runnable {
    private final String inputFileName;
    private final String outputFileName;
    private Thread t;

    public FileSquareProcessor(String inputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = inputFileName + "_res";
        t = new Thread(this);
    }
    public void start() {
        t.start();
    }

    @Override
    public void run() {
        try {
            processFile();
            System.out.println("Файл '" + inputFileName + "' успешно обработан. Результат: '" + outputFileName + "'");
        } catch (Exception e) {
            System.err.println("Ошибка при обработке файла '" + inputFileName + "': " + e.getMessage());
        }
    }


    private void processFile() throws Exception {
        List<Double> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        double number = Double.parseDouble(line);
                        numbers.add(number);
                    } catch (NumberFormatException e) {
                        System.err.println("Пропуск некорректного значения в файле '" +
                                inputFileName + "': " + line);
                    }
                }
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
            for (double num : numbers) {
                double square = num * num;
                writer.printf("%.6f%n", square);
            }
        }
    }
    public void join() throws InterruptedException {
        if (t != null) {
            t.join();
        }
    }
}
