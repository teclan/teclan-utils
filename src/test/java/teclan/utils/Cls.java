package teclan.utils;

public class Cls{
	public int a;
	public String b;
	
	public Cls(){
		
	}
	
	public Cls(int a,String b){
		this.a=a;
		this.b=b;
	}
	public String toString(){
		return String.format("a = %s,b = %s", a,b);
	}
}
