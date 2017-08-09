package thread;

public class MultiTreadExample {

	public static void main(String[] args) {
		
		Thread th1 = new AlphabetThread();
		th1.start();
		
		
		Thread th2 = new Thread( new DigitThread() );
		th2.start();
		
		new Thread( new Runnable() {
			@Override
			public void run() {
				for ( char c='A'; c<='Z'; c++) {
					System.out.println(c);
					
					try {
						Thread.sleep( 1000 );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		
//		for ( int i=0; i<=9; i++ ) {
//			System.out.print( i );
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
		
		
	}

}
