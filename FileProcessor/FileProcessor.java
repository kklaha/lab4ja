import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileProcessor implements Runnable {
    private final String inputFileName;
    private final String outputFileName;
    private Thread thread;
    
    public FileProcessor(String inputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = inputFileName + "_res";
        this.thread = new Thread(this);
    }
    
    // Метод для запуска потока
    public void start() {
        thread.start();
    }
    
    // Метод для ожидания завершения потока
    public void join() throws InterruptedException {
        thread.join();
    }
    
    @Override
    public void run() {
        System.out.println(thread.getName() + " начал обработку файла: " + inputFileName);
        
        try {
            // Чтение чисел из входного файла
            List<Double> numbers = readNumbersFromFile(inputFileName);
            
            // Вычисление квадратов
            List<Double> squares = calculateSquares(numbers);
            
            // Запись результатов в выходной файл
            writeSquaresToFile(squares, outputFileName);
            
            System.out.println(thread.getName() + " завершил обработку файла: " + inputFileName);
            System.out.println("  - Обработано чисел: " + numbers.size());
            System.out.println("  - Результат сохранен в: " + outputFileName);
            System.out.println("  - Первые 5 результатов: " + getFirstFiveSquares(squares));
            
        } catch (FileNotFoundException e) {
            System.err.println(thread.getName() + " - Ошибка: Файл не найден: " + inputFileName);
        } catch (IOException e) {
            System.err.println(thread.getName() + " - Ошибка ввода/вывода: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println(thread.getName() + " - Ошибка: Некорректный формат числа в файле: " + inputFileName);
        }
    }
    
    private List<Double> readNumbersFromFile(String fileName) throws IOException {
        List<Double> numbers = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    // Поддержка нескольких чисел в строке через пробел
                    String[] parts = line.split("\\s+");
                    for (String part : parts) {
                        if (!part.isEmpty()) {
                            double number = Double.parseDouble(part);
                            numbers.add(number);
                        }
                    }
                }
            }
        }
        
        return numbers;
    }
    
    private List<Double> calculateSquares(List<Double> numbers) {
        List<Double> squares = new ArrayList<>(numbers.size());
        for (Double number : numbers) {
            squares.add(number * number);
        }
        return squares;
    }
    
    private void writeSquaresToFile(List<Double> squares, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < squares.size(); i++) {
                // Форматируем вывод: целые числа без десятичной части, дробные с 6 знаками
                double value = squares.get(i);
                if (value == Math.floor(value)) {
                    writer.write(String.format("%.0f", value));
                } else {
                    writer.write(String.format("%.6f", value));
                }
                if (i < squares.size() - 1) {
                    writer.newLine();
                }
            }
        }
    }
    
    private String getFirstFiveSquares(List<Double> squares) {
        StringBuilder sb = new StringBuilder("[");
        int count = Math.min(5, squares.size());
        for (int i = 0; i < count; i++) {
            if (i > 0) sb.append(", ");
            double value = squares.get(i);
            if (value == Math.floor(value)) {
                sb.append(String.format("%.0f", value));
            } else {
                sb.append(String.format("%.2f", value));
            }
        }
        if (squares.size() > 5) {
            sb.append(", ...");
        }
        sb.append("]");
        return sb.toString();
    }
}
