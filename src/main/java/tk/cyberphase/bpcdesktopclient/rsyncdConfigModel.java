/*
    BackupPC Desktop Client
    Copyright (C) 2021  Atheesh Thirumalairajan

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package tk.cyberphase.bpcdesktopclient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 *
 * @author atheesh
 */
public class rsyncdConfigModel extends AbstractTableModel {
    
    private static final long serialVersionUID = 1L;
    private String[] columnIdentifiers;
    private List<String[]> rowIdentifiers;
    
    public rsyncdConfigModel(bpcConfigParser bpcConfParser) {
        rowIdentifiers = new ArrayList<>();
        columnIdentifiers = new String[]{
            "Module Name",
            "Backup Path",
            "Comment",
            "Strict Mode",
            "Authorized Users",
            "Secrets File",
            "Allowed Hosts",
            "Read Only",
            "Allow Listing",
            "Charset"
        };

        updateConfigModel(bpcConfParser);
    }

    @Override
    public int getRowCount() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return rowIdentifiers.size();
    }

    @Override
    public int getColumnCount() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return columnIdentifiers.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return rowIdentifiers.get(i)[i1];
    }
    
    @Override
    public String getColumnName(int i) {
        return columnIdentifiers[i];
    }    
    
    public void updateConfigModel (bpcConfigParser bpcConfParser) {
        try {
            rowIdentifiers.clear();
            rowIdentifiers.add(new String[columnIdentifiers.length]);
            
            List<iniConfigData> configData = new rsyncDaemonConfParser().parseDaemonConfig(bpcConfParser.bpcConfigData.get("rsyncd_conf_path"));
            ListIterator configDataIterator = configData.listIterator();
            
            while (configDataIterator.hasNext()) { configDataIterator.next(); }
            
            for (iniConfigData internalConfigIteratorData : configData) {
                System.out.println(internalConfigIteratorData.config_header);
            }
            
        } catch (FileNotFoundException e) {
            Logger.getLogger(rsyncDaemonConfParser.class.getName()).log(Level.SEVERE, null, e);
        } catch (ConfigurationException | IOException ex) {
            Logger.getLogger(rsyncDaemonConfParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        fireTableDataChanged();
    }
}
