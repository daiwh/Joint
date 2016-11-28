package 人工智能;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import 人工智能.Auto;
 public class Joint extends JFrame{
	private JLabel[]l;
	private int[][] num;
	private String src="F:/豚豚/Java/代/src/人工智能/output/jj_00";
	private Click click;
	private Key  key;
	private int select=1 ;
	private Auto auto;
	private int w=195,h=130;
	private int stepNum=0;
	int i0,j0;
	public Joint()
	{
		this.setLayout(null);
		l=new JLabel[9];
		auto=new Auto();
		num=auto.getInit();
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				if(num[i][j]!=0)
				{
					 l[num[i][j]-1]=new JLabel( new ImageIcon(src+(num[i][j])+".jpg"),SwingConstants.CENTER);
					 l[num[i][j]-1].setBounds(j*w,i*h, w,h);
					 add(l[num[i][j]-1]);
				}
				else
				{
					i0=i;j0=j;
				}
			}
		this.setSize(590,420);
		this.setTitle("拼图");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocation(300,100);
		ArrayList<Character> path=auto.getPath();
	    select=JOptionPane.showConfirmDialog(null,"电脑完成需要"+path.size()+"步，自动执行？","选择框",JOptionPane.YES_NO_OPTION);
		if(select==1)
		{
			click=new Click();
			key=new Key();
			this.addMouseListener(click);
			this.addKeyListener(key);
		}
		else
		{
			for(int i=0;i<path.size();i++)
			{
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				switch(path.get(i))
				{
				  case 'l':      //左
						  num[i0][j0]=num[i0][j0+1];
						  num[i0][j0+1]=0;
						  j0=j0+1;
						  move(num[i0][j0-1]-1,j0*w,i0*h,'l');
					       break;
				  case 'u':      //上
						  num[i0][j0]=num[i0+1][j0];
						  num[i0+1][j0]=0;
						  i0=i0+1;
						  move(num[i0-1][j0]-1,j0*w,i0*h,'u');
					  break;
				  case 'r':      //右
						  num[i0][j0]=num[i0][j0-1];
						  num[i0][j0-1]=0;
						  j0=j0-1;
						  move(num[i0][j0+1]-1,j0*w,i0*h,'r');
					  break;
				  case 'd':      //下
						  num[i0][j0]=num[i0-1][j0];
						  num[i0-1][j0]=0;
						  i0=i0-1;
						  move(num[i0+1][j0]-1,j0*w,i0*h,'d');
					  break;
				}
			}
		}
	}
	public static void main(String[] args)
	{
		new Joint();
	}
 class Key implements KeyListener
 {
	@Override
	public void keyPressed(KeyEvent a) {
		switch(a.getKeyCode())
		{
		  case 37:      //左
			  if(j0<2)
			  {
				  num[i0][j0]=num[i0][j0+1];
				  num[i0][j0+1]=0;
				  j0=j0+1;
				  move(num[i0][j0-1]-1,j0*w,i0*h,'l');
			  }
			  break;
		  case 38:      //上
			  if(i0<2)
			  {
				  num[i0][j0]=num[i0+1][j0];
				  num[i0+1][j0]=0;
				  i0=i0+1;
				  move(num[i0-1][j0]-1,j0*w,i0*h,'u');
			  }
			  break;
		  case 39:      //右
			  if(j0>0)
			  {
				  num[i0][j0]=num[i0][j0-1];
				  num[i0][j0-1]=0;
				  j0=j0-1;
				  move(num[i0][j0+1]-1,j0*w,i0*h,'r');
			  }
			  break;
		  case 40:      //下
			  if(i0>0)
			  {
				  num[i0][j0]=num[i0-1][j0];
				  num[i0-1][j0]=0;
				  i0=i0-1;
				  move(num[i0+1][j0]-1,j0*w,i0*h,'d');
			  }
			  break;
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	} 
 }
 class Click implements MouseListener
	{
	@Override
	public void mouseClicked(MouseEvent m) {
		int x=m.getX();
		int y=m.getY();
		int i=y/h;
		int j=x/w;
		deal(i,j);
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {	
	}
	@Override
	public void mousePressed(MouseEvent arg0) {	
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	}
    void deal(int i,int j)
    {
    	if(i>0&&num[i-1][j]==0)
    	{
    		num[i-1][j]=num[i][j];
    		num[i][j]=0;
    		i0=i;
    		j0=j;
    		move(num[i-1][j]-1,j*w,i*h,'u');
    	}
    	if(i<2&&num[i+1][j]==0)
    	{
    		num[i+1][j]=num[i][j];
    		num[i][j]=0;
    		i0=i;
    		j0=j;
    		move(num[i+1][j]-1,j*w,i*h,'d');
    	}
    	if(j>0&&num[i][j-1]==0)
    	{ 
    		num[i][j-1]=num[i][j];
    		num[i][j]=0;
    		i0=i;
    		j0=j;
    		move(num[i][j-1]-1,j*w,i*h,'l');
    	}	
    	if(j<2&&num[i][j+1]==0)
    	{
    		num[i][j+1]=num[i][j];
    		num[i][j]=0;
    		i0=i;
    		j0=j;
    		move(num[i][j+1]-1,j*w,i*h,'r');
    	}
    }
    void move(int ID,int x,int y,char c) 
    {
    	switch(c)
    	{
    	case 'u':
    	    l[ID].setBounds(x, y-h, w, h);
    		break;
    	case 'l':
    		l[ID].setBounds(x-w, y, w, h);
    		break;
    	case 'd':
    		l[ID].setBounds(x, y+h, w, h);
    		break;
    	case 'r':
    		l[ID].setBounds(x+w, y, w, h);
    		break;
    	}
    	stepNum++;
    	if(isOK())
    		JOptionPane.showConfirmDialog(null,"一共用了"+stepNum+"步",null,JOptionPane.CANCEL_OPTION);	
    }
    boolean isOK()
    {
    	for(int i=0;i<3;i++)
    		for(int j=0;j<3;j++)
    		  if(num[i][j]!=0)
    			if(num[i][j]!=i*3+j+1)
    				return false;
    	return true;
    }
}