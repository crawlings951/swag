package swag;

public interface living_being {
	int home_x =0, home_y = 0;
	int work_x = 0, work_y = 0;
	int current_x = 0, current_y = 0;
	Boolean infected = false;
	void next_move();
	
}
