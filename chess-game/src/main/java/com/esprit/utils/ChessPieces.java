package com.esprit.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.esprit.beans.*;

public class ChessPieces implements Serializable {

    private static final Map<Class<? extends Piece>, String> pieceUnicodeMap = createPieceUnicodeMap();

    private static Map<Class<? extends Piece>, String> createPieceUnicodeMap() {
        Map<Class<? extends Piece>, String> map = new HashMap<>();
        map.put(Pawn.class, "\u265F");
        map.put(Rook.class, "\u265C");
        map.put(Knight.class, "\u265E");
        map.put(Bishop.class, "\u265D");
        map.put(Queen.class, "\u265B");
        map.put(King.class, "\u265A");
        return map;
    }

    public Map<Class<? extends Piece>, String> getPieceUnicodeMap() {
        return pieceUnicodeMap;
    }
}
