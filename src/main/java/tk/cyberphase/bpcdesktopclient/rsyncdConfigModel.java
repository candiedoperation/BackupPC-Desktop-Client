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
    private rsyncDaemonConfParser rsyncdConfigParser = new rsyncDaemonConfParser();
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
            
            List<iniConfigData> configData = rsyncdConfigParser.getNonGlobalFormattedParsedData(rsyncdConfigParser.parseDaemonConfig(bpcConfParser.bpcConfigData.get("rsyncd_conf_path")));
            ListIterator configDataIterator = configData.listIterator();
            
            while (configDataIterator.hasNext()) { configDataIterator.next(); }
            
            configData.forEach(internalConfigIteratorData -> {
                String[] newConfigRow = new String[columnIdentifiers.length];
                newConfigRow[0] = internalConfigIteratorData.config_header;
                newConfigRow[1] = internalConfigIteratorData.config_settings.get("path");
                newConfigRow[2] = internalConfigIteratorData.config_settings.get("comment");
                newConfigRow[3] = internalConfigIteratorData.config_settings.get("strict modes");
                newConfigRow[4] = internalConfigIteratorData.config_settings.get("auth users");
                newConfigRow[5] = internalConfigIteratorData.config_settings.get("secrets file");
                newConfigRow[6] = internalConfigIteratorData.config_settings.get("hosts allow");
                newConfigRow[7] = internalConfigIteratorData.config_settings.get("read only");
                newConfigRow[8] = internalConfigIteratorData.config_settings.get("list");
                newConfigRow[9] = internalConfigIteratorData.config_settings.get("charset");                
                
                System.out.println(Arrays.toString(newConfigRow));
                rowIdentifiers.add(newConfigRow);
            });
            
        } catch (FileNotFoundException e) {
            Logger.getLogger(rsyncDaemonConfParser.class.getName()).log(Level.SEVERE, null, e);
        } catch (ConfigurationException | IOException ex) {
            Logger.getLogger(rsyncDaemonConfParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        fireTableDataChanged();
    }
}
