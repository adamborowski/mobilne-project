package pl.adamborowski.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by aborowski on 14.12.2015.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchSyncData {
    private String deviceId;
    private List<DeviceItemDelta> deltas = null;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class DeviceItemDelta {
        private String name;
        private String id;
        private int delta;
        private int price = 0;
        private String category = "default";
        boolean deleteRequested = false;
        /**
         * Od tej pory id produktów będą losowane - nie da się "utworzyć" drugi raz tych samych produktów. Jeśli dwa urządzenia utworzą pomidory, serwer może zwrócić błąd, UI: ktoś inny utworzył pomidory. Co chcesz zrobić?
         * Jeśli ktoś przesyła samą deltę, a na serwerze nie ma takiego urządzenia, po cichu usuń obiekt, chyba, że lokalnie od czasu synchronizacji delta się zmieniła, wtedy powiadom, że obiekt został usunięty. czy zamiast tego spróbować utworzyć go ponownie? jeśli w międzyczasie ktos dodal o tej samej nazwie - dostaniemy blad
         * Jeśli ktoś przesyła deleteRequest, usuń cały obiekt
         * Jeśli ktoś przesyła createRequest, to jeśli istnieje na serwerze inny obiekt o tej samej nazwie, zwróć błąd (UI: taki element został utworzony na innym urządzeniu)
         * Jeśli ktoś przesyła deleteRequest, a istenije inny obiekt o tej samej nazwie, to spytaj użytkownika, czy tamten też chce usunąć?:
         * (Uwaga: na innym urządzeniu został utworzony nowy obiekt o tej samej nazwie. Czy chcesz go też usunąć?)
         *
         * scenariusz:
         * serwer, A, B: {4 jabłka, 3 gruszki}; C: {}
         * na A usuwamy jabłka i gruszki, na B dodajemy jedno jabłko,
         *  A: {}, B:{5 jabłek, 3 gruszki}, C:{}, serwer: {4 jabłka, 3 gruszki}
         * sync B - A: {}, B: {5 jabłek, 3 gruszki}, C: {}, serwer: {5 jabłek, 3 gruszki}
         * sync A - A: {}, B: {5 jabłek, 3 gruszki}, C: {}, serwer: {}
         * na C tworzymy jedno jabłko: A: {}, B: {5 jabłek, 3 gruszki}, C: {1 jabłko}, serwer: {}
         * sync C: A: {}, B: {5 jabłek, 3 gruszki}, C: {1 jabłko}, serwer: {1 jabłko}
         * na B dodajemy 1 gruszkę
         * sync C:
         * sync B - error: jabłka przestały istnieć. czy chcesz dodać
         *
         */
        boolean createRequested = false;
        boolean priceChangeRequested = false;
        boolean categoryChangeRequested = false;
    }
}
