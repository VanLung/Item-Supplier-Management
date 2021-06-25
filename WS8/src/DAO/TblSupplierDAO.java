/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Suppliers;
import GUI.CustomerTableModelSuppliers;
import ULTILS.DBUltis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author By Van Lung, IDStudent: SE140193
 */
public class TblSupplierDAO {

    private String[] headers = {"supCode", "supName", "Address"};
    private int[] indexes = {0, 1, 2};
    private CustomerTableModelSuppliers modelSup = new CustomerTableModelSuppliers(headers, indexes);
    private Vector<String> listSuppliersComboBox = new Vector();

    public TblSupplierDAO() {
        try {
            loadData();
            loadSuppliersComboBox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws Exception {
        modelSup.getList().clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "select * from tblSuppliers";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Suppliers sup = new Suppliers(rs.getString("supCode"), rs.getString("supName"),
                            rs.getString("address"), rs.getBoolean("colloborating"));
                    modelSup.getList().add(sup);
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

    public CustomerTableModelSuppliers getModelSup() {
        return modelSup;
    }
    

    public Vector<String> loadSuppliersComboBox() throws Exception {
        listSuppliersComboBox.clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                con = DBUltis.openConnection();
                if (con != null) {
                    String sql = "SELECT supCode, supName FROM tblSuppliers";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        listSuppliersComboBox.add(rs.getString("supCode") + "-" + rs.getString("supName"));
                    }
                    return listSuppliersComboBox;
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
        return null;
    }

    public Vector<String> getListSuppliersComboBox() {
        return listSuppliersComboBox;
    }

    public String getSupNameComboBox(String supCode) {
        for (Suppliers sup : modelSup.getList()) {
            if (sup.getSupCode().compareToIgnoreCase(supCode) == 0) {
                return sup.getSupName();
            }
        }
        return "";
    }

    public int Insert(Suppliers sup) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "Insert tblSuppliers Values(?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, sup.getSupCode());
                ps.setString(2, sup.getSupName());
                ps.setString(3, sup.getAddress());
                ps.setBoolean(4, sup.isColloborating());
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

    public int Save(Suppliers sup) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "UPDATE tblSuppliers SET supName =?, address=?, collaborating=? WHERE supCode=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, sup.getSupName());
                ps.setString(2, sup.getAddress());
                ps.setBoolean(3, sup.isColloborating());
                ps.setString(4, sup.getSupCode());
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

    public int Delete(Suppliers sup) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if (con != null) {
                String sql = "DELETE FROM tblSuppliers WHERE supCode =?";
                ps = con.prepareStatement(sql);
                ps.setString(1, sup.getSupCode());
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

    public boolean validDateCodeSupplier(String supCode) {
        try {
            for (int i = 0; i < modelSup.getList().size(); i++) {
                if (modelSup.getList().get(i).getSupCode().compareToIgnoreCase(supCode) == 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean validDate(String supCode, String supName, String address) {
        if (supCode.isEmpty() || supCode.length() > 10) {
            JOptionPane.showMessageDialog(null, "Invalid Code Suppliers");
            return false;
        }
        if (supName.isEmpty() || supName.length() > 50) {
            JOptionPane.showMessageDialog(null, "Invalid supName");
            return false;
        }
        if (supName.isEmpty() || supName.length() > 50) {
            JOptionPane.showMessageDialog(null, "Invalid Address");
            return false;
        }
        return true;
    }

}
