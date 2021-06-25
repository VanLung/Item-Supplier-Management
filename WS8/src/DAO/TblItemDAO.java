/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Item;
import GUI.CustomerTableModelItem;
import ULTILS.DBUltis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author By Van Lung, IDStudent: SE140193
 */
public class TblItemDAO {

    private String[] headeres = {"itemCode", "itemName", "Suppliers", "Unit", "Price", "Suppying"};
    private int[] indexes = {0, 1, 5, 2, 3, 4};
    private CustomerTableModelItem modelItem = new CustomerTableModelItem(headeres, indexes);

    public CustomerTableModelItem getModelItem() {
        return modelItem;
    }

    public TblItemDAO() {
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() throws Exception {
        modelItem.getList().clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "SELECT*FROM tblItems";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Item it = new Item(rs.getString("itemCode"), rs.getString("itemName"), rs.getString("unit"),
                            rs.getFloat("price"), rs.getBoolean("supplying"), rs.getString("supCode"));
                    modelItem.getList().add(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public String loadSupCodeIntoItems(String Suppliers) {
        StringTokenizer stk = new StringTokenizer(Suppliers, " - ");
        return stk.nextToken();
    }

    public int Insert(Item it) throws Exception {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "insert into tblItems(itemCode,itemName,supCode,unit,price,supplying) values(?,?,?,?,?,?)";
                StringTokenizer stk = new StringTokenizer(it.getSupCode(), " - ");
                ps = con.prepareStatement(sql);
                ps.setString(1, it.getItemCode());
                ps.setString(2, it.getItemName());
                ps.setString(3, stk.nextToken());
                ps.setString(4, it.getUnit());
                ps.setFloat(5, it.getPrice());
                ps.setBoolean(6, it.isSupplying());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return -1;
    }

    public int Save(Item it) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "Update tblItems Set itemName=?, unit=?, supplying=?, supCode=?, Where itemCode=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, it.getItemName());
                ps.setString(2, it.getUnit());
                ps.setFloat(3, it.getPrice());
                ps.setBoolean(4, it.isSupplying());
                ps.setString(5, it.getSupCode());
                ps.setString(6, it.getItemCode());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return -1;
    }

    public int Delete(Item it) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "DELETE FROM tblItems WHERE itemCode = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, it.getItemCode());
                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return -1;
    }

    public boolean validDateCodeItem(String itemCode) {
        try {
            for (int i = 0; i < modelItem.getList().size(); i++) {
                if (modelItem.getList().get(i).getItemCode().compareToIgnoreCase(itemCode) == 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean validDate(String itemCode, String itemName, String unit, String price, Object supCode) {
        if (!itemCode.toUpperCase().matches("E\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Invalid Code");
            return false;
        }
        if (itemName.isEmpty() || itemName.length() >= 50) {
            JOptionPane.showMessageDialog(null, "Invalid Name");
            return false;
        }
        if (unit.isEmpty() || unit.length() >= 50) {
            JOptionPane.showMessageDialog(null, "Invalid Unit");
            return false;
        }
        if (!price.matches("\\d+.?\\d*")) {
            JOptionPane.showMessageDialog(null, "Invalid Price");
            return false;
        }
        if (supCode == null) {
            JOptionPane.showMessageDialog(null, "Choose the supCode");
            return false;
        }
        return true;
    }

    public boolean checkconstraintSupCode(String supCode) {
        for (int i = 0; i < modelItem.getList().size(); i++) {
            if (supCode.compareToIgnoreCase(modelItem.getList().get(i).getSupCode()) == 0) {
                return false;
            }
        }
        return true;
    }
}
