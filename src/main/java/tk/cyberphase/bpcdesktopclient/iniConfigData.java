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

import java.util.HashMap;
import java.util.Map;

public class iniConfigData {
    public String config_header;
    public Map<String, String> config_settings;
    
    public iniConfigData() {
        config_header = "";
        config_settings = new HashMap<>();
    }
    
    public String convertToString() {
        return String.format("%s -> %s", config_header, config_settings.toString()); //-2 to remove the misformatted ',' at last
    }
}
