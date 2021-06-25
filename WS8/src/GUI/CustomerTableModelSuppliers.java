/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DTO.Suppliers;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author By Van Lung, IDStudent: SE140193
 */
public class CustomerTableModelSuppliers extends AbstractTableModel {

    private String[] headeres;
    private int[] indexes;
    private Vector<Suppliers> data;
    public Vector<Suppliers> getList(){
        return data;
    }
    public CustomerTableModelSuppliers(String[] headeres,int[] indexes){
        this.headeres=new String[headeres.length];
        for (int i = 0; i < headeres.length; i++) {
            this.headeres[i]=headeres[i];
        }
        this.indexes=new int[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            this.indexes[i]=indexes[i];
        }
        data=new Vector<>();
    }

    @Override
    public String getColumnName(int column) {
        if(column>=0||column<headeres.length){
            return headeres[column];
        }else return "";
    }
    @Override
    public int getRowCount(){
        return data.size();
    }
    @Override
    public int getColumnCount(){
        return headeres.length;
    }
    @Override
    public Object getValueAt(int rowIndex,int columnIndex){
        if(rowIndex<0||rowIndex>=data.size()||columnIndex<0||columnIndex>=headeres.length){
            return null;
        }
        Suppliers sup=data.get(rowIndex);
        switch(indexes[columnIndex]){
            case 0: return sup.getSupCode();
            case 1: return sup.getSupName();
            case 2: return sup.getAddress();
            case 3: return sup.isColloborating();
        }
        return null;
    }

}
