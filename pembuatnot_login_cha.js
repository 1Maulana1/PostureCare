<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<notifikasiList>
    <notifikasi hari="" interval="1" pesan="qqq" tipe="Jam"/>
</notifikasiList>

<script>
// ...existing code dari login.js...
function login(username, password) {
    // ...kode autentikasi...
    // return true/false
}

// ...existing code dari pembuatnot.js...
function buatNotifikasi(hari, interval, pesan, tipe) {
    // ...kode membuat notifikasi...
    // contoh: simpan ke localStorage atau kirim ke backend
}

// ...existing code dari cha.js...
function chatSend(message) {
    // ...kode kirim pesan chat...
}

// UI: Tambahkan tombol notifikasi di pojok kanan atas
function renderNotificationButton() {
    const btn = document.createElement('button');
    btn.innerText = '🔔 Notifikasi';
    btn.style.position = 'fixed';
    btn.style.top = '10px';
    btn.style.right = '10px';
    btn.style.zIndex = 1000;
    btn.onclick = function() {
        // Tampilkan daftar notifikasi atau form tambah notifikasi
        alert('Fitur Notifikasi: \n- Hari: \n- Interval: \n- Pesan: \n- Tipe: Jam');
        // Atau tampilkan UI lebih lanjut sesuai kebutuhan
    };
    document.body.appendChild(btn);
}

// Panggil fungsi render setelah DOM siap
window.addEventListener('DOMContentLoaded', renderNotificationButton);

// ...existing code...
</script>