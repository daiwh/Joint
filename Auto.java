package 人工智能;
import java.util.ArrayList;
import java.util.Random;
public class Auto {
    static int[][]num;
	private int direction;
	private int i0,j0;
	Auto()                     ////构造函数
	{
		 num=new int [3][3];
	}
	public int[][]getInit()    ////初始化操作
	{
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				num[i][j]=i*3+j+1;
		num[2][2]=0;
		i0=2;
		j0=2;
		Random random=new Random();
		for(int i=0;i<200;i++)
		{
			///0 1 2 3分别表示上   右   下  左
		    direction=random.nextInt(4);
			switch(direction)
			{
			case 0:  //上
				if(i0<2)
				{
					num[i0][j0]=num[i0+1][j0];num[i0+1][j0]=0;i0+=1;
				}
				break;
			case 1:  //右
				if(j0>0)
				{
					num[i0][j0]=num[i0][j0-1];num[i0][j0-1]=0;j0-=1;
				}
				break;
			case 2:  //下
				if(i0>0)
				{
					num[i0][j0]=num[i0-1][j0];num[i0-1][j0]=0;i0-=1;
				}
				break;
			case 3:  //左
				if(j0<2)
				{
					num[i0][j0]=num[i0][j0+1];num[i0][j0+1]=0;j0+=1;
				}
				break;
			}
		}
		return num;
	}
	public ArrayList<Character> getPath()             ////获得路径
	{
		EP ep=new EP(num);
		return ep.getPath();	
	}
	public static void main(String[] args)
	{
		Auto auto=new Auto();
		int [][]n=auto.getInit();
		EP e=new EP(n);
	}
}
class Node{
	   int s[][]=new int[3][3];
	   int f,g;
	   Node next,previous; 
	   char d;
	   public boolean equal(Node p)
	   {
		   for(int i=0;i<3;i++)
			   for(int j=0;j<3;j++)
				   if(p.s[i][j]!=this.s[i][j])
					   return false;
		   return true;
	   }
}
class MyList{
	  Node h;
	  int n;
	  public MyList()
	  {
		  h=new Node();
		  h.next=null;
		  h.previous=null;
		  n=0;
	  }
	  void addNode(Node p)
		 {
		   n++;
		   Node q=h.next;
		   p.next=null;
		     if(q!=null)
		      {
		         if((p.f+p.g)<(q.f+q.g))
		         {
		             p.next=q;
		             h.next=p;
		         }
		         else
		         {
		             while(q.next!=null)
		             {
		                 if((p.f+p.g)<(q.f+q.g)&&(q.next.f+q.next.g)>=(p.f+p.g))
		                 {
		                     p.next=q.next;
		                     q.next=p;
		                     break;
		                 }
		                 q=q.next;
		             }
		             if(q.next==null)
		                q.next=p;
		         }
		         }
		     else
		        h.next=p;
		 }  
	  void removenode(Node p)
	  {
		  n--;
		     Node q=h;
		     while(q.next!=null)
		         {
		             if(q.next.equal(p))
		             {
		                 q.next=p.next;
		                 if(q.next==null)
		                    return;
		             }
		             q=q.next;
		         }
	  }
	  Node iscontain(Node p)
	  {
		     Node q=h;
		     Node old=new Node();
		     while(q.next!=null)
		     {
		         int f=0;
		         for(int i=0;i<3;i++)
		            for(int j=0;j<3;j++)
		              if(q.next.s[i][j]!=p.s[i][j])
		                    {f=1;break;}
		         if(f==0)
		         {
		             old.next=q.next;
		             if(old.next.g>p.g)
		             {
		            	 old.next.g=p.g;
		            	 this.removenode(p);
		            	 this.addNode(p);
		             }
		             return old;
		         }
		         else
		            q=q.next;
		     }
		     return null;
	 }
}

class EP{
	 private Node bestnode,successor,initial;
	 private MyList open,close;
	 private int num[][];
	 private ArrayList<Character> path;
	 public EP(int num[][])
	 {
		 this.num=num;
		 path=new ArrayList<Character>();
		 open=new MyList();
		 close=new MyList();
		 initial=new Node();
		 initial.g=0;
		 initial.f=this.errorsum(num);
		 for(int i=0;i<3;i++)
			 for(int j=0;j<3;j++)
				 initial.s[i][j]=num[i][j];
		 open.addNode(initial);
		 this.go();
		 for(int i=0;i<3;i++)
			 for(int j=0;j<3;j++)
				 bestnode.s[i][j]=num[i][j];
		 this.show(bestnode);
	 }
	public ArrayList<Character> getPath() {
		return path;
	}
	private  int errorsum(int a[][])
	 {
		 int sum=0;
	     for(int i=0;i<3;i++)
	        for(int j=0;j<3;j++)
	            if(a[i][j]!=0)
	              sum+=(Math.abs((a[i][j]-1)/3-i)+Math.abs((a[i][j]-1)%3-j));
	     return sum;
	 }
	void update(Node p)
	 {
		
	     Node old;
	     p.previous=bestnode;
	     p.next=null;
	     p.g=bestnode.g+1;
	     p.f=errorsum(p.s);
	     old=open.iscontain(p);
	     if(old!=null)
	     {
	         if(p.g<old.next.g)
	         {
	             old.next.g=p.g;
	             open.removenode(old.next);
	             old.next.f=this.errorsum(old.next.s);
	             open.addNode(old.next);
	         }
	     }
	     else
	     {
	    	 old=close.iscontain(p);
	         if(old!=null&&old.next!=null)
	      {
	          if(p.g<old.next.g)
	         {
	             old.next.previous=bestnode;
	             old.next.g=p.g;
	         }
	      }
	      else
	       {
	         p.f=errorsum(p.s);
	         open.addNode(p);
	       }
	     }
	 }
  Node getmove(char c)
  {
	    int i0=0,j0=0;
	     Node p=new Node();
	     for(int i=0;i<3;i++)
	        for(int j=0;j<3;j++)
	        {
	            p.s[i][j]=bestnode.s[i][j];
	            if(bestnode.s[i][j]==0)
	               {i0=i;j0=j;}
	        }
	     switch(c)
	     {
	     case 'u':if(i0<2){p.s[i0][j0]=p.s[i0+1][j0];p.s[i0+1][j0]=0;return p;}break;
	     case 'd':if(i0>0){p.s[i0][j0]=p.s[i0-1][j0];p.s[i0-1][j0]=0;return p;}break;
	     case 'l':if(j0<2){p.s[i0][j0]=p.s[i0][j0+1];p.s[i0][j0+1]=0;return p;}break;
	     case 'r':if(j0>0){p.s[i0][j0]=p.s[i0][j0-1];p.s[i0][j0-1]=0;return p;}break;
	     }
	     return null;
	}
  int go()
  {
	     while(true)
	     {
	    	
	         if(open.h.next==null)
	            return -1;
	         bestnode=open.h.next;
	         open.removenode(bestnode);
	         close.addNode(bestnode);
	         bestnode.f=this.errorsum(bestnode.s);
	         if((bestnode.f==0))
	             return 1;
	         else
	         {
	             successor=getmove('d');
	             if(successor!=null)
	             {
	                 update(successor);
	             }
	             successor=getmove('u');
	             if(successor!=null)
	             {
	                 update(successor);
	             }
	             successor=getmove('l');
	             if(successor!=null)
	             {
	                 update(successor);
	             }
	             successor=getmove('r');
	             if(successor!=null)
	             {
	                 update(successor);
	             }
	         }
	     }
	 }
   void show(Node h)
   {
	   int a=0;
	   Node p=h;
	   while(p!=initial)
	   {
		    p.previous.next=p;
	        p=p.previous;
	   }   
	   p=initial;
	   while(p!=h)
	   {
	        if(p.next!=h)
	        {
	        	path.add(com(p.s,p.next.s));
	        }
	        else
	        {
	        	int [][]q=new int[3][3];
	        	for(int i=0;i<3;i++)
	        		for(int j=0;j<3;j++)
	        			q[i][j]=i*3+j+1;
	        	q[2][2]=0;
	        	path.add(com(p.s,q));
	        }
	        p=p.next; 
	   }
	 }
  private  char com(int a[][],int b[][])
  {
	  int i0a=0,i0b=0,j0a=0,j0b=0;
	  for(int i=0;i<3;i++)
		  for(int j=0;j<3;j++)
		  {
			  if(a[i][j]==0)
			  {i0a=i;j0a=j;}
			  if(b[i][j]==0)
			  {i0b=i;j0b=j;}
		  }
	  if(i0a==i0b&&j0a>j0b)
	         return 'r';
	  if(i0a==i0b&&j0a<j0b)
		     return 'l';
	  if(j0a==j0b&&i0a>i0b)
	         return 'd';
	  if(j0a==j0b&&i0a<i0b)
		     return 'u';
	  return 0;
  }
}