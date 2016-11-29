/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmojsino_zadaca_1;

/**
 *
 * @author Mario
 */
public class Ronioc {

    //required
    private String imeRonioca;
    private int godinaRodjenja;

    //optional
    private String agencija;
    private String agencijaNorm;
    private String rang;
    private String rangNorm;

    public String getImeRonioca() {
        return imeRonioca;
    }

    public int getGodinaRodjenja() {
        return godinaRodjenja;
    }

    public String getAgencija() {
        return agencija;
    }

    public String getRang() {
        return rang;
    }

    public String getRangNorm() {
        return rangNorm;
    }

    public String getAgencijaNorm() {
        return agencijaNorm;
    }

    private Ronioc(RoniocBuilder builder) {
        this.agencija = builder.agencija;
        this.agencijaNorm = builder.agencijaNorm;
        this.godinaRodjenja = builder.godinaRodjenja;
        this.imeRonioca = builder.imeRonioca;
        this.rang = builder.rang;
        this.rangNorm = builder.rangNorm;
    }

    public static class RoniocBuilder {

        //required
        private String imeRonioca;
        private int godinaRodjenja;

        //optional
        private String agencija;
        private String agencijaNorm;
        private String rang;
        private String rangNorm;

        public RoniocBuilder(String ime, int godina) {
            this.imeRonioca = ime;
            this.godinaRodjenja = godina;
        }

        public RoniocBuilder setAgencija(String agencija) {
            this.agencija = agencija;
            return this;
        }

        public RoniocBuilder setRang(String rang) {
            this.rang = rang;
            return this;
        }

        public RoniocBuilder setAgencijaNorm(String agencijaNorm) {
            this.agencijaNorm = agencijaNorm;
            return this;
        }

        public RoniocBuilder setRangNorm() {
            this.rangNorm = RankingNorm.normalizirajRazinu(this.agencija, this.rang);
            return this;
        }

        public Ronioc build() {
            return new Ronioc(this);
        }
    }

    
}
