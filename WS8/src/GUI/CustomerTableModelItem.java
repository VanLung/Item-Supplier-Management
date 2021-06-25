/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DAO.TblSupplierDAO;
import DTO.Item;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author By Van Lung, IDStudent: SE140193
 */
public class CustomerTableModelItem extends AbstractTableModel {

    private String[] headeres;
    private int[] indexes;
    private Vector<Item> data;
    TblSupplierDAO dao=new TblSupplierDAO();


    public void loadSuppliersComboBox(){
        try{
            dao.loadSuppliersComboBox();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public String getSuppliers(String supCode){
        for (String sup : dao.getListSuppliersComboBox()) {
            if(sup.contains(supCode)){
                return sup;
            }
        }
        return "";
    }
    public Vector<Item> getList(){
        return data;
    }
    public CustomerTableModelItem(String[] headeres,int[] indexes){
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
    public String getColumnName(int column){
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
        Item it=data.get(rowIndex);
        switch(indexes[columnIndex]){
            case 0: return it.getItemCode();
            case 1: return it.getItemName();
            case 2: return it.getUnit();
            case 3: return it.getPrice();
            case 4: return it.isSupplying();
            case 5: return getSuppliers(it.getSupCode());
        }
        return null;
    }

}
