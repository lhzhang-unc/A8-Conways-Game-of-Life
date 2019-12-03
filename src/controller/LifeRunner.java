package controller;

public class LifeRunner extends Thread {

	LifeController _controller;

	public LifeRunner(LifeController controller) {

		_controller = controller;
	}

	@Override
	public void run() {
		_controller.runThread();
	}
}
