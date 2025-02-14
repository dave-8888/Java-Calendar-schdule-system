package one.component;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * 日期选择器控件
 *
 *
 */
@SuppressWarnings("serial")
public final class JDateChooser extends JDialog
{

    // 定义相关参数
    /**
     * 年份
     */
    private int year = 0;
    /**
     * 月份
     */
    private int month = 0;
    /**
     * 天
     */
    private int date = 0;

    /**
     * 日期选择背景色
     */
    private Color selectColor = Color.green;
    /**
     * 日期背景色
     */
    private Color dateColor = Color.white;
    /**
     * 日期鼠标进入背景色
     */
    private Color dateHoverColor = Color.lightGray;
    /**
     * 日期标题背景色
     */
    private Color dateTitleColor = Color.gray;
    /**
     * 日期标题字体颜色
     */
    private Color dateTitleFontColor = Color.black;
    /**
     * 日期字体颜色
     */
    private Color dateFontColor = Color.black;

    /**
     * 日期是否有效标志
     */
    private boolean flag = false;

    /**
     * 最小年份
     */
    private int minYear = 1900;
    /**
     * 最大年份
     */
    private int maxYear = 2050;

    // 定义所需组件
    /**
     * 上一年
     */
    private JButton jbYearPre;
    /**
     * 下一年
     */
    private JButton jbYearNext;
    /**
     * 上一月
     */
    private JButton jbMonthPre;
    /**
     * 下一月
     */
    private JButton jbMonthNext;
    /**
     * 年份下拉选择框
     */
    private JComboBox<String> jcbYear;
    /**
     * 月份下拉选择框
     */
    private JComboBox<String> jcbMonth;
    /**
     * 天标签
     */
    private JLabel[][] jlDays;
    /**
     * 选择
     */
    private JButton jbChoose;
    /**
     * 今日
     */
    private JButton jbToday;
    /**
     * 取消
     */
    private JButton jbCancel;

    /**
     * 程序主方法
     *
     * @param args
     *   命令参数
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
        }
        JDateChooser gg = new JDateChooser();
        gg.showDateChooser();
        System.out.println(gg.getDateFormat("yyyy-MM-dd"));
    }

    /**
     * 显示对话框
     */
    public void showDateChooser()
    {
        setVisible(true);
    }

    /**
     * 关闭对话框
     */
    public void closeDateChooser()
    {
        this.dispose();
    }

    /**
     * 设置时间
     *
     * @param year
     *   年份 1900-2050
     * @param month
     *   月份 1-12
     * @param date
     *   天
     */
    public void setDate(int year, int month, int date)
    {
        if (year >= minYear && year <= maxYear)
        {
            this.year = year;
        }
        else
        {
            return;
        }
        if (month >= 1 && month <= 12)
        {
            this.month = month;
        }
        else
        {
            return;
        }
        if (date > 0 && date <= getDaysInMonth(year, month))
        {
            this.date = date;
        }
        else
        {
            return;
        }
    }

    /**
     * 获得用户操作是否有效标志
     *
     * @return 事件是否有效
     */
    public boolean getFlag()
    {
        return flag;
    }

    /**
     * 构造方法
     */
    public JDateChooser()
    {
        initComponent();
        initComponentData();
        addComponent();
        addListener();
        setDialogAttribute();
    }

    /**
     * 实例化组件
     */
    private void initComponent()
    {
        jbYearPre = new JButton();
        jbYearNext = new JButton();
        jbMonthPre = new JButton();
        jbMonthNext = new JButton();
        jcbYear = new JComboBox<String>();
        jcbMonth = new JComboBox<String>();

        jlDays = new JLabel[7][7];

        jbChoose = new JButton();
        jbToday = new JButton();
        jbCancel = new JButton();
    }

    /**
     * 初始化组件数据
     */
    private void initComponentData()
    {
        jbYearPre.setText("上年");
        jbYearNext.setText("下年");
        jbMonthPre.setText("上月");
        jbMonthNext.setText("下月");
        Calendar calendar = Calendar.getInstance();
        if (year != 0 && month != 0 && date != 0)
        {
            calendar.set(year, month - 1, date);
        }
        else
        {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            date = calendar.get(Calendar.DAY_OF_MONTH);
        }
        initYear();
        jcbYear.setSelectedItem(year + "年");
        for (int i = 1; i <= 12; i++)
        {
            jcbMonth.addItem(i + "月");
        }
        jcbMonth.setSelectedItem(month + "月");
        for (int i = 0; i < 7; i++)
        {
            JLabel temp = new JLabel();
            temp.setHorizontalAlignment(JLabel.CENTER);
            temp.setVerticalAlignment(JLabel.CENTER);
            temp.setOpaque(true);
            temp.setBackground(dateTitleColor);
            temp.setForeground(dateTitleFontColor);
            jlDays[0][i] = temp;
        }
        for (int i = 1; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                JLabel temp = new JLabel();
                temp.setHorizontalAlignment(JLabel.CENTER);
                temp.setVerticalAlignment(JLabel.CENTER);
                temp.setOpaque(true);
                temp.setForeground(dateFontColor);
                jlDays[i][j] = temp;
            }
        }

        String[] days = { "日", "一", "二", "三", "四", "五", "六" };
        for (int i = 0; i < 7; i++)
        {
            jlDays[0][i].setText(days[i]);
        }

        jbChoose.setText("选择");
        jbToday.setText("今日");
        jbCancel.setText("取消");

        changeDate();
    }

    /**
     * 初始化显示年份范围
     */
    private void initYear()
    {
        jcbYear.removeAllItems();
        for (int i = minYear; i <= maxYear; i++)
        {
            jcbYear.addItem(i + "年");
        }
    }

    /**
     * 添加组件
     */
    private void addComponent()
    {
        // 添加背部组件
        JPanel north = new JPanel();
        north.add(jbYearPre);
        north.add(jbMonthPre);
        north.add(jcbYear);
        north.add(jcbMonth);
        north.add(jbMonthNext);
        north.add(jbYearNext);
        this.add(north, "North");

        // 添加中间组件
        JPanel center = new JPanel(new GridLayout(7, 7));
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                center.add(jlDays[i][j]);
            }
        }
        this.add(center);

        // 添加南部组件
        JPanel jpSouth = new JPanel();
        jpSouth.add(jbChoose);
        jpSouth.add(jbToday);
        jpSouth.add(jbCancel);
        this.add(jpSouth, "South");
    }

    /**
     * 获得指定年指定月份的天数
     *
     * @param year
     *   年份
     * @param month
     *   月份
     * @return 天数
     */
    private int getDaysInMonth(int year, int month)
    {
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(year))
                {
                    return 29;
                }
                return 28;
            default:
                return 0;
        }
    }

    /**
     * 清空日期
     */
    private void clearDate()
    {
        for (int i = 1; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                jlDays[i][j].setText("");
            }
        }
    }

    /**
     * 更改日期
     *
     */
    private void changeDate()
    {
        refreshLabelColor();
        clearDate();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int day_in_week = calendar.get(Calendar.DAY_OF_WEEK);
        int days = getDaysInMonth(year, month);
        if (date > days)
        {
            date = 1;
        }
        int temp = 0;
        for (int i = day_in_week - 1; i < 7; i++)
        {
            temp++;
            jlDays[1][i].setText(temp + "");
            if (temp == date)
            {
                jlDays[1][i].setBackground(selectColor);
            }
        }
        for (int i = 2; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                temp++;
                if (temp > days)
                {
                    return;
                }
                jlDays[i][j].setText(temp + "");
                if (temp == date)
                {
                    jlDays[i][j].setBackground(selectColor);
                }
            }
        }
    }

    /**
     * 添加监听
     */
    private void addListener()
    {
        LabelMouseListener labelMouseListener = new LabelMouseListener();
        for (int i = 1; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                jlDays[i][j].addMouseListener(labelMouseListener);
            }
        }
        ButtonActionListener buttonActionListener = new ButtonActionListener();
        jbYearPre.addActionListener(buttonActionListener);
        jbYearNext.addActionListener(buttonActionListener);
        jbMonthPre.addActionListener(buttonActionListener);
        jbMonthNext.addActionListener(buttonActionListener);
        jbChoose.addActionListener(buttonActionListener);
        jbToday.addActionListener(buttonActionListener);
        jbCancel.addActionListener(buttonActionListener);

        ComboBoxItemListener comboBoxItemListener = new ComboBoxItemListener();
        jcbYear.addItemListener(comboBoxItemListener);
        jcbMonth.addItemListener(comboBoxItemListener);
    }

    /**
     * 解析年份或月份
     *
     * @param yearOrMonth
     *   年份或月份字符串
     * @return 年份或月份
     */
    private int parseYearOrMonth(String yearOrMonth)
    {
        return Integer.parseInt(yearOrMonth.substring(0, yearOrMonth.length() - 1));
    }

    /**
     * 判断是否为闰年
     *
     * @param year
     *   年份
     * @return true 闰年<br/>
     *   false 平年
     */
    private boolean isLeapYear(int year)
    {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 设置对话框属性
     */
    private void setDialogAttribute()
    {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        // 显示为模态对话框
        this.setModal(true);
        this.setTitle("日期选择器");
    }

    /**
     * 刷新日期标签背景颜色
     */
    private void refreshLabelColor()
    {
        for (int i = 1; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                jlDays[i][j].setBackground(dateColor);
            }
        }
    }

    /**
     * 获得显示最小年份
     *
     * @return 显示最小年份
     */
    public int getMinYear()
    {
        return minYear;
    }

    /**
     * 获得显示最大年份
     *
     * @return 显示最大年份
     */
    public int getMaxYear()
    {
        return maxYear;
    }

    /**
     * 设置显示最小年份和最大年份(1900-9999)
     *
     * @param minYear
     *   最小年份
     * @param maxYear
     *   最大年份
     */
    public void setMinAndMaxYear(int minYear, int maxYear)
    {
        if (minYear > maxYear || minYear < 1900 || maxYear > 9999)
        {
            return;
        }
        this.minYear = minYear;
        this.maxYear = maxYear;
        initYear();
    }

    /**
     * 获得选中背景颜色
     *
     * @return 选中背景颜色
     */
    public Color getSelectColor()
    {
        return selectColor;
    }

    /**
     * 设置选中背景颜色
     *
     * @param selectColor
     *   选中背景颜色
     */
    public void setSelectColor(Color selectColor)
    {
        this.selectColor = selectColor;
    }

    /**
     * 获得日期背景颜色
     *
     * @return 日期背景颜色
     */
    public Color getDateColor()
    {
        return dateColor;
    }

    /**
     * 设置日期背景颜色
     *
     * @param dateColor
     *   日期背景颜色
     */
    public void setDateColor(Color dateColor)
    {
        this.dateColor = dateColor;
    }

    /**
     * 获得日期鼠标进入背景颜色
     *
     * @return 日期鼠标进入背景颜色
     */
    public Color getDetaHoverColor()
    {
        return dateHoverColor;
    }

    /**
     * 设置日期鼠标进入背景颜色
     *
     * @param dateHoverColor
     *   日期鼠标进入背景颜色
     */
    public void setDateHoverColor(Color dateHoverColor)
    {
        this.dateHoverColor = dateHoverColor;
    }

    /**
     * 获得日期标题背景颜色
     *
     * @return 日期标题背景颜色
     */
    public Color getDateTitleColor()
    {
        return dateTitleColor;
    }

    /**
     * 设置日期标题背景颜色
     *
     * @param dateTitleColor
     *   日期标题背景颜色
     */
    public void setDateTitleColor(Color dateTitleColor)
    {
        this.dateTitleColor = dateTitleColor;
    }

    /**
     * 获得日期标题字体颜色
     *
     * @return 日期标题字体颜色
     */
    public Color getDateTitleFontColor()
    {
        return dateTitleFontColor;
    }

    /**
     * 设置日期标题字体颜色
     *
     * @param dateTitleFontColor
     *   日期标题字体颜色
     */
    public void setDateTitleFontColor(Color dateTitleFontColor)
    {
        this.dateTitleFontColor = dateTitleFontColor;
    }

    /**
     * 获得日期字体颜色
     *
     * @return 日期字体颜色
     */
    public Color getDateFontColor()
    {
        return dateFontColor;
    }

    /**
     * 设置日期字体颜色
     *
     * @param dateFontColor
     *   日期字体颜色
     */
    public void setDateFontColor(Color dateFontColor)
    {
        this.dateFontColor = dateFontColor;
    }

    /**
     * 获得选择年份
     *
     * @return 选择年份
     */
    public int getYear()
    {
        return year;
    }

    /**
     * 获得选中月份
     *
     * @return 选中月份
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * 获得选中天为当月第几天
     *
     * @return 选中天为当月第几天
     */
    public int getDate()
    {
        return date;
    }

    /**
     * 获得选中天为一周中第几天
     *
     * @return 选中天为一周中第几天
     */
    public int getDayOfWeek()
    {
        return getCalendar().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得选中天为一年中第几天
     *
     * @return 选中天为一年中第几天
     */
    public int getDayOfYear()
    {
        return getCalendar().get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获得日期对象
     *
     * @return 日期对象
     */
    public Date getDateObject()
    {
        return getCalendar().getTime();
    }

    /**
     * 获得以指定规则格式化的日期字符串
     *
     * @param format
     *   格式化规则
     * @return 日期字符串
     */
    public String getDateFormat(String format)
    {
        return new SimpleDateFormat(format).format(getDateObject());
    }

    /**
     * 获得Calendar对象
     *
     * @return Calendar对象
     */
    private Calendar getCalendar()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        return calendar;
    }

    /**
     * 标签鼠标监听
     *
     * @author jianggujin
     *
     */
    final class LabelMouseListener extends MouseAdapter
    {

        @Override
        public void mouseClicked(MouseEvent e)
        {
            JLabel temp = (JLabel) e.getSource();
            if (!temp.getText().equals(""))
            {
                int date = Integer.parseInt(temp.getText());
                {
                    if (date != JDateChooser.this.date)
                    {
                        JDateChooser.this.date = date;
                        refreshLabelColor();
                        temp.setBackground(selectColor);
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            JLabel temp = (JLabel) e.getSource();
            if (!temp.getText().equals(""))
            {
                temp.setBackground(dateHoverColor);
            }
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            JLabel temp = (JLabel) e.getSource();
            if (!temp.getText().equals(""))
            {
                if (Integer.parseInt(temp.getText()) != date)
                {
                    temp.setBackground(dateColor);
                }
                else
                {
                    temp.setBackground(selectColor);
                }
            }
        }

    }

    /**
     * 按钮动作监听
     *
     * @author jianggujin
     *
     */
    final class ButtonActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == jbYearPre)
            {
                int select = jcbYear.getSelectedIndex();
                if (select > 0)
                {
                    jcbYear.setSelectedIndex(select - 1);
                }
            }
            else if (e.getSource() == jbYearNext)
            {
                int select = jcbYear.getSelectedIndex();
                if (select < jcbYear.getItemCount() - 1)
                {
                    jcbYear.setSelectedIndex(select + 1);
                }
            }
            else if (e.getSource() == jbMonthPre)
            {
                int select = jcbMonth.getSelectedIndex();
                if (select > 0)
                {
                    jcbMonth.setSelectedIndex(select - 1);
                }
            }
            else if (e.getSource() == jbMonthNext)
            {
                int select = jcbMonth.getSelectedIndex();
                if (select < jcbMonth.getItemCount() - 1)
                {
                    jcbMonth.setSelectedIndex(select + 1);
                }
            }
            else if (e.getSource() == jbChoose)
            {
                flag = true;
                closeDateChooser();
            }
            else if (e.getSource() == jbToday)
            {
                flag = true;
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                date = calendar.get(Calendar.DATE);
                closeDateChooser();
            }
            else if (e.getSource() == jbCancel)
            {
                flag = false;
                closeDateChooser();
            }
        }
    }

    /**
     * 下拉选择框项改变监听
     *
     * @author jianggujin
     *
     */
    final class ComboBoxItemListener implements ItemListener
    {

        public void itemStateChanged(ItemEvent e)
        {
            if (e.getSource() == jcbYear)
            {
                int year = parseYearOrMonth(jcbYear.getSelectedItem().toString());
                if (year != JDateChooser.this.year)
                {
                    JDateChooser.this.year = year;
                    changeDate();
                }
            }
            else if (e.getSource() == jcbMonth)
            {
                int month = parseYearOrMonth(jcbMonth.getSelectedItem().toString());
                if (month != JDateChooser.this.month)
                {
                    JDateChooser.this.month = month;
                    changeDate();
                }
            }
        }
    }
}