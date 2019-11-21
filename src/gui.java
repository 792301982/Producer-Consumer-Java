import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui {
    public static JLabel[] labelsHuanchong = null;
    public static JLabel[] labelsZuse = null;
    public static JLabel[] labelsWait = null;
    //初始化按钮
    public static JButton[] btn = null;

    public static void main(String[] args) {
        class Act {
            private String[][] ns = {
                    {"xx", "xx"},
                    {"xx", "xx", "xx"},
                    {"xx", "xx", "xx"}
            };

            public void printDate(JLabel[] l1, JLabel[] l2, JLabel[] l3) {
                //缓冲区
                for (int i = 1; i < 3; i++) {
                    l1[i].setText(ns[0][i - 1]);
                }
                //阻塞队列
                for (int i = 1; i < 4; i++) {
                    l2[i].setText(ns[1][i - 1]);
                }
                //等待队列
                for (int i = 1; i < 4; i++) {
                    l3[i].setText(ns[2][i - 1]);
                }
            }

            private int len(String[] s)
            {
                int n=0;
                for(String i : s)
                {
                    if(i != "xx") n++;
                }
                return  n;
            }

            public void setEnabledBtn(JButton[] btns)
            {
                for (JButton i : btns)
                {
                    i.setEnabled(true);
                }
                for (int i=0 ;i<3; i++)
                {

                    if(ns[1][i]!="xx" )
                    {
                        String index=ns[1][i].substring(3);
                        int index_int = Integer.parseInt( index );
                        btns[index_int-1].setEnabled(false);
                    }
                }

                for (int i=3 ;i<6; i++)
                {
                    if(ns[2][i-3]!="xx" )
                    {
                        String index=ns[2][i-3].substring(3);
                        int index_int = Integer.parseInt( index );
                        btns[index_int-1+4].setEnabled(false);
                    }
                }

                //缓冲队列或阻塞队列满
                if(len(ns[1])>=3) {
                    for(int i=0;i<4;i++)
                    {
                        btns[i].setEnabled(false);
                    }
                }
                else if(len(ns[2])>=3){
                    for(int i=4;i<8;i++)
                    {
                        btns[i].setEnabled(false);
                    }
                };
            }

            public void shengchan(String s) {
                if (this.len(ns[0]) == 2) {
                    //缓冲区满
                    System.out.println("缓冲区满");
                    for (int i = 0; i < 3; i++) {
                        if (ns[1][i] == "xx") {
                            ns[1][i] = s;
                            break;
                        }
                    }
                } else {
                    //缓冲区非满
                    System.out.println("缓冲区非满");
                    if(this.len(ns[2]) != 0)
                    {
                        //等待队列非空 出队一位
                        for(int i=0;i<3;i++)
                        {
                            if(i<2) ns[2][i]=ns[2][i+1];
                            else ns[2][i]="xx";
                        }
                    }
                    else {

                        for (int i = 0; i < 2; i++) {
                            if (ns[0][i] == "xx") {
                                ns[0][i] = s;
                                break;
                            }
                        }
                    }
                }
            }

            public void xiaofei(String s) {
                if (this.len(ns[0]) != 0) {
                    //缓冲区非空 出队一位
                    for(int i=0;i<2;i++)
                    {
                        if(i<1) ns[0][i]=ns[0][i+1];
                        else
                        {
                            if(this.len(ns[1]) != 0)
                            {
                                //阻塞队列非空 出队一位 进入缓冲区
                                ns[0][i]=ns[1][0];
                                for(int u=0;u<3;u++)
                                {
                                    if(u<2) ns[1][u]=ns[1][u+1];
                                    else ns[1][u]="xx";
                                }
                            }
                            else{
                                ns[0][1]="xx";
                            }

                        }
                    }

                } else {
                    //缓冲区空 等待队列入队
                    for (int i = 0; i < 3; i++) {
                        if (ns[2][i] == "xx") {
                            ns[2][i] = s;
                            break;
                        }
                    }
                }
            }


        }


        Act d = new Act();
        JFrame jf = new JFrame("生产者消费者");
        jf.setSize(700, 500);

        // 创建按钮面板
        JPanel panel = new JPanel();


        btn = new JButton[8];
        for (int i = 0; i < 8; i++) {
            if (i < 4) {
                btn[i] = new JButton("生产者" + (i + 1));
                btn[i].addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                 //System.out.println("按钮被点击生产者");
                                                 d.shengchan(e.getActionCommand());
                                                 d.printDate(labelsHuanchong, labelsZuse, labelsWait);
                                                 d.setEnabledBtn(btn);
                                             }
                                         }
                );
            } else {
                btn[i] = new JButton("消费者" + (i + 1 - 4));
                btn[i].addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                 //System.out.println("按钮被点击消费者");
                                                 d.xiaofei(e.getActionCommand());
                                                 d.printDate(labelsHuanchong, labelsZuse, labelsWait);
                                                 d.setEnabledBtn(btn);
                                             }
                                         }
                );
            }
        }

        for (JButton i : btn) {
            panel.add(i);
        }

//        JButton btn01 = new JButton("生产者1");
//        JButton btn02 = new JButton("生产者2");
//        JButton btn03 = new JButton("生产者3");
//        JButton btn04 = new JButton("消费者1");
//        JButton btn05 = new JButton("消费者2");
//        JButton btn06 = new JButton("消费者3");

//        panel.add(btn01);
//        panel.add(btn02);
//        panel.add(btn03);
//        panel.add(btn04);
//        panel.add(btn05);
//        panel.add(btn06);

        //创建缓冲区面板
        JPanel panel2 = new JPanel();
        //显示信息初始化
        //缓冲区 panel2

        labelsHuanchong = new JLabel[3];
        labelsHuanchong[0] = new JLabel("缓冲区：");
        for (int i = 1; i < 3; i++) {
            labelsHuanchong[i] = new JLabel(("xx"));
        }
        for (JLabel i : labelsHuanchong) {
            panel2.add(i);
        }

        //创建阻塞队列面板
        JPanel panel3 = new JPanel();
        // 阻塞队列 panel3

        labelsZuse = new JLabel[4];
        labelsZuse[0] = new JLabel("阻塞队列：");
        for (int i = 1; i < 4; i++) {
            labelsZuse[i] = new JLabel(("xx"));
        }
        for (JLabel i : labelsZuse) {
            panel3.add(i);
        }

        //创建等待队列面板
        JPanel panel4 = new JPanel();
        // 等待队列 panel4

        labelsWait = new JLabel[4];
        labelsWait[0] = new JLabel("等待队列：");
        for (int i = 1; i < 4; i++) {
            labelsWait[i] = new JLabel(("xx"));
        }
        for (JLabel i : labelsWait) {
            panel4.add(i);
        }

        // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
        Box vBox = Box.createVerticalBox();
        vBox.add(panel);
        vBox.add(panel2);
        vBox.add(panel3);
        vBox.add(panel4);


        jf.setContentPane(vBox);
        jf.setVisible(true);
    }
}
