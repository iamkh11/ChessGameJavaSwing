package com.esprit.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Piece implements Serializable{

    protected Piece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }

    protected Position position;
    protected PieceColor color;
  
    public abstract boolean isValidMove(Position newPosition, Piece[][] board);
    
}