package com.esprit.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor 
@NoArgsConstructor
public class Position implements Serializable{

    private int row;
    private int column;
  
}
