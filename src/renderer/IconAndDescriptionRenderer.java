package renderer;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class IconAndDescriptionRenderer extends DefaultTableCellRenderer implements TableCellRenderer
{	
    private static final long serialVersionUID = 9064995251506839328L;

    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		label.setText(((ImageIcon) value).getDescription());
		label.setIcon((ImageIcon) value);
		return label;
	}

}
