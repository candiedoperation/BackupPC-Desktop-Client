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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class iniConfigData {
    public String config_header;
    public List<Map<String, String>> config_settings;
    
    public iniConfigData() {
        config_header = "";
        config_settings = new ArrayList<>();
    }
    
    public String convertToString() {
        String parsedINIConfigData = String.format("%s -> ", config_header);
        ListIterator configSettingsIterator = config_settings.listIterator();
        
        while (configSettingsIterator.hasNext()) {
            configSettingsIterator.next();
        }
        
        parsedINIConfigData = config_settings.stream()
                .map(innerMapData -> String.format("%s, ", innerMapData.toString()))
                .reduce(parsedINIConfigData, String::concat);
        
        return String.format("[%s]", parsedINIConfigData.substring(0, (parsedINIConfigData.length() - 2))); //-2 to remove the misformatted ',' at last
    }
}
