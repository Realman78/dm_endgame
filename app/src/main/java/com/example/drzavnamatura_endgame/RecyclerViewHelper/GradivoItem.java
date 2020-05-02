package com.example.drzavnamatura_endgame.RecyclerViewHelper;


import com.example.drzavnamatura_endgame.R;

public class GradivoItem  {
    private int slikaGradiva;
    private String naslov;


    public GradivoItem(){

    }
    public GradivoItem( String naslov) {
        slikaGradiva = R.drawable.slika_za_iteme;
        this.naslov = naslov;
    }


    public GradivoItem(int slikaGradiva, String naslov) {
        this.slikaGradiva = slikaGradiva;
        this.naslov = naslov;
    }

    int getSlikaGradiva() {
        return slikaGradiva;
    }

    public void setSlikaGradiva(int slikaGradiva) {
        this.slikaGradiva = slikaGradiva;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }
}
