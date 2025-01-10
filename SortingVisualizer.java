import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;

public class SortingVisualizer extends JFrame {
    private int[] array;
    private final int BAR_WIDTH = 10;
    private JPanel controlPanel;
    private JButton bubbleSortButton, selectionSortButton, insertionSortButton, mergeSortButton, quickSortButton;
    private JButton inputArrayButton;
    private JTextField arrayInputField;
    private JLabel timeLabel;
    private boolean isSorting = false;

    public SortingVisualizer() {
        setTitle("Sorting Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        bubbleSortButton = new JButton("Bubble Sort");
        selectionSortButton = new JButton("Selection Sort");
        insertionSortButton = new JButton("Insertion Sort");
        mergeSortButton = new JButton("Merge Sort");
        quickSortButton = new JButton("Quick Sort");
        inputArrayButton = new JButton("Set Array");
        arrayInputField = new JTextField(20);
        timeLabel = new JLabel("Time Complexity: N/A");

        controlPanel.add(arrayInputField);
        controlPanel.add(inputArrayButton);
        controlPanel.add(bubbleSortButton);
        controlPanel.add(selectionSortButton);
        controlPanel.add(insertionSortButton);
        controlPanel.add(mergeSortButton);
        controlPanel.add(quickSortButton);
        controlPanel.add(timeLabel);

        add(controlPanel, BorderLayout.SOUTH);

        generateNewArray();

        bubbleSortButton.addActionListener(e -> runSortingAlgorithm("BubbleSort"));
        selectionSortButton.addActionListener(e -> runSortingAlgorithm("SelectionSort"));
        insertionSortButton.addActionListener(e -> runSortingAlgorithm("InsertionSort"));
        mergeSortButton.addActionListener(e -> runSortingAlgorithm("MergeSort"));
        quickSortButton.addActionListener(e -> runSortingAlgorithm("QuickSort"));

        inputArrayButton.addActionListener(e -> {
            String input = arrayInputField.getText();
            try {
                String[] inputStrings = input.split(",");
                array = new int[inputStrings.length];
                for (int i = 0; i < inputStrings.length; i++) {
                    array[i] = Integer.parseInt(inputStrings[i].trim());
                }
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter integers separated by commas.");
            }
        });

        // Add a component listener to handle resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
    }

    private void generateNewArray() {
        array = new int[getWidth() / BAR_WIDTH];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * (getHeight() - 50));
        }
        repaint();
    }

    private void runSortingAlgorithm(String algorithm) {
        if (array == null || array.length == 0 || isSorting) {
            return; // Do nothing if already sorting
        }

        new Thread(() -> {
            isSorting = true;
            int[] arrayCopy = array.clone();
            long startTime = System.nanoTime();
            switch (algorithm) {
                case "BubbleSort":
                    bubbleSort(arrayCopy);
                    timeLabel.setText("Time Complexity: O(N^2) (BubbleSort)");
                    break;
                case "SelectionSort":
                    selectionSort(arrayCopy);
                    timeLabel.setText("Time Complexity: O(N^2) (SelectionSort)");
                    break;
                case "InsertionSort":
                    insertionSort(arrayCopy);
                    timeLabel.setText("Time Complexity: O(N^2) (InsertionSort)");
                    break;
                case "MergeSort":
                    mergeSort(arrayCopy, 0, arrayCopy.length - 1);
                    timeLabel.setText("Time Complexity: O(N log N) (MergeSort)");
                    break;
                case "QuickSort":
                    quickSort(arrayCopy, 0, arrayCopy.length - 1);
                    timeLabel.setText("Time Complexity: O(N log N) (QuickSort)");
                    break;
            }
            long endTime = System.nanoTime();
            System.out.println("Execution time: " + (endTime - startTime) / 1000000 + " ms");
            isSorting = false;
        }).start();
    }
    private void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
            // Update the display after each iteration, ensure it's done on the EDT
            SwingUtilities.invokeLater(() -> repaint());
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
    }

    private void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
            // Update the display after each iteration, ensure it's done on the EDT
            SwingUtilities.invokeLater(() -> repaint());
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
    }

    private void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
            // Update the display after each iteration, ensure it's done on the EDT
            SwingUtilities.invokeLater(() -> repaint());
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
            // Update the display after each iteration, ensure it's done on the EDT
            SwingUtilities.invokeLater(() -> repaint());
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
            // Update the display after each iteration, ensure it's done on the EDT
            SwingUtilities.invokeLater(() -> repaint());
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (array == null || array.length == 0) return;

        int maxValue = Arrays.stream(array).max().orElse(1); // Avoid division by zero

        for (int i = 0; i < array.length; i++) {
            int scaledHeight = (int) ((double) array[i] / maxValue * (getHeight() - 50));
            g.setColor(Color.BLUE);
            g.fillRect(i * BAR_WIDTH, getHeight() - scaledHeight, BAR_WIDTH, scaledHeight);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingVisualizer visualizer = new SortingVisualizer();
            visualizer.setVisible(true);
        });
    }
}
