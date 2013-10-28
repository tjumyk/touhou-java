package org.tjuscs.bulletgame.util;

import java.lang.reflect.Constructor;
import java.util.List;

import org.tjuscs.bulletgame.model.BaseObject;
import org.tjuscs.bulletgame.model.GameModel;

public class GameUtil {

	public static int GROUP_GHOST = 0;
	public static int GROUP_ENEMY_BULLET = 1;
	public static int GROUP_ENEMY = 2;
	public static int GROUP_PLAYER_BULLET = 3;
	public static int GROUP_PLAYER = 4;
	public static int GROUP_INDES = 5;
	public static int GROUP_ITEM = 6;
	public static int GROUP_ALL = 16;
	public static int GROUP_NUM_OF_GROUP = 16;

	public static double LAYER_BG = -700;
	public static double LAYER_ENEMY = -600;
	public static double LAYER_PLAYER_BULLET = -500;
	public static double LAYER_PLAYER = -400;
	public static double LAYER_ITEM = -300;
	public static double LAYER_ENEMY_BULLET = -200;
	public static double LAYER_ENEMY_BULLET_EF = -100;
	public static double LAYER_TOP = 0;

	public static int MOVE_NORMAL = 0;
	public static int MOVE_ACCEL = 1;
	public static int MOVE_DECEL = 2;
	public static int MOVE_ACC_DEC = 3;

	public static int STATUS_NORMAL = 0;
	public static int STATUS_DEL = 1;
	public static int STATUS_KILL = 2;

	public static int COLOR_RED = 2;
	public static int COLOR_PURPLE = 4;
	public static int COLOR_BLUE = 6;
	public static int COLOR_CYAN = 8;
	public static int COLOR_GREEN = 10;
	public static int COLOR_YELLOW = 12;
	public static int COLOR_ORANGE = 14;
	public static int COLOR_GRAY = 16;

	private GameUtil() {
	}

	private static GameUtil instance;

	public static GameUtil getInstance() {
		if (instance == null)
			instance = new GameUtil();
		return instance;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void NewObj(Class instanceClass, Object... params) {
		if (instanceClass != null)
			try {
				if (params == null || params.length <= 0)
					GameModel.getInstance().getObjList()
							.add((BaseObject) instanceClass.newInstance());
				else {
					Class[] paramClass = new Class[params.length];
					for (int i = 0; i < params.length; i++)
						paramClass[i] = params[i].getClass();
					Constructor con = instanceClass.getConstructor(paramClass);
					GameModel.getInstance().getObjList()
							.add((BaseObject) con.newInstance(params));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static void Del(BaseObject unit) {
		unit.setStatus(STATUS_DEL);
		unit.del();
	}

	public static void RawDel(BaseObject unit) {
		unit.setStatus(STATUS_DEL);
	}

	public static void Kill(BaseObject unit) {
		unit.setStatus(STATUS_KILL);
		unit.kill();
	}

	public static void RawKill(BaseObject unit) {
		unit.setStatus(STATUS_KILL);
	}

	public static void PerserveUnit(BaseObject unit) {
		unit.setStatus(STATUS_NORMAL);
	}

	public static boolean IsValid(BaseObject unit) {
		if (unit.getStatus() == STATUS_NORMAL)
			return true;
		else
			return false;
	}

	public static void SetV(BaseObject unit, double v, double angle,
			boolean set_rot) {
		unit.setVx(v * MathUtils.cosDeg(angle));
		unit.setVy(v * MathUtils.sinDeg(angle));
		if (set_rot)
			unit.setRot(angle);
	}

	public static void SetV2(BaseObject unit, double v, double angle,
			boolean set_rot, boolean aim) {
		if (aim)
			SetV(unit, v,
					angle + Angle(unit, GameModel.getInstance().getPlayer()),
					set_rot);
		else
			SetV(unit, v, angle, set_rot);
	}

	public static boolean BoxCheck(BaseObject unit, double x1, double x2,
			double y1, double y2) {
		return unit.getX() > x1 && unit.getX() < x2 && unit.getY() > y1
				&& unit.getY() < y2;
	}

	public static double Dist(BaseObject unit1, BaseObject unit2) {
		return Dist(unit1.getX(), unit1.getY(), unit2.getX(), unit2.getY());
	}

	public static double Dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public static double Angle(BaseObject unit1, BaseObject unit2) {
		return Angle(unit1.getX(), unit1.getY(), unit2.getX(), unit2.getY());
	}

	public static double Angle(double x1, double y1, double x2, double y2) {
		double res = Math.acos((x2 - x1) / Dist(x1, y1, x2, y2));
		if (y2 < y1)
			res = -res;
		return res * 180.0 / Math.PI;
	}

	public static void BulletKiller(double x, double y, boolean kill_indes) {
		List<BaseObject> objList = GameModel.getInstance().getObjList();
		for (int i = 0 ; i < objList.size(); i++) {
			BaseObject obj = objList.get(i);
			if (obj.getGroup() == GROUP_ENEMY_BULLET
					&& Dist(x, y, obj.getX(), obj.getY()) < 600) {
				Kill(obj);
			}
		}
		if (kill_indes) {
			for (int i = 0 ; i < objList.size(); i++) {
				BaseObject obj = objList.get(i);
				if (obj.getGroup() == GROUP_INDES
						&& Dist(x, y, obj.getX(), obj.getY()) < 600) {
					Kill(obj);
				}
			}
		}
	}

	// public static void BulletDeleter(double x, double y, boolean kill_indes)
	// {
	// List<BaseObject> objList = GameModel.getInstance().getObjList();
	// for (BaseObject obj : objList) {
	// if (obj.getGroup() == GROUP_ENEMY_BULLET
	// && Dist(x, y, obj.getX(), obj.getY()) < 600) {
	// Del(obj);
	// }
	// }
	// if (kill_indes) {
	// for (BaseObject obj : objList) {
	// if (obj.getGroup() == GROUP_INDES
	// && Dist(x, y, obj.getX(), obj.getY()) < 600) {
	// Del(obj);
	// }
	// }
	// }
	// }

	public static void GetPower(int v) {
		int power = GameModel.getInstance().getPower();
		GameModel.getInstance().setPower(Math.min(500, power + v));
	}

	public static void Movement(BaseObject unit) {
		unit.setX(unit.getX() + unit.getVx());
		unit.setY(unit.getY() + unit.getVy());
		unit.setRot(unit.getRot() + unit.getOmiga());
		unit.setTimer(unit.getTimer() + 1);
		unit.setAni(unit.getAni() + 1);
		if (unit.isNavi()) {
			if (unit.getVx() == 0) {
				if (unit.getVy() > 0)
					unit.setRot(90.0);
				else
					unit.setRot(-90.0);
			} else {
				if (unit.getVx() > 0) {
					unit.setRot(Math.atan(unit.getVy() / unit.getVx()) * 180.0
							/ Math.PI);
				} else {
					double tmpr = Math.atan(unit.getVy() / unit.getVx())
							* 180.0 / Math.PI;
					unit.setRot(unit.getVy() > 0 ? tmpr + 180.0 : tmpr - 180.0);
				}
			}
		}
		if (unit.isBound()
				&& !BoxCheck(unit, 0, GameModel.getInstance().getWorldWidth(),
						0, GameModel.getInstance().getWorldHeight())) {
			Del(unit);
		}
	}

	public static void info(Object obj) {
		System.out.println(obj.toString());
	}

	public static void error(Object obj) {
		System.err.println(obj.toString());
	}
}
