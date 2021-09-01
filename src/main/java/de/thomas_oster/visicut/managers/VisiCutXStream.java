/**
 * This file is part of VisiCut.
 * Copyright (C) 2011 - 2013 Thomas Oster <thomas.oster@rwth-aachen.de>
 * RWTH Aachen University - 52062 Aachen, Germany
 *
 *     VisiCut is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     VisiCut is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with VisiCut.  If not, see <http://www.gnu.org/licenses/>.
 **/
package de.thomas_oster.visicut.managers;

import com.thoughtworks.xstream.XStream;

/**
 * Wrapper around XStream for basic init
 * @author Thomas Oster <thomas.oster@upstart-it.de>
 */
public class VisiCutXStream extends XStream
{

    public VisiCutXStream()
    {
      // Configure allowed classes. Be careful:
      // Classes that do evil things upon construction must not be allowed to prevent code injection vulnerabilities.
      // All classes used by all settings (device, laser profile et cetera) must be allowed, or there will be an error loading the settings.
      allowTypesByWildcard(new String[]{
        "de.thomas_oster.**",
        "java.awt.Color",
        "java.awt.geom.AffineTransform",
        "java.awt.geom.Point2D$Double",
        "javax.swing.plaf.ColorUIResource",
      });
    }
  
}
