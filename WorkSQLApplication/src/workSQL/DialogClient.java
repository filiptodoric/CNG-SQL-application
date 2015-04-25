package workSQL;

public interface DialogClient{
	
	//This interface describes a protocol for applications that want to
	//launch a JDialog. 
	//It provides call back methods for the Dialog to call to inform the client that
	//the dialog has been OK'ed or Cancelled.
	
	public static enum operation {UPDATE, DELETE, ADD};
	
	public void dialogFinished(operation anOperation);
	public void dialogCancelled();
}