/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package be.camping.campingzwaenepoel.presentation.dialog.fotochooser;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

import be.camping.campingzwaenepoel.common.utils.ImageUtils;

/* ImageFileView.java is used by FileChooserDemo2.java. */
public class ImageFileView extends FileView {
    ImageIcon jpgIcon = ImageUtils.createImageIcon("images/jpgIcon.gif");
    ImageIcon gifIcon = ImageUtils.createImageIcon("images/gifIcon.gif");
    ImageIcon tiffIcon = ImageUtils.createImageIcon("images/tiffIcon.gif");
    ImageIcon pngIcon = ImageUtils.createImageIcon("images/pngIcon.png");

    public String getName(File f) {
        return null; //let the L&F FileView figure this out
    }

    public String getDescription(File f) {
        return null; //let the L&F FileView figure this out
    }

    public Boolean isTraversable(File f) {
        return null; //let the L&F FileView figure this out
    }

    public String getTypeDescription(File f) {
        String extension = ImageUtils.getExtension(f);
        String type = null;

        if (extension != null) {
            if (extension.equals(ImageUtils.JPEG) ||
                extension.equals(ImageUtils.JPG)) {
                type = "JPEG Image";
            } else if (extension.equals(ImageUtils.GIF)){
                type = "GIF Image";
            } else if (extension.equals(ImageUtils.TIFF) ||
                       extension.equals(ImageUtils.TIF)) {
                type = "TIFF Image";
            } else if (extension.equals(ImageUtils.PNG)){
                type = "PNG Image";
            }
        }
        return type;
    }

    public Icon getIcon(File f) {
        String extension = ImageUtils.getExtension(f);
        Icon icon = null;

        if (extension != null) {
            if (extension.equals(ImageUtils.JPEG) ||
                extension.equals(ImageUtils.JPG)) {
                icon = jpgIcon;
            } else if (extension.equals(ImageUtils.GIF)) {
                icon = gifIcon;
            } else if (extension.equals(ImageUtils.TIFF) ||
                       extension.equals(ImageUtils.TIF)) {
                icon = tiffIcon;
            } else if (extension.equals(ImageUtils.PNG)) {
                icon = pngIcon;
            }
        }
        return icon;
    }
}
