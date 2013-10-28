package org.tjuscs.bulletgame.collide;

public class CollisionCheck {
	// true -> collision false -> no collision
	public static boolean checkrr(GeometryRec m1, GeometryRec m2) {
		// TODO Auto-generated method stub
		m1.setData();
		m2.setData();
		double centerDistanceVertor[] = { m1.centerP[0] - m2.centerP[0],
				m1.centerP[1] - m2.centerP[1] };
		double[][] axes = { m1.axisx, m1.axisy, m2.axisx, m2.axisy };
		for (int i = 0; i < axes.length; i++) {
			if (m1.getProjectionRadius(axes[i])
					+ m2.getProjectionRadius(axes[i]) <= m1.dot(
					centerDistanceVertor, axes[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean checkre(GeometryRec m1, GeometryEllipse e2) {
		// TODO Auto-generated method stub
		m1.getFourCor();
		double radius = e2.getDiameter() / 2.0;
		// check whether the center of circle is in the rectangle
		double cen[] = new double[2];
		cen[0] = e2.getLocx();
		cen[1] = e2.getLocy();
		int r1 = equation(m1.fourCor[0], m1.fourCor[1], m1.fourCor[2],
				m1.fourCor[3], cen);
		int r2 = equation(m1.fourCor[0], m1.fourCor[3], m1.fourCor[1],
				m1.fourCor[2], cen);
		if (r1 == 1 && r2 == 1) {
			return true;
		} else if (r1 == 0 && r2 == 0) {
			// 距离四个点的距离是否大于半径
			if (dis(m1.fourCor[0], cen) < radius
					|| dis(m1.fourCor[1], cen) < radius
					|| dis(m1.fourCor[2], cen) < radius
					|| dis(m1.fourCor[3], cen) < radius) {
				return true;
			}
			return false;
		} else if (r1 == 0 && r2 == 1) {
			// 判断到直线距离 01 23 是否大于半径
			if (disLinePoint(m1.fourCor[0], m1.fourCor[1], cen) < squared(radius)
					|| disLinePoint(m1.fourCor[2], m1.fourCor[3], cen) < squared(radius)) {
				return true;
			}
			return false;
		} else {
			// 判断到直线距离 12 03
			if (disLinePoint(m1.fourCor[2], m1.fourCor[1], cen) < squared(radius)
					|| disLinePoint(m1.fourCor[0], m1.fourCor[3], cen) < squared(radius)) {
				return true;
			}
			return false;
		}
	}

	private static double disLinePoint(double[] ds, double[] ds2, double[] cen) {
		// TODO Auto-generated method stub
		double ab0 = ds[0] - cen[0];
		double ab1 = ds[1] - cen[1];
		double bc0 = ds2[0] - ds[0];
		double bc1 = ds2[1] - ds[1];
		double shadow = squared(ab0 * bc0 + ab1 * bc1)
				/ (squared(bc0) + squared(bc1));
		return squared(ab0) + squared(ab1) - shadow;
	}

	private static double dis(double[] ds, double[] cen) {
		// TODO Auto-generated method stub
		return squared(ds[0] - cen[0]) + squared(ds[1] - cen[1]);
	}

	private static int equation(double[] ds, double[] ds2, double[] ds3,
			double[] ds4, double[] cen) {
		// check which side cen is of the line
		if (ds[0] == ds2[0]) {
			if ((cen[0] > ds[0] && cen[0] > ds3[0])
					|| (cen[0] < ds[0] && cen[0] < ds3[0]))
				return 0;
			else
				return 1;
		}
		if (ds[1] == ds2[1]) {
			if ((cen[1] > ds[1] && cen[1] > ds3[1])
					|| (cen[1] < ds[1] && cen[1] < ds3[1]))
				return 0;
			else
				return 1;
		}
		double t1 = (cen[0] - ds[0]) / (ds2[0] - ds[0]);
		double t2 = (cen[1] - ds[1]) / (ds2[1] - ds[1]);
		double t3 = (cen[0] - ds3[0]) / (ds4[0] - ds3[0]);
		double t4 = (cen[1] - ds3[1]) / (ds4[1] - ds3[1]);
		double k1 = t1 - t2;
		double k2 = t3 - t4;
		if ((k1 > 0 && k2 > 0) || (k1 < 0 && k2 < 0)) {
			return 0;
		}
		return 1;
	}

	public static boolean checkee(GeometryEllipse e1, GeometryEllipse e2) {
		// TODO Auto-generated method stub
		double e1x = e1.getLocx();
		double e1y = e1.getLocy();
		double e2x = e2.getLocx();
		double e2y = e2.getLocy();
		if ((squared(e1x - e2x) + squared(e1y - e2y)) < squared((e1
				.getDiameter() + e2.getDiameter()) / 2.0)) {
			return true;
		}
		return false;
	}

	static double squared(double x) {
		return x * x;
	}

	/**
	 * 执行碰撞检测
	 * @param g1  一个几何对象
	 * @param g2 另一个几何对象
	 * @return 是否碰撞
	 */
	public static boolean check(GeometryBase g1, GeometryBase g2) {
		if (g1 instanceof GeometryRec && g2 instanceof GeometryRec)
			return checkrr((GeometryRec) g1, (GeometryRec) g2);
		else if (g1 instanceof GeometryEllipse && g2 instanceof GeometryRec)
			return checkre((GeometryRec) g2, (GeometryEllipse) g1);
		else if (g1 instanceof GeometryRec && g2 instanceof GeometryEllipse)
			return checkre((GeometryRec) g1, (GeometryEllipse) g2);
		return checkee((GeometryEllipse) g1, (GeometryEllipse) g2);
	}

}
