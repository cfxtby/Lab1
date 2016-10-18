public class linkTable//一个二维链表类
	{
		node head;
		node tail;

		public node insert(int fac)//插入每列的头节点，返回该节点
		//插入系数节点

		{
			node n=new node();
			n.fac=fac;
			if(head==null)
			{
				head=n;
				tail=n;
			}
			else
			{
				tail.next=n;
				tail=tail.next;
			}
			return n;
		}
		public void insert(String var,node top,int num)
		{
			node p=top;
			while (p.link!=null&&p.link.var.compareTo(var)<0)
			{
				p=p.link;
			}
			
			if(p.link==null){
				node n=new node();
				n.var=var;n.exp=num;
				p.link=n;
			}
			else if(p.link.var.compareTo(var)==0){
				p.link.exp+=num;
			}
			else{
				node q=p.link,n=new node();
				n.var=var;n.exp=num;
				p.link=n;n.link=q;
			}
		}
	
		public void simplify(){//对于该链表进行处理，简化多项式
			node p=head,q=head.next,r=head,r1=head;
			while(p!=null){
			
			if(q==null){
				r1=p;
				p=p.next;
				if(p==null)break;
				q=p.next;r=p;
			}
			else if(compare(p,q)){
				p.fac=p.fac+q.fac;
			
				
				if(q==tail)tail=r;
				r.next=q.next;
				q=q.next;
				
				if(p.fac==0){
					if(r1==p)head=p.next;
					if(tail==p)tail=null;
				}
				
				
				}
			else{
				r=q;q=q.next;
				}
			}
		}
	
		boolean compare(node top1,node top2){//检查每一列后的变量是否完全相同
			node p=top1.link,q=top2.link;
			while(p!=null&&q!=null){
				if(!(p.exp==q.exp&&p.var.equals(q.var)))
					return false;
				p=p.link;q=q.link;
			}
			if(p==q)return true;
			else return false;
		}
		
	}
