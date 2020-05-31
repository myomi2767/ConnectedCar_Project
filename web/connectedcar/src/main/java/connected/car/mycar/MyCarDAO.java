package connected.car.mycar;

public interface MyCarDAO {
	public int inseryMyCar(MyCarVO mycar);
	public MyCarVO getCarinfo(String carid);
}
