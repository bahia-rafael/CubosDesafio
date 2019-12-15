package com.example.myapplication.presenter;

public interface Config {

    int ACAO = 28;
    int DRAMA = 18;
    int FANTASIA = 14;
    int FICCAO = 878;
    int NOT_GENRE = 9999;

    static int getGeneroToTabs(int tab) {
        if (tab == 0) {
            return ACAO;
        } else if (tab == 1) {
            return DRAMA;
        } else if (tab == 2) {
            return FANTASIA;
        } else if (tab == 3) {
            return FICCAO;
        } else {
            return 0;
        }
    }
}
