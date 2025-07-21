package src;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// Class untuk merepresentasikan data penerbangan
class Flight {
    private String id;
    private int depDelay;
    private int arrDelay;
    private String origin;
    private String dest;
    private String name;
    
    public Flight(String id, int depDelay, int arrDelay, String origin, String dest, String name) {
        this.id = id;
        this.depDelay = depDelay;
        this.arrDelay = arrDelay;
        this.origin = origin;
        this.dest = dest;
        this.name = name;
    }
    
    // Getter methods
    public String getId() { return id; }
    public int getDepDelay() { return depDelay; }
    public int getArrDelay() { return arrDelay; }
    public String getOrigin() { return origin; }
    public String getDest() { return dest; }
    public String getName() { return name; }
    
    @Override
    public String toString() {
        return String.format("ID: %s, Maskapai: %s, Asal: %s, Tujuan: %s, Delay Keberangkatan: %d, Delay Kedatangan: %d", 
                           id, name, origin, dest, depDelay, arrDelay);
    }
}

public class FlightDataAnalysis {
    private List<Flight> flights;
    
    public FlightDataAnalysis() {
        flights = new ArrayList<>();
    }
    
    // Method untuk membaca data dari file CSV
    public void readFlightData(String filename) {
        // Cek apakah file ada
        File file = new File(filename);
        if (!file.exists()) {
            System.err.println("File tidak ditemukan: " + filename);
            System.err.println("Path lengkap: " + file.getAbsolutePath());
            System.err.println("Pastikan file " + filename + " ada di direktori yang sama dengan program Java");
            
            // Buat data dummy untuk testing
            createDummyData();
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                
                String[] values = line.split(",");
                if (values.length >= 6) {
                    try {
                        String id = values[0].trim();
                        int depDelay = Integer.parseInt(values[1].trim());
                        int arrDelay = Integer.parseInt(values[2].trim());
                        String origin = values[3].trim();
                        String dest = values[4].trim();
                        String name = values[5].trim();
                        
                        flights.add(new Flight(id, depDelay, arrDelay, origin, dest, name));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing line: " + line);
                    }
                }
            }
            System.out.println("Data berhasil dibaca. Total penerbangan: " + flights.size());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            createDummyData();
        }
    }
    
    // Method untuk membuat data dummy jika file tidak ada
    private void createDummyData() {
        System.out.println("Membuat data dummy untuk testing...");
        
        // Data dummy untuk testing
        flights.add(new Flight("1", -15, -10, "JFK", "LAX", "American Airlines"));
        flights.add(new Flight("2", 25, 0, "LAX", "JFK", "Delta Airlines"));
        flights.add(new Flight("3", 45, 50, "ORD", "DFW", "United Airlines"));
        flights.add(new Flight("4", -20, -18, "DFW", "ATL", "Southwest Airlines"));
        flights.add(new Flight("5", 30, 25, "ATL", "MIA", "JetBlue Airways"));
        flights.add(new Flight("6", -25, -22, "MIA", "BOS", "American Airlines"));
        flights.add(new Flight("7", 10, 8, "BOS", "SEA", "Alaska Airlines"));
        flights.add(new Flight("8", -30, -28, "SEA", "SFO", "United Airlines"));
        flights.add(new Flight("9", 60, 65, "SFO", "LAS", "Southwest Airlines"));
        flights.add(new Flight("10", -18, -15, "LAS", "PHX", "American Airlines"));
        flights.add(new Flight("11", 25, 0, "PHX", "DEN", "Frontier Airlines"));
        flights.add(new Flight("12", -22, -20, "DEN", "MSP", "Delta Airlines"));
        flights.add(new Flight("13", 15, 12, "MSP", "DTW", "Delta Airlines"));
        flights.add(new Flight("14", -35, -30, "DTW", "PHL", "American Airlines"));
        flights.add(new Flight("15", 25, 0, "PHL", "CLT", "American Airlines"));
        
        System.out.println("Data dummy berhasil dibuat. Total penerbangan: " + flights.size());
    }
    
    // a. Maskapai dengan keterlambatan keberangkatan paling lama
    public void findLongestDepartureDelay() {
        System.out.println("\n=== JAWABAN A: Maskapai dengan keterlambatan keberangkatan paling lama ===");
        
        Flight maxDelayFlight = flights.stream()
                .max(Comparator.comparingInt(Flight::getDepDelay))
                .orElse(null);
        
        if (maxDelayFlight != null) {
            System.out.println("Maskapai dengan keterlambatan keberangkatan paling lama:");
            System.out.println("Nama Maskapai: " + maxDelayFlight.getName());
            System.out.println("Keterlambatan: " + maxDelayFlight.getDepDelay() + " menit");
            System.out.println("Detail: " + maxDelayFlight);
        }
    }
    
    // b. Tujuan maskapai dengan keterlambatan kedatangan paling lama
    public void findLongestArrivalDelay() {
        System.out.println("\n=== JAWABAN B: Tujuan maskapai dengan keterlambatan kedatangan paling lama ===");
        
        Flight maxArrDelayFlight = flights.stream()
                .max(Comparator.comparingInt(Flight::getArrDelay))
                .orElse(null);
        
        if (maxArrDelayFlight != null) {
            System.out.println("Tujuan maskapai dengan keterlambatan kedatangan paling lama:");
            System.out.println("Tujuan: " + maxArrDelayFlight.getDest());
            System.out.println("Nama Maskapai: " + maxArrDelayFlight.getName());
            System.out.println("Keterlambatan Kedatangan: " + maxArrDelayFlight.getArrDelay() + " menit");
            System.out.println("Detail: " + maxArrDelayFlight);
        }
    }
    
    // c. Asal penerbangan dengan keberangkatan awal paling cepat ke-7
    public void findSeventhEarliestDeparture() {
        System.out.println("\n=== JAWABAN C: Asal penerbangan dengan keberangkatan awal paling cepat ke-7 ===");
        List<Flight> sortedByEarlyDeparture = flights.stream()
                .sorted(Comparator.comparingInt(Flight::getDepDelay))
                .collect(Collectors.toList());
        
        if (sortedByEarlyDeparture.size() >= 7) {
            Flight seventhEarliest = sortedByEarlyDeparture.get(6); // Index 6 untuk ke-7
            System.out.println("Penerbangan dengan keberangkatan awal paling cepat ke-7:");
            System.out.println("Asal: " + seventhEarliest.getOrigin());
            System.out.println("Keberangkatan awal: " + seventhEarliest.getDepDelay() + " menit");
            System.out.println("Detail: " + seventhEarliest);
            
            // Tampilkan 7 penerbangan teratas untuk konteks
            System.out.println("\nTop 7 penerbangan dengan keberangkatan paling awal:");
            for (int i = 0; i < Math.min(7, sortedByEarlyDeparture.size()); i++) {
                Flight f = sortedByEarlyDeparture.get(i);
                System.out.println((i+1) + ". " + f.getOrigin() + " - " + f.getName() + " (Delay: " + f.getDepDelay() + " menit)");
            }
        }
    }
    
    // d. Nama maskapai dengan kedatangan awal paling cepat ke-13
    public void findThirteenthEarliestArrival() {
        System.out.println("\n=== JAWABAN D: Nama maskapai dengan kedatangan awal paling cepat ke-13 ===");
        List<Flight> sortedByEarlyArrival = flights.stream()
                .sorted(Comparator.comparingInt(Flight::getArrDelay))
                .collect(Collectors.toList());
        
        if (sortedByEarlyArrival.size() >= 13) {
            Flight thirteenthEarliest = sortedByEarlyArrival.get(12); // Index 12 untuk ke-13
            System.out.println("Maskapai dengan kedatangan awal paling cepat ke-13:");
            System.out.println("Nama Maskapai: " + thirteenthEarliest.getName());
            System.out.println("Kedatangan awal: " + thirteenthEarliest.getArrDelay() + " menit");
            System.out.println("Detail: " + thirteenthEarliest);
            
            // Tampilkan 13 penerbangan teratas untuk konteks
            System.out.println("\nTop 13 penerbangan dengan kedatangan paling awal:");
            for (int i = 0; i < Math.min(13, sortedByEarlyArrival.size()); i++) {
                Flight f = sortedByEarlyArrival.get(i);
                System.out.println((i+1) + ". " + f.getName() + " (Delay: " + f.getArrDelay() + " menit)");
            }
        }
    }
    
    // e. Maskapai dengan delay keberangkatan 25 menit tapi tiba tepat waktu
    public void findDelayedDepartureOnTimeArrival() {
        System.out.println("\n=== JAWABAN E: Maskapai dengan delay keberangkatan 25 menit tapi tiba tepat waktu ===");
        List<Flight> matchingFlights = flights.stream()
                .filter(f -> f.getDepDelay() == 25 && f.getArrDelay() == 0)
                .collect(Collectors.toList());
        
        if (!matchingFlights.isEmpty()) {
            System.out.println("Maskapai dengan delay keberangkatan 25 menit tapi tiba tepat waktu:");
            for (Flight flight : matchingFlights) {
                System.out.println("Nama Maskapai: " + flight.getName());
                System.out.println("Rute: " + flight.getOrigin() + " → " + flight.getDest());
                System.out.println("Delay Keberangkatan: " + flight.getDepDelay() + " menit");
                System.out.println("Delay Kedatangan: " + flight.getArrDelay() + " menit");
                System.out.println("Detail: " + flight);
                System.out.println("---");
            }
        } else {
            System.out.println("Tidak ditemukan penerbangan dengan kriteria tersebut.");
            
            List<Flight> dep25Flights = flights.stream()
                    .filter(f -> f.getDepDelay() == 25)
                    .collect(Collectors.toList());
            if (!dep25Flights.isEmpty()) {
                System.out.println("\nPenerbangan dengan delay keberangkatan 25 menit:");
                for (Flight flight : dep25Flights) {
                    System.out.println(flight + " (Delay Kedatangan: " + flight.getArrDelay() + " menit)");
                }
            }
        }
    }
    
    // Method untuk menampilkan statistik umum
    public void showStatistics() {
        System.out.println("\n=== STATISTIK UMUM ===");
        System.out.println("Total penerbangan: " + flights.size());
        
        if (!flights.isEmpty()) {
            double avgDepDelay = flights.stream().mapToInt(Flight::getDepDelay).average().orElse(0);
            double avgArrDelay = flights.stream().mapToInt(Flight::getArrDelay).average().orElse(0);
            
            System.out.println("Rata-rata delay keberangkatan: " + String.format("%.2f", avgDepDelay) + " menit");
            System.out.println("Rata-rata delay kedatangan: " + String.format("%.2f", avgArrDelay) + " menit");
            
            Set<String> uniqueAirlines = new HashSet<>();
            for (Flight f : flights) {
                uniqueAirlines.add(f.getName());
            }
            System.out.println("Jumlah maskapai unik: " + uniqueAirlines.size());
        }
    }
    
    public static void main(String[] args) {
        FlightDataAnalysis analysis = new FlightDataAnalysis();
        
        // Coba beberapa kemungkinan lokasi file
        String[] possibleFiles = {
            "flights_part.csv",
            "src/flights_part.csv",
            "./flights_part.csv",
            "../flights_part.csv"
        };
        
        System.out.println("=== ANALISIS DATA PENERBANGAN ===");
        
        String csvFile = null;
        for (String filename : possibleFiles) {
            File file = new File(filename);
            if (file.exists()) {
                csvFile = filename;
                break;
            }
        }
        
        if (csvFile != null) {
            System.out.println("Membaca data dari file: " + csvFile);
        } else {
            System.out.println("File flights_part.csv tidak ditemukan di lokasi manapun.");
            System.out.println("Program akan menggunakan data dummy untuk demonstrasi.");
        }
        
        analysis.readFlightData(csvFile != null ? csvFile : "flights_part.csv");
        
        // Pastikan ada data sebelum melanjutkan
        if (analysis.flights.isEmpty()) {
            System.err.println("Tidak ada data untuk dianalisis!");
            return;
        }
        
        // Jalankan semua analisis
        analysis.findLongestDepartureDelay();
        analysis.findLongestArrivalDelay();
        analysis.findSeventhEarliestDeparture();
        analysis.findThirteenthEarliestArrival();
        analysis.findDelayedDepartureOnTimeArrival();
        analysis.showStatistics();
    }
}