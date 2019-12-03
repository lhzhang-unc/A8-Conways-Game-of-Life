package controller;

public class LifeRunner extends Thread {

	LifeController _controller;

	public LifeRunner(LifeController controller) {

		_controller = controller;
	}

	//Calls the method in the controller to run the game
	@Override
	public void run() {
		_controller.runThread();
	}
}
