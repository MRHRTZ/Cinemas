package co.gararetech.cinemas.model;

import co.gararetech.cinemas.view.CheckoutTicketView;
import co.gararetech.cinemas.view.PembayaranView;
import java.util.List;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;

public class BookingTicketModel {

    private CheckoutTicketView checkoutView;
    private JSONArray kursi;
    private JSONArray kursiAnda;
    private int harga;
    private int hargaTiket;
    private double pajak;
    private int totalHarga;
    private JSONObject checkoutTicketObj;

    public BookingTicketModel() {
        this.pajak = 0.03;
        this.kursi = new JSONArray();
        this.kursiAnda = new JSONArray();
    }

    public CheckoutTicketView getCheckoutView() {
        return checkoutView;
    }

    public void setCheckoutView(CheckoutTicketView checkoutView) {
        this.checkoutView = checkoutView;
    }

    public int getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(int hargaTiket) {
        this.hargaTiket = hargaTiket;
    }

    public double getPajak() {
        return pajak;
    }

    public JSONArray getKursi() {
        return kursi;
    }

    public void setKursi(JSONArray kursi) {
        this.kursi = kursi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public JSONObject getCheckoutTicketObj() {
        return checkoutTicketObj;
    }

    public void setCheckoutTicketObj(JSONObject checkoutTicketObj) {
        this.checkoutTicketObj = checkoutTicketObj;
    }

    public JSONArray getKursiAnda() {
        return kursiAnda;
    }

    public void setKursiAnda(JSONArray kursiAnda) {
        this.kursiAnda = kursiAnda;
    }

}
