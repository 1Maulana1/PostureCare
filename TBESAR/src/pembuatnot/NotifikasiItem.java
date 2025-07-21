package src.pembuatnot;

import java.io.Serializable;

public class NotifikasiItem implements Serializable {
    public String pesan;
    public int interval;
    public String tipe;  // "Menit" atau "Jam"
    public String hari;  // (bisa dikosongkan atau dihapus jika tidak dipakai)

    public NotifikasiItem(String pesan, int interval, String tipe, String hari) {
        this.pesan = pesan;
        this.interval = interval;
        this.tipe = tipe;
        this.hari = hari;
    }

    @Override
    public String toString() {
        return pesan + " | Interval: " + interval + " " + tipe;
    }
}
