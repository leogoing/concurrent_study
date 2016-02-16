package com.leo.study.no7;

/**
 * 通过锁顺序来避免死锁
 * @author leo
 *
 */
public class NoDeadLock {

	private static final Object tieLock=new Object();
	
	//模拟账户转账
	public void transferMoney(final Account fromAcct,final Account toAcct,final DollarAmount amount)
		throws Exception
	{
		class Helper {
			public void transfer()throws Exception{
				if(fromAcct.getBalance().compareTo(amount)<0){
					throw new Exception();
				}else{
					//进行转账操作
				}
			}
		}
		
		int fromHash=System.identityHashCode(fromAcct);
		int toHash=System.identityHashCode(toAcct);
		
		if(fromHash<toHash){
			synchronized (fromAcct) {
				synchronized (toAcct) {
					new Helper().transfer();
				}
			}
		}else if(fromHash>toHash){
			synchronized (toAcct) {
				synchronized (fromAcct) {
					new Helper().transfer();
				}
			}
		}else{
			synchronized (tieLock) {
				synchronized (tieLock) {
					new Helper().transfer();
				}
			}
		}
		
	}
	
}
class Account{
	DollarAmount getBalance(){
		return null;
	}
}//账户
class DollarAmount implements Comparable{
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}}//总额