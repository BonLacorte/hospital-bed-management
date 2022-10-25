package swing;

//import com.raven.model.StatusType;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class Table1 extends JTable{
    
    public Table1() {
        //setShowHorizontalLines(false);
        //setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        
        
        for (int column = 0; column < this.getColumnCount(); column++) {
            TableColumn tableColumn = this.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < this.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = this.getCellRenderer(row, column);
                Component c = this.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + this.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows

                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }
        }
        
        
        
        
        
        
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                
                // TableHeader.java Class
                TableHeader header = new TableHeader(o + "");
                
                header.setForeground(Color.WHITE);
                header.setBackground(new Color(0,153,153));
                header.setHorizontalAlignment(JLabel.CENTER);
                //header.setBorder(noFocusBorder);
                //header.setH
                
                return header;
            }
        });
        
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int i, int i1) {
                
                    Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                    com.setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    setHorizontalTextPosition(CENTER);
                    //setV
                    if (selected) {
                        com.setForeground(new Color(0,153,153));
                    } else {
                        com.setForeground(Color.BLACK);
                    }
                    return com;
                // 0,153,153
            }
        });
        
        
    }
    /*
    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
    */
}
