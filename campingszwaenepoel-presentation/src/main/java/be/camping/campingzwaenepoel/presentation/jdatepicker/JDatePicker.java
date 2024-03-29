/**
Copyright 2004 Juan Heyns. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Juan Heyns.
*/
package be.camping.campingzwaenepoel.presentation.jdatepicker;

import java.util.Date;

import javax.swing.JFormattedTextField;


public interface JDatePicker extends JDatePanel {

	/**
	 * Is the text component editable or not. Defaults to false.
	 * 
	 * @param editable
	 */
	public abstract void setTextEditable(boolean editable);

	/**
	 * Is the text component editable or not.
	 * 
	 * @return
	 */
	public abstract boolean isTextEditable();

	/**
	 * Sets the button to be focusable. Defaults to true.
	 * 
	 * @param focusable
	 */
	public abstract void setButtonFocusable(boolean focusable);

	/**
	 * Is the button focusable.
	 * 
	 * @return
	 */
	public abstract boolean getButtonFocusable();
	
	/**
	 * Clears the textfield
	 */
	public abstract void clearTextField();
	
	public abstract JFormattedTextField getJFormattedTextField();
	
	public abstract Date getTime();
	
	public abstract void resetTextFieldToDefaultValue();
	
}